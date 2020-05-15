/**
 * @author Michaluk David <49762@etu.he2b.be>
 * @author El Amouri Meihdi <49262@etu.he2b.be>
 * @author Rossitto Nicolas <49282@etu.he2b.be>
 * @author Azoud Ismael <42394@etu.he2b.be>
 * @licence GPL-3.0 <https://www.gnu.org/licenses/>
 */

import {exec, spawn} from "child-process-async";
import {readFile, writeFile} from "fs-extra";
import * as path from "path";
import * as PackageFile from "../../package.json";

const PKI_BASE_PATH = "./pki";

/**
 * the public-key infrastructure interface
 * it interfaces various actions with the openssl command-line tool
 */
class PKI {
    private static PRIVATE_KEY_PATH = path.join(PKI_BASE_PATH, `${PackageFile.name}.key`);
    private static CSR_PATH = path.join(PKI_BASE_PATH, `${PackageFile.name}.csr`);
    private static CERT_PATH = path.join(PKI_BASE_PATH, `${PackageFile.name}.crt`);
    private static CA_PATH = path.join(PKI_BASE_PATH, `ca.crt`);

    private pkiAuth: any = {
        key: null,
        cert: null,
        ca: null
    };

    /**
     * generate a private key
     * @param {number} keySize - the size of the key
     */
    private async generatePrivateKey(keySize: number = 2048) {
        await exec(`openssl genrsa -out ${PKI.PRIVATE_KEY_PATH} ${keySize}`);
    }

    /**
     * generate a CSR from a private key
     * @param {string} commonName - the CN to use for the CSR
     */
    private async generateCertificateSigningRequest(commonName: string) {
        await exec(`openssl req -new -key ${PKI.PRIVATE_KEY_PATH} -out ${PKI.CSR_PATH} -subj "/C=BE/ST=Brussels/L=Brussels/O=ESI/CN=${commonName}"`);
    }

    /**
     * generate private key & CSR
     * @param {string} commonName - the CN to use for the CSR
     * @return {Promise<string>} promised CSR content
     */
    public async generate(commonName: string): Promise<string> {
        await this.generatePrivateKey();
        await this.generateCertificateSigningRequest(commonName);

        await this.reloadPKIAuthParams();

        return readFile(PKI.CSR_PATH, "utf8");
    }

    /**
     * write certificate to disk
     * @param {string} cert - the certificate to save
     */
    public async writeCertificate(cert: string) {
        await writeFile(PKI.CERT_PATH, cert);
        await this.reloadPKIAuthParams();
    }

    /**
     * reload PKI authentication parameters
     */
    public async reloadPKIAuthParams() {

        try {
            this.pkiAuth.key = await readFile(PKI.PRIVATE_KEY_PATH);
        } catch (e) {
            this.pkiAuth.key = null;
        }

        try {
            this.pkiAuth.cert = await readFile(PKI.CERT_PATH);
        } catch (e) {
            this.pkiAuth.cert = null;
        }

        try {
            this.pkiAuth.ca = await readFile(PKI.CA_PATH);
        } catch (e) {
            this.pkiAuth.ca = null;
        }
    }

    /**
     * get PKI authentication parameters
     */
    public getPKIAuth() {
        return this.pkiAuth;
    }
}

export default new PKI();