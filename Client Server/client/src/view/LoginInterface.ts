/**
 * @author Michaluk David <49762@etu.he2b.be>
 * @author El Amouri Meihdi <49262@etu.he2b.be>
 * @author Rossitto Nicolas <49282@etu.he2b.be>
 * @author Azoud Ismael <42394@etu.he2b.be>
 * @licence GPL-3.0 <https://www.gnu.org/licenses/>
 */

import Login from "../model/Login";
import Chalk from "chalk";

/**
 * the login request maker interface
 */
class LoginInterface {
    /**
     * ask for login
     * @param {string} login - the login to use to authenticate
     * @param {string} password - the password to use to authenticate
     */
    public async login(login: string, password: string): Promise<void> {
        try {
            await Login.execute(login, password);
            console.log(Chalk.green("Login successful"));
        } catch (e) {
            console.log(Chalk.red("Login failed"));
            console.log(Chalk.red(e));
        }
    }
}

export default new LoginInterface();