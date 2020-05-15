/**
 * @author Michaluk David <49762@etu.he2b.be>
 * @author El Amouri Meihdi <49262@etu.he2b.be>
 * @author Rossitto Nicolas <49282@etu.he2b.be>
 * @author Azoud Ismael <42394@etu.he2b.be>
 * @licence GPL-3.0 <https://www.gnu.org/licenses/>
 */

import {put} from 'request-promise';
import Server from "./Server";
import PKI from "../service/PKI";

/**
 * the register request maker model
 */
class Register {
    /**
     * execute a register request and generate a CSR and make it signed by the server
     * @param {string} login - the login to use for the accounted to be created
     * @param {string} password - the password to use for the accounted to be created
     */
    async execute(login: string, password: string): Promise<any> {
        const csr = await PKI.generate(login);

        const response = await put(Server.resolve("/user", 3003), {
            json: {
                login,
                password,
                csr
            },
            ca: PKI.getPKIAuth().ca
        });

        await PKI.writeCertificate(response.cert);
    }
}

export default new Register();