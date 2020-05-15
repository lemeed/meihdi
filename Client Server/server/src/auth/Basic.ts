/**
 * @author Michaluk David <49762@etu.he2b.be>
 * @author El Amouri Meihdi <49262@etu.he2b.be>
 * @author Rossitto Nicolas <49282@etu.he2b.be>
 * @author Azoud Ismael <42394@etu.he2b.be>
 * @licence GPL-3.0 <https://www.gnu.org/licenses/>
 */

import * as basicAuth from "basic-auth";
import User from "../entity/User";
import * as bcrypt from "bcrypt";
import {Connection, getConnection} from "typeorm";

/**
 * a basic authentication handler
 */
export default class BasicAuth {
    /**
     * a basic auth constructor
     * @param app - the express app where to register the middleware
     */
    constructor(app) {
        app.use(this.middleWare);
    }

    /**
     * the basic auth middleware compatible with express
     * @param req - the incoming request
     * @param res - the outgoing response
     * @param {function} next - a reference to the next middleware
     */
    async middleWare(req, res, next) {
        try {
            const auth = basicAuth(req);

            if (!auth || !auth.name || !auth.pass) {
                throw new Error("Wrong authentication");
            }

            const {subject: {CN: clientCertificateCommonName}} = res.socket.getPeerCertificate(true);

            // block access if the certificate CN does not match the supplied login
            if (clientCertificateCommonName !== auth.name) {
                throw new Error("Your certificate common name does not match your login");
            }


            const connection: Connection = getConnection();

            const user: User = await connection
                .getRepository(User)
                .createQueryBuilder("user")
                .addSelect("user.password")
                .where("user.login = :login", {login: auth.name})
                .andWhere("user.enabled = :enabled", {enabled: true})
                .getOne();

            if (typeof user === 'undefined') {
                throw new Error("User does not exists or is not enabled yet");
            }

            const matchPwd: boolean = await bcrypt.compare(auth.pass, user.password);

            if (!matchPwd) {
                throw new Error("Wrong authentication");
            }

            res.locals.authUser = user;

            next();
        } catch (e) {
            console.log(e);
            res.status(401);
            return res.send({
                error: e instanceof Error ? e.message : e
            });
        }
    }
}