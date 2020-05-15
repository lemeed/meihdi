/**
 * @author Michaluk David <49762@etu.he2b.be>
 * @author El Amouri Meihdi <49262@etu.he2b.be>
 * @author Rossitto Nicolas <49282@etu.he2b.be>
 * @author Azoud Ismael <42394@etu.he2b.be>
 * @licence GPL-3.0 <https://www.gnu.org/licenses/>
 */

import {get, post} from 'request-promise';
import * as RP from 'request-promise';
import Server from "./Server";
import Auth from "./Auth";
import PKI from "../service/PKI";
import DiskInterface from "../service/DiskInterface";
import Dependency from "./Dependency";

/**
 * the package request maker model
 */
class Package {

    /**
     * get the names of packages that were implicitly installed and are no longer needed
     * @return {Promise<string>} promised packages names
     */
    public async getAllUselessPackages(): Promise<string> {
        const localPackages: Array<Dependency> = await DiskInterface.getLocalPackages();
        let msg = "";

        for (const localPackage of localPackages) {
            const packDescriptor = await DiskInterface.getPackageWithVersionDescriptor(localPackage.name, localPackage.version);
            if (await this.packageLocalDependencyCount(localPackage) === 0 && !packDescriptor.isManualInstall) {
                msg += `${localPackage.name}@${localPackage.version}`;
                msg += " ";
            }
        }

        return msg;
    }

    /**
     * uninstall no longer needed packages
     * @param {boolean} keepUsrConfig - tell if the system should keep user config files
     */
    public async uninstallAllUseless(keepUsrConfig: boolean = false): Promise<void> {
        const localPackages: Array<Dependency> = await DiskInterface.getLocalPackages();
        for (const localPackage of localPackages) {
            const packDescriptor = await DiskInterface.getPackageWithVersionDescriptor(localPackage.name, localPackage.version);
            if (await this.packageLocalDependencyCount(localPackage) === 0 && !packDescriptor.isManualInstall) {
                await this.uninstall(localPackage, false, keepUsrConfig);
            }
        }

    }

    /**
     * uninstall specific package
     * @param {Dependency} pack - the package to uninstall
     * @param {boolean} cascadeUseless - tell if the useless dependencies should be uninstalled recursively
     * @param {boolean} keepUserConfig - tell if the system should keep user config files for the uninstalled package and dependencies (if applicable)
     */
    public async uninstall(pack: Dependency, cascadeUseless: boolean, keepUserConfig: boolean) {
        if (!await DiskInterface.hasPackageWithVersion(pack.name, pack.version)) {
            throw new Error(`The package ${pack.name}@${pack.version} does not exists locally`);
        }

        if (cascadeUseless) {
            const {__dependencies__: dependencies} = await DiskInterface.getPackageWithVersionDescriptor(pack.name, pack.version);
            for (const {__package__: {name: dName}, version: dVersion} of dependencies) {
                const dep: Dependency = {name: dName, version: dVersion};
                if (await this.packageLocalDependencyCount(dep) === 1) { // only the parent package depends on it
                    await this.uninstall(dep, cascadeUseless, keepUserConfig);
                }
            }
        }

        await DiskInterface.removePackage(pack.name, pack.version, keepUserConfig);
    }

    /**
     * stringify list of packages
     * @param {Array<Dependency>} packages - the packages list to stringify
     * @return {string} the stringified packages list
     */
    public getPrintablePackagesList(packages: Array<Dependency>): string {
        let msg = "";

        packages.forEach((pack, index) => {
            msg += `${pack.name}@${pack.version}`;
            if (index < packages.length) {
                msg += " ";
            }
        });

        return msg;
    }

    /**
     * count the amount of packages depending on a specific package
     * @param {Dependency} pack - the package to count the amount of packages depending on
     * @return {Promise<number>} promised count
     */
    private async packageLocalDependencyCount(pack: Dependency): Promise<number> {
        const remainingDependentPackages = await DiskInterface.getPackagesWithDependency(pack.name, pack.version);
        return remainingDependentPackages.length;
    }

