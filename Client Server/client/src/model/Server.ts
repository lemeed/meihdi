/**
 * @author Michaluk David <49762@etu.he2b.be>
 * @author El Amouri Meihdi <49262@etu.he2b.be>
 * @author Rossitto Nicolas <49282@etu.he2b.be>
 * @author Azoud Ismael <42394@etu.he2b.be>
 * @licence GPL-3.0 <https://www.gnu.org/licenses/>
 */

import {resolve} from "url";

/**
 * the remote server model
 */
class Server {
    private host: string;

    /**
     * a remote server model constructor
     * @param {string} host - the initial host
     */
    constructor(host: string = null) {
        this.setHost(host);
    }

    /**
     * update the host
     * @param {string} host - the host to set
     */
    setHost(host: string): void {
        this.host = host;
    }

    /**
     * resolve an url on hold host basis and variable url and port
     * @param {string} path - the path to resolve
     * @param {number} port - the port to use for resolution
     */
    resolve(path: string, port: number = 3000): string {
        return resolve(`https://${this.host}:${port}`, path);
    }
}

export default new Server("127.0.0.1");


