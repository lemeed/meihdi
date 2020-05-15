/**
 * @author Michaluk David <49762@etu.he2b.be>
 * @author El Amouri Meihdi <49262@etu.he2b.be>
 * @author Rossitto Nicolas <49282@etu.he2b.be>
 * @author Azoud Ismael <42394@etu.he2b.be>
 * @licence GPL-3.0 <https://www.gnu.org/licenses/>
 */

import {BaseEntity, Entity, PrimaryGeneratedColumn, Column, ManyToOne, Unique, OneToMany, Connection, getConnection} from "typeorm";

import User from "./User";
import PackageVersion from "./PackageVersion";

/**
 * a package is a piece of software maintained by an user
 * and one or more versions
 */
@Entity()
@Unique(["name"])
export default class Package extends BaseEntity {

    @PrimaryGeneratedColumn()
    id: number;

    @Column()
    name: string;

    @ManyToOne(() => User, user => user.packages, {eager: true})
    author: User;

    @OneToMany(() => PackageVersion, aPackage => aPackage.package)
    versions: Promise<Array<Package>>;

    /**
     * get the latest version of the current package
     * @return {Promise<PackageVersion>} promised version of the package
     */
    public async getLatest(): Promise<PackageVersion> {
        const connection: Connection = getConnection();
        return await connection
            .getRepository(PackageVersion)
            .createQueryBuilder("packageVersion")
            .innerJoinAndSelect("packageVersion.package", "package")
            .where("package.id = :id", {id: this.id})
            .orderBy("packageVersion.version", "DESC")
            .getOne();
    }
}
