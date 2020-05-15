/**
 * @author Michaluk David <49762@etu.he2b.be>
 * @author El Amouri Meihdi <49262@etu.he2b.be>
 * @author Rossitto Nicolas <49282@etu.he2b.be>
 * @author Azoud Ismael <42394@etu.he2b.be>
 * @licence GPL-3.0 <https://www.gnu.org/licenses/>
 */

import {existsSync, mkdirSync} from "fs";
import {unlink, remove, mkdirp, readdir, lstat, pathExists, readJson, writeJson} from "fs-extra";
import * as path from "path";
import Dependency from "../model/Dependency"

const PACKAGE_LOCAL_STORAGE_DIR: string = "./packages";

/**
 * the interface between the storage and the packages
 */
class DiskInterface {
    /**
     * a disk interface constructor
     */
    constructor() {
        if (!existsSync(PACKAGE_LOCAL_STORAGE_DIR)) {
            mkdirSync(PACKAGE_LOCAL_STORAGE_DIR);
        }
    }

    /**
     * remove a package
     * @param {string} name - the name of the package to remove
     * @param {string} version - the version of the package to remove
     * @param {boolean} keepUserConfig - tell if we should keep user config files during the removal
     */
    async removePackage(name: string, version: string, keepUserConfig: boolean = false) {
        const targetPackageDir = path.join(PACKAGE_LOCAL_STORAGE_DIR, name);
        const targetPackageVersionDir = path.join(targetPackageDir, version);
        if (keepUserConfig) {
            await remove(path.join(targetPackageVersionDir, "bin"));
            await remove(path.join(targetPackageVersionDir, "package-descriptor.json"));
        } else {
            await remove(targetPackageVersionDir);
            const remainingVersions = await this.getPackageLocalVersions(name);
            if (remainingVersions.length === 0) {
                await remove(targetPackageDir);
            }
        }
    }

    /**
     * write a package to the storage
     * @param {Object} packageDescriptor - the package description
     * @param {boolean} isManualInstall - tell if the package install was implicit or explicitly asked by the user
     */
    async writePackage(packageDescriptor: any, isManualInstall: boolean = false) {
        const {__package__: {name}, version} = packageDescriptor;
        const targetBaseDir = path.join(PACKAGE_LOCAL_STORAGE_DIR, name, version);

        await mkdirp(path.join(targetBaseDir, "usr-config"));
        await mkdirp(path.join(targetBaseDir, "bin"));
        await writeJson(path.join(PACKAGE_LOCAL_STORAGE_DIR, name, version, "package-descriptor.json"), {...packageDescriptor, isManualInstall});
    }

    /**
     * get versions of locally installed package
     * @param {string} name - the package name of which lookup versions
     * @return {Promise<Array<string>>} promised packages versions
     */
    async getPackageLocalVersions(name: string): Promise<Array<string>> {
        return new Promise(async (resolve, reject) => {
            try {
                let versions: Array<string> = new Array<string>();

                const basePath = path.join(PACKAGE_LOCAL_STORAGE_DIR, name);
                const list = await readdir(basePath);

                for (const version of list) {
                    const stat = await lstat(path.join(basePath, version));
                    if (stat.isDirectory() && await this.isPackageValid(name, version)) {
                        versions.push(version);
                    }
                }

                resolve(versions);
            } catch (e) {
                reject(e);
            }
        });
    }

    /**
     * tell if a package is valid (if it is complete or a tombstone holding only the user config files)
     * @param {string} name - the name of the package of which to check validity
     * @param {string} version - the version of the package of which to check validity
     * @return {boolean} promised validity check answer
     */
    private async isPackageValid(name: string, version: string): Promise<boolean> {
        try {
            const targetPath = path.join(PACKAGE_LOCAL_STORAGE_DIR, name, version);
            return await pathExists(path.join(targetPath, "usr-config")) && await pathExists(path.join(targetPath, "bin")) && await pathExists(path.join(targetPath, "package-descriptor.json"));
        } catch (e) {
            return false;
        }
    }

    /**
     * tell if there is a specific package installed locally
     * @param {string} name - the name of the package to check
     * @return {boolean} promised existence check answer
     */
    async hasPackage(name: string): Promise<boolean> {
        try {
            await this.getPackageLocalVersions(name);
            return true;
        } catch (e) {
            return false;
        }
    }

    /**
     * tell if there is a specific package and version of it installed locally
     * @param {string} name - the name of the package to check
     * @param {string} version - the version of the package to check
     * @return {boolean} promised existence check answer
     */
    async hasPackageWithVersion(name: string, version: string): Promise<boolean> {
        try {
            const versions = await this.getPackageLocalVersions(name);
            return versions.includes(version);
        } catch (e) {
            return false;
        }
    }

    /**
     * get package descriptor
     * @param {string} name - the name of the package to check
     * @param {string} version - the version of the package to check
     * @return {Object} promised package descriptor
     */
    async getPackageWithVersionDescriptor(name: string, version: string): Promise<any> {
        const targetPath = path.join(PACKAGE_LOCAL_STORAGE_DIR, name, version, "package-descriptor.json");
        return await readJson(targetPath);
    }

    /**
     * get all the packages have another one as a dependency
     * @param {string} rootName - the name of the package of which to look for dependent packages
     * @param {string} rootVersion - the version of the package of which to look for dependent packages
     * @return {Promise<Array<Dependency>>} promised dependent packages list
     */
    async getPackagesWithDependency(rootName: string, rootVersion: string): Promise<Array<Dependency>> {
        let concernedPackages: Array<Dependency> = new Array<Dependency>();

        const localPackages = await this.getLocalPackages();

        for (const localPackage of localPackages) {
            const {__dependencies__: dependencies} = await this.getPackageWithVersionDescriptor(localPackage.name, localPackage.version);
            const match = dependencies.findIndex(({__package__: {name}, version}) => name === rootName && version === rootVersion) > -1;
            if (match) {
                concernedPackages.push(localPackage);
            }
        }

        return concernedPackages;
    }

    /**
     * get locally installed packages
     * @return {Promise<Array<Dependency>>} promised packages list
     */
    async getLocalPackages(): Promise<Array<Dependency>> {
        return new Promise(async (resolve, reject) => {
            try {
                let allPackages: Array<Dependency> = new Array<Dependency>();

                const basePath = path.join(PACKAGE_LOCAL_STORAGE_DIR);
                const list = await readdir(basePath);

                for (const file of list) {
                    const stat = await lstat(path.join(basePath, file));
                    if (stat.isDirectory()) {
                        const packageVersions = await this.getPackageLocalVersions(file);

                        allPackages.push(...packageVersions.map(version => ({name: file, version: version} as Dependency)));
                    }
                }
                resolve(allPackages);
            } catch (e) {
                reject(e);
            }
        });
    }
}

export default new DiskInterface();