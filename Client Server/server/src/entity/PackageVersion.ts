/**
 * @author Michaluk David <49762@etu.he2b.be>
 * @author El Amouri Meihdi <49262@etu.he2b.be>
 * @author Rossitto Nicolas <49282@etu.he2b.be>
 * @author Azoud Ismael <42394@etu.he2b.be>
 * @licence GPL-3.0 <https://www.gnu.org/licenses/>
 */

import {BaseEntity, Entity, PrimaryGeneratedColumn, Column, ManyToOne, ManyToMany, JoinTable} from "typeorm";

import User from "./User";
import Package from "./Package";

/**
 * a package version is a version of a specific package
 * it could have one ore more dependencies
 * and be a dependency of one or more other packages
 * a package is disabled by default and so not available for download by default
 */
@Entity()
export default class PackageVersion extends BaseEntity {

    @PrimaryGeneratedColumn()
    id: number;

    @ManyToOne(() => Package, pack => pack.versions)
    package: Promise<Package>;

    @Column()
    version: string;

    @ManyToMany(() => PackageVersion, aPackage => aPackage.isDependencyOf)
    @JoinTable()
    dependencies: Promise<Array<PackageVersion>>;

    @ManyToMany(() => PackageVersion, aPackage => aPackage.dependencies)
    isDependencyOf: Promise<Array<PackageVersion>>;

    @Column()
    enabled: boolean = false;

    /**
     * remove the package version and its dependencies if they are no longer needed
     * @param {User} actionUser - the user that requested the removal
     */
    async removeCascade(actionUser: User): Promise<this> {

        const dependencies = await this.dependencies;
        const parentPack = await Package.findOne((await this.package).id);

        await this.remove();

        try {
            for (const partialDependency of dependencies) {
                const dependency = await PackageVersion.findOne(partialDependency.id);
                const dependingPacks = await dependency.isDependencyOf;
                const depInnerPackage = await dependency.package;
                const depPackage = await Package.findOne(depInnerPackage.id);

                if (dependingPacks.length === 0 && depPackage.author.id === actionUser.id) {
                    console.log(depPackage.name, dependingPacks.length);
                    await dependency.removeCascade(actionUser);
                }
            }
        } catch (e) {
            console.log(e);
        }

        if ((await parentPack.versions).length === 0) {
            await parentPack.remove();
        }

        return this;
    }
}