    /**
     * get the package list to install on package-basis
     * @param {Dependency} basePackage - the package from which to start to discover the whole elements to install
     * @param {Array<Dependency>} packagesToInstall - the list of packages that have to be installed
     * @param {Array<Dependency>} packagesToUpdate - the list of packages that have to be updated
     */
    public async getPackageListToInstall(
        basePackage: Dependency,
        packagesToInstall: Array<Dependency> = [],
        packagesToUpdate: Array<Dependency> = []): Promise<void> {

        const {__package__: {name}, version, __dependencies__: dependencies} = await this.getPackageMetadataFromServer(basePackage);

        for (const {__package__: {name: dName}, version: dVersion} of dependencies) {
            await this.getPackageListToInstall({name: dName, version: dVersion}, packagesToInstall, packagesToUpdate);
        }

        let hasPackage: boolean = await DiskInterface.hasPackage(name);
        let hasPackageWithVersion: boolean = await DiskInterface.hasPackageWithVersion(name, version);

        if (!hasPackageWithVersion && hasPackage) {
            packagesToUpdate.push({
                name: name,
                version: version
            });
        } else if (!hasPackageWithVersion && !hasPackage) {
            packagesToInstall.push({
                name: name,
                version: version
            });
        }
    }

    /**
     * install a package
     * @param {Dependency} pack - the package to install
     * @param {boolean} isManualInstall - tell if the install is implicit or explicitly asked by the user
     * @return {Promise<boolean>} true if the install succeeded, false otherwise
     */
    public async install(pack: Dependency, isManualInstall: boolean): Promise<boolean> {
        try {
            const packageDescriptor = await this.getPackageMetadataFromServer(pack);
            await DiskInterface.writePackage(packageDescriptor, isManualInstall);
            return true;
        } catch (e) {
            console.log(e.error);
            return false;
        }
    }

    /**
     * upload package and its dependencies (if applicable) to the server
     * @param {Dependency} pack - the package to upload
     */
    public async upload(pack: Dependency) {
        const {__dependencies__: dependencies} = await DiskInterface.getPackageWithVersionDescriptor(pack.name, pack.version);

        for (const {__package__: {name: dName}, version: dVersion} of dependencies) {
            try {
                await this.upload({name: dName, version: dVersion});
            } catch (e) {
                console.log("Already on server", e);
            }
        }

        console.log(await post(Server.resolve(`/package`), {
            ...Auth.getRequestBasicAuth(),
            ...PKI.getPKIAuth(),
            body: {
                name: pack.name,
                version: pack.version,
                dependencies: dependencies.map(({__package__: {name: dName}, version: dVersion}) => ({name: dName, version: dVersion}))
            },
            json: true
        }));
    }

    /**
     * remove dist package
     * @param {Dependency} pack - the package to remove
     * @param {boolean} cascadeUseless - tell if the server should delete recursively no more useful dependencies
     */
    public async removeDist(pack: Dependency, cascadeUseless: boolean = true) {
        await RP.delete(Server.resolve(`/package/${pack.name}/${pack.version}`), {
            ...Auth.getRequestBasicAuth(),
            ...PKI.getPKIAuth(),
            body: {
                cascadeUseless
            },
            json: true
        });
    }

    /**
     * get metadata about a package from remote server
     * @param {Dependency} pack - the package to lookup metadata about
     */
    private async getPackageMetadataFromServer(pack: Dependency) {
        const response = await get(Server.resolve(`/package/${pack.name}/${pack.version}`), {
            ...Auth.getRequestBasicAuth(),
            ...PKI.getPKIAuth(),
            json: true
        });

        if (response.error) {
            throw new Error(response.error);
        }

        return response;
    }
}

export default new Package();