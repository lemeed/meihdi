/**
 * @author Michaluk David <49762@etu.he2b.be>
 * @author El Amouri Meihdi <49262@etu.he2b.be>
 * @author Rossitto Nicolas <49282@etu.he2b.be>
 * @author Azoud Ismael <42394@etu.he2b.be>
 * @licence GPL-3.0 <https://www.gnu.org/licenses/>
 */

import Chalk from "chalk";
import * as Inquirer from "inquirer";
import Package from "../model/Package";
import Dependency from "../model/Dependency";

/**
 * the package request maker interface
 */
class PackageInterface {

    /**
     * install some packages by their names and versions
     * @param {Array<Dependency>} packages - the packages to install
     */
    public async installByNames(packages: Array<Dependency>) {

        try {

            let packagesToInstall: Array<Dependency> = [];
            let packagesToUpdate: Array<Dependency> = [];


            for (const pack of packages) {
                await Package.getPackageListToInstall(pack, packagesToInstall, packagesToUpdate);
            }

            if (packagesToInstall.length > 0) {
                console.log(`The following packages will be INSTALLED: ${Package.getPrintablePackagesList(packagesToInstall)}`);
            }

            if (packagesToUpdate.length > 0) {
                console.log(`The following packages will be UPDATED: ${Package.getPrintablePackagesList(packagesToUpdate)}`);
            }


            if (packagesToInstall.length > 0 || packagesToUpdate.length > 0) {

                const promptResponse = await Inquirer.prompt([
                    {type: 'confirm', name: 'next', message: 'Continue?', default: true}
                ]);

                const next: boolean = promptResponse.next;

                if (next) {
                    for (const pack of [...packagesToInstall, ...packagesToUpdate]) {
                        const {name, version} = pack;
                        console.log(`Installing ${name}@${version}`);
                        await Package.install(pack, packages.findIndex(({name: packName}) => packName === name) > -1);
                        console.log(Chalk.green(`${name}@${version} was successfully installed`));
                    }
                    console.log(Chalk.green("Completed"));
                } else {
                    console.log(Chalk.yellow("Cancelled by the user"));
                }
            } else {
                console.log(Chalk.green("Nothing new to install"));
            }

        } catch (e) {
            console.log(Chalk.red("Installation failed"));
            console.log(Chalk.red(e.message));
        }
    }

    /**
     * uninstall some packages by theirs names and versions
     * @param {Array<Dependency>} packages - the packages to uninstall
     */
    public async uninstallByNames(packages: Array<Dependency>) {

        const promptResponse = await Inquirer.prompt([
            {type: 'confirm', name: 'cascadeUseless', message: 'Remove useless packages recursively?', default: false},
            {type: 'confirm', name: 'keepUserConfig', message: 'Keep user config files?', default: true}
        ]);

        const cascadeUseless: boolean = promptResponse.cascadeUseless;
        const keepUserConfig: boolean = promptResponse.keepUserConfig;

        for (const pack of packages) {
            const {name, version} = pack;
            try {
                await Package.uninstall(pack, cascadeUseless, keepUserConfig);
                console.log(Chalk.green(`${pack.name}@${pack.version} was successfully uninstalled`));
            } catch (e) {
                console.log(Chalk.red(`Could not uninstall ${name}@${version}`));
                console.log(e instanceof Error ? e.message : e);
            }
        }
    }

    /**
     * update some packages by theirs names
     * @param {Array<string>} packageNames - the packages to update
     */
    public async updateByNames(packageNames: Array<string>) {
        await this.installByNames(packageNames.map(packName => ({name: packName, version: "latest"})));
        console.log(Chalk.green(`The packages were updated`));

        const uselessPackagesText: string = await Package.getAllUselessPackages();

        if (uselessPackagesText.length > 0) {
            const promptResponse = await Inquirer.prompt([
                {type: 'confirm', name: 'removeUseless', message: `You have some packages installed that are no longer needed as dependencies: ${uselessPackagesText}. Do you want to remove them?`, default: true}
            ]);

            if (promptResponse.removeUseless) {
                await Package.uninstallAllUseless();
            }

            console.log(Chalk.green(`Useless packages were removed`));
        }

    }

    /**
     * upload package to the server
     * @param {Dependency} pack - the package to upload
     */
    public async upload(pack: Dependency) {
        try {
            await Package.upload(pack);
            console.log(Chalk.green(`${pack.name}@${pack.version} uploaded`));
        } catch (e) {
            console.log(Chalk.red(`${pack.name}@${pack.version} upload failed`));
        }
    }

    /**
     * upload package to the server
     * @param {Dependency} pack - the package to upload
     */
    public async removeDist(pack: Dependency) {
        try {

            const promptResponse = await Inquirer.prompt([
                {type: 'confirm', name: 'cascadeUseless', message: 'Remove useless packages recursively?', default: false}
            ]);

            await Package.removeDist(pack, promptResponse.cascadeUseless);

            console.log(Chalk.green(`${pack.name}@${pack.version} removed from server`));
        } catch (e) {
            console.log(Chalk.red(`${pack.name}@${pack.version} removal failed`));
        }
    }
}


export default new PackageInterface();