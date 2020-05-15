/**
 * @author Michaluk David <49762@etu.he2b.be>
 * @author El Amouri Meihdi <49262@etu.he2b.be>
 * @author Rossitto Nicolas <49282@etu.he2b.be>
 * @author Azoud Ismael <42394@etu.he2b.be>
 * @licence GPL-3.0 <https://www.gnu.org/licenses/>
 */

import Route from './Route';
import User from '../entity/User';
import Package from "../entity/Package";
import PackageVersion from "../entity/PackageVersion";

/**
 * a package (or dependency) definition
 */
interface Dependency {
    name: string;
    version: string;
}

/**
 * the package route handler
 */
export default class PackageRoute extends Route {

    /**
     * a package route constructor
     * @param app - the express app where to register the route
     */
    constructor(app) {
        super();

        // link route patterns to handlers
        app.get('/packages', (req, res) => this.getAll(req, res));
        app.get('/package/:name', (req, res) => this.getVersions(req, res));
        app.get('/package/:name/:version', (req, res) => this.get(req, res));
        app.post('/package', (req, res) => this.post(req, res));
        app.put('/package/:name/:version', (req, res) => this.put(req, res));
        app.delete('/package/:name/:version', (req, res) => this.delete(req, res));
    }

    /**
     * find one package version according to search parameters
     * @param {Object} searchParams - the search parameters to use for the search
     * @return {Promise<PackageVersion>} promised package version matching the criteria
     */
    private async findOne(searchParams: any): Promise<PackageVersion> {
        const pack: PackageVersion = await PackageVersion.findOne(searchParams);

        if (typeof pack === 'undefined') {
            throw new Error("Package not found or it does not exits with the specified version");
        }

        if (!pack.enabled) {
            throw new Error("This package was not yet enabled");
        }

        await pack.package;

        await Promise.all((await pack.dependencies).map(dependency => dependency.package));

        return pack;
    }

    /**
     * get all available packages
     * @param req - the incoming request
     * @param res - the outgoing response
     */
    async getAll(req, res) {
        try {
            const packs: Array<Package> = await Package.find();
            await Promise.all(packs.map(pack => pack.versions));
            res.send(packs);

        } catch (e) {
            res.send({
                error: e instanceof Error ? e.message : e
            });
        }
    }

    /**
     * get all versions of a specific package
     * @param req - the incoming request
     * @param res - the outgoing response
     */
    async getVersions(req, res) {
        const {name} = req.params;

        try {
            const pack: Package = await Package.findOne({name: name});
            await pack.versions;
            res.send(pack);

        } catch (e) {
            res.send({
                error: e instanceof Error ? e.message : e
            });
        }
    }

    /**
     * get a specific version of a specific package
     * @param req
     * @param res
     */
    async get(req, res) {
        const {name, version} = req.params;

        try {
            const pack: Package = await Package.findOne({name: name});
            const packVersion: PackageVersion = await this.findOne({package: pack, version: (version === "latest" ? (await pack.getLatest()).version : version)});

            res.send(packVersion);

        } catch (e) {
            res.send({
                error: e instanceof Error ? e.message : e
            });
        }
    }

    /**
     * upsert (update or insert) a specific package
     * @param req - the incoming request
     * @param res - the outgoing response
     * @param {string} rootPackageName - the package name to edit/create
     * @param {string} rootPackageVersion - the package version to edit/create
     * @param {Array<{name: string, version: string}>} rootPackageDependencies - the versions of the package to edit/create
     */
    private async upsert(req, res, rootPackageName, rootPackageVersion, rootPackageDependencies) {
        console.log(rootPackageName, rootPackageVersion, rootPackageDependencies);
        try {
            const currentUser: User = res.locals.authUser as User;

            if (!currentUser.canUploadPackages) {
                throw new Error("You cannot upload a package yet. Please apply to become an allowed user to submit packages.")
            }

            const packageDependencies: Array<PackageVersion> = [];

            for (const dependency of rootPackageDependencies as Array<Dependency>) {

                if (dependency.name === rootPackageName) {
                    throw new Error("A package cannot depend on itself, event with another version number");
                }

                const pack: Package = await Package.findOne({name: dependency.name});
                const packVersion: PackageVersion = await PackageVersion.findOne({where: {package: pack.id, version: dependency.version}});

                if (typeof pack === 'undefined' || typeof packVersion === 'undefined' || (!packVersion.enabled && pack.author.id !== currentUser.id)) {
                    throw new Error(`Missing dependency ${dependency.name} (${dependency.version}). Please submit that dependency first then try again`);
                }

                packageDependencies.push(packVersion);
            }

            let rootPackage: Package = await Package.findOne({name: rootPackageName});

            if (typeof rootPackage === 'undefined') {
                rootPackage = new Package();
                rootPackage.author = currentUser;
                rootPackage.name = rootPackageName;
                await rootPackage.save();
            } else {
                rootPackage = await Package.findOneOrFail({name: rootPackageName});

                if (rootPackage.author.id !== currentUser.id) {
                    throw new Error(`You cannot push a new version to package named '${rootPackage.name}' because it does not belong to you`);
                }
            }

            let packageVersion: PackageVersion = new PackageVersion();

            packageVersion.package = Promise.resolve(rootPackage);
            packageVersion.version = rootPackageVersion;
            packageVersion.dependencies = Promise.resolve(packageDependencies);

            await packageVersion.save();

            res.sendStatus(200);

        } catch (e) {
            console.log("got e", e);
            res.send({
                error: e instanceof Error ? e.message : e
            });
        }
    }

    /**
     * create a new package
     * @param req - the incoming request
     * @param res - the outgoing response
     */
    async post(req, res) {
        const {name, version, dependencies} = req.body;
        return this.upsert(req, res, name, version, dependencies);
    }

    /**
     * update a specific package
     * @param req - the incoming request
     * @param res - the outgoing response
     */
    async put(req, res) {
        const {name, version, dependencies} = req.body;
        return this.upsert(req, res, name, version, dependencies);
    }

    /**
     * remove a specific package
     * @param req - the incoming request
     * @param res - the outgoing response
     */
    async delete(req, res) {
        const {name, version} = req.params;
        const {cascadeUseless} = req.body;
        const currentUser: User = res.locals.authUser as User;

        try {

            const pack: Package = await Package.findOne({name: name, author: currentUser});

            if (typeof pack === 'undefined') {
                throw new Error("This package does not exists or does not belong to you");
            }

            const packVersion: PackageVersion = await PackageVersion.findOne({where: {package: pack.id, version: version}});

            if (typeof packVersion === 'undefined') {
                throw new Error("This package version does not exists");
            }

            if (cascadeUseless) {
                await packVersion.removeCascade(currentUser);
            } else {
                await packVersion.remove();
            }

            res.sendStatus(200);
        } catch (e) {
            res.send({
                error: e instanceof Error ? e.message : e
            });
        }
    }
}