/**
 * @author Michaluk David <49762@etu.he2b.be>
 * @author El Amouri Meihdi <49262@etu.he2b.be>
 * @author Rossitto Nicolas <49282@etu.he2b.be>
 * @author Azoud Ismael <42394@etu.he2b.be>
 * @licence GPL-3.0 <https://www.gnu.org/licenses/>
 */

import Register from "../model/Register";
import Chalk from "chalk";
import * as Inquirer from "inquirer";

/**
 * the register request maker interface
 */
class RegisterInterface {
    /**
     * ask for registration
     * @param {string} login - the login to use to register
     * @param {string} password - the password to use to register
     */
    public async register(login: string, password: string): Promise<void> {
        try {

            const promptResponse = await Inquirer.prompt([
                {type: 'confirm', name: 'next', message: 'Registering will replace already existing local PKI related files (if there are some). Continue?', default: true}
            ]);

            if (promptResponse.next) {
                await Register.execute(login, password);
                console.log(Chalk.green("Register successful"));
            }

        } catch (e) {
            console.log(Chalk.red("Register failed"));
            console.log(Chalk.red(e));
        }
    }
}

export default new RegisterInterface();