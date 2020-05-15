/**
 * @author Michaluk David <49762@etu.he2b.be>
 * @author El Amouri Meihdi <49262@etu.he2b.be>
 * @author Rossitto Nicolas <49282@etu.he2b.be>
 * @author Azoud Ismael <42394@etu.he2b.be>
 * @licence GPL-3.0 <https://www.gnu.org/licenses/>
 */

import {get} from 'request-promise';
import Server from "./Server";
import Auth from "./Auth";
import PKI from "../service/PKI";

/**
 * the login request maker model
 */
class Login {
    /**
     * execute a login request and save the credentials for further requests if the login succeeded
     * @param {string} login - the login to use to authenticate
     * @param {string} password - the password to use to authenticate
     */
    async execute(login: string, password: string): Promise<any> {
        await PKI.reloadPKIAuthParams();

        const response = await get(Server.resolve("/auth-test"), {
            auth: {
                'user': login,
                'pass': password
            },
            ...PKI.getPKIAuth()
        });

        Auth.setAuth(login, password);
        console.log(response);
    }
}

export default new Login();