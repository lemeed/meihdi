/**
 * @author Michaluk David <49762@etu.he2b.be>
 * @author El Amouri Meihdi <49262@etu.he2b.be>
 * @author Rossitto Nicolas <49282@etu.he2b.be>
 * @author Azoud Ismael <42394@etu.he2b.be>
 * @licence GPL-3.0 <https://www.gnu.org/licenses/>
 */

import {BaseEntity, Entity, PrimaryGeneratedColumn, Column, Unique, OneToMany} from "typeorm";
import Package from "./Package";

/**
 * an user of the package manager system
 * it can maintain multiple packages by submitting new ones, editing or deleting existing ones
 * if the user permission allow it
 * an user that just have been register is disabled by default, waiting to be enabled by a sysadmin
 * an user cannot submit package by default, he has to ask the permission to a sysadmin
 */
@Entity()
@Unique(["login"])
export default class User extends BaseEntity {

    @PrimaryGeneratedColumn()
    id: number;

    @Column()
    login: string;

    @Column({type: "char", length: 60, select: false})
    password: string;

    @OneToMany(type => Package, aPackage => aPackage.author)
    packages: Array<Package>;

    @Column()
    enabled: boolean = false;

    @Column()
    canUploadPackages: boolean = false;

}
