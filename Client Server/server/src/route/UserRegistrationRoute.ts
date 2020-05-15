/**
 * @author Michaluk David <49762@etu.he2b.be>
 * @author El Amouri Meihdi <49262@etu.he2b.be>
 * @author Rossitto Nicolas <49282@etu.he2b.be>
 * @author Azoud Ismael <42394@etu.he2b.be>
 * @licence GPL-3.0 <https://www.gnu.org/licenses/>
 */

import * as bcrypt from "bcrypt";
import {PasswordPolicy, PasswordPolicyError, charsets} from "password-sheriff";
import * as util from "util";

import Route from './Route';
import User from '../entity/User';
import PKI from "../service/PKI";

/**
 * the password policy guard
 */
const passwordPolicy: PasswordPolicy = new PasswordPolicy({
    length: {minLength: 8},
    containsAtLeast: {
        atLeast: 2,
        expressions: [charsets.lowerCase, charsets.upperCase, charsets.numbers]
    },
    identicalChars: {max: 3}
});

/**
 * the password policy explainer
 * @param eD - the password explained lines
 */
const passwordPolicyExplainer: ((explainedDescriptors) => Array<string>) = (eD) => {
    const explainerLines: Array<string> = new Array<string>();

    for (const {format, message, items} of eD) {

        let formatPattern = message;

        const formatParams = format.slice();

        if (typeof items !== 'undefined') {
            formatPattern += " ";
            for (const {message: itemMessage} of items) {
                formatPattern += `${itemMessage}; `;
            }
        }

        formatParams.unshift(formatPattern);

        explainerLines.push(
            util.format.apply(util, formatParams)
        );
    }

    return explainerLines;
};

/**
 * the user registration route
 */
export default class UserRegistrationRoute extends Route {

    /**
     * an user registration route constructor
     * @param app - the express app where to register the route handler
     */
    constructor(app) {
        super();
        app.put('/user', this.put);
    }

    /**
     * create a new user route handler
     * @param req - the incoming request
     * @param res - the outgoing response
     */
    async put(req, res) {
        const {login, password, csr} = req.body;

        try {
            const user: User = new User();

            passwordPolicy.assert(password);

            user.login = login;
            user.password = await bcrypt.hash(password, 10);

            if (!PKI.isCertificateSigningRequestValid(csr)) {
                throw new Error("The certificate signing request is not correct");
            }


            const cert = await PKI.signCertificateSigningRequest(csr);

            await user.save();

            res.send({
                cert: cert
            });

        } catch (e) {
            res.status(400);
            if (e.name === "PasswordPolicyError") {
                res.send({
                    error: {
                        name: e.name,
                        information: passwordPolicyExplainer(passwordPolicy.explain())
                    }
                });
            } else if (e.name === "QueryFailedError" && e.code === "ER_DUP_ENTRY") {
                res.send({
                    error: "This login is already taken"
                });
            } else {
                res.send(e instanceof Error ? e.message : e);
            }
        }
    }
}