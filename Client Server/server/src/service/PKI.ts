/**
 * @author Michaluk David <49762@etu.he2b.be>
 * @author El Amouri Meihdi <49262@etu.he2b.be>
 * @author Rossitto Nicolas <49282@etu.he2b.be>
 * @author Azoud Ismael <42394@etu.he2b.be>
 * @licence GPL-3.0 <https://www.gnu.org/licenses/>
 */

import {exec, spawn} from "child-process-async";
import * as path from "path";

const PKI_BASE_PATH = "./pki";
const CA_CERT_PATH = path.join(PKI_BASE_PATH, "ca.crt");
const CA_KEY_PATH = path.join(PKI_BASE_PATH, "ca.key");

/**
 * the public-key infrastructure interface
 * it interfaces various actions with the openssl command-line tool
 */
class PKI {
    /**
     * sign a certificate signing request
     * @param {string} csr - the certificate signing request to sign
     * @param {number} days - the days of validity of the certificate
     * @return {Promise<string>} the signed certificate
     */
    public async signCertificateSigningRequest(csr: string, days: number = 365): Promise<string> {
        console.log(csr);
        const child = exec(`openssl x509 -req -days ${days} -in /dev/stdin -CA ${CA_CERT_PATH} -CAkey ${CA_KEY_PATH} -CAcreateserial`, {});
        child.stdin.write(csr);
        child.stdin.end();

        const {stdout, stderr} = await child;

        return stdout;
    }

    /**
     * tell if a csr has a valid format
     * @param {string} csr - the certificate signing request to check
     * @return true if the csr is valid, false otherwise
     */
    public isCertificateSigningRequestValid(csr: string): boolean {
        return /^(?:(?!-{3,}(?:BEGIN|END) CERTIFICATE REQUEST)[\s\S])*(-{3,}BEGIN CERTIFICATE REQUEST(?:(?!-{3,}END CERTIFICATE REQUEST)[\s\S])*?-{3,}END CERTIFICATE REQUEST-{3,})(?![\s\S]*?-{3,}BEGIN CERTIFICATE REQUEST[\s\S]+?-{3,}END CERTIFICATE REQUEST[\s\S]*?$)/i.test(csr);
    }
}

export default new PKI();