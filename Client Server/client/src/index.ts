#!/usr/bin/env node
/**
 * @author Michaluk David <49762@etu.he2b.be>
 * @author El Amouri Meihdi <49262@etu.he2b.be>
 * @author Rossitto Nicolas <49282@etu.he2b.be>
 * @author Azoud Ismael <42394@etu.he2b.be>
 * @licence GPL-3.0 <https://www.gnu.org/licenses/>
 */

const PackageFile = require('../package.json');
import Chalk from "chalk";
import * as Figlet from "figlet";
import * as Commander from "commander";

import Dependency from "./model/Dependency";
import PackageInterface from "./view/PackageInterface";
import RegisterInterface from "./view/RegisterInterface";
import LoginInterface from "./view/LoginInterface";

console.log(Chalk.red(Figlet.textSync(PackageFile.name, {horizontalLayout: 'default'})));

/**
 * convert $packageName@packageVersion to list of dependencies
 * @param {Array<string>} packages - unparsed package list
 */
const formatPackagesList: ((array: any) => Array<Dependency>) = (packages) => {
    let formattedPackages: Array<Dependency> = new Array<Dependency>();

    for (const pack of packages) {

        let name: string, version: string;
        if (pack.includes("@")) {
            [name, version] = pack.split("@");
        } else {
            [name, version] = [pack, "latest"];
        }

        formattedPackages.push({name: name, version: version} as Dependency);
    }

    return formattedPackages;
};

/**
 * register cli default options
 */
Commander
    .version(PackageFile.version)
    .description(PackageFile.description)
    .option('-u, --user <user>', 'The account user to login with')
    .option('-p, --password <password>', 'The account password to login with');

/**
 * register cli registration action
 */
Commander
    .command('register <user> <login>')
    .action(async (user, login) => {
        await RegisterInterface.register(user, login);
    });

/**
 * register cli install action
 */
Commander
    .command('install [packages...]')
    .action(async (packages) => {
        await LoginInterface.login(Commander.user, Commander.password);
        await PackageInterface.installByNames(formatPackagesList(packages));
    });

/**
 * register cli uninstall action
 */
Commander
    .command('uninstall [packages...]')
    .action(async (packages) => {
        await LoginInterface.login(Commander.user, Commander.password);
        await PackageInterface.uninstallByNames(formatPackagesList(packages));
    });

/**
 * register cli update action
 */
Commander
    .command('update [packages...]')
    .action(async (packages) => {
        await LoginInterface.login(Commander.user, Commander.password);
        await PackageInterface.updateByNames(packages);
    });

/**
 * register cli update action
 */
Commander
    .command('upload <packageName> <version>')
    .action(async (packageName, version) => {
        await LoginInterface.login(Commander.user, Commander.password);
        await PackageInterface.upload({name: packageName, version: version} as Dependency);
    });

/**
 * register cli remove action
 */
Commander
    .command('remove <packageName> <version>')
    .action(async (packageName, version) => {
        await LoginInterface.login(Commander.user, Commander.password);
        await PackageInterface.removeDist({name: packageName, version: version} as Dependency);
    });


Commander.parse(process.argv);