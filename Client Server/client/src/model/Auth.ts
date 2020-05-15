/**
 * @author Michaluk David <49762@etu.he2b.be>
 * @author El Amouri Meihdi <49262@etu.he2b.be>
 * @author Rossitto Nicolas <49282@etu.he2b.be>
 * @author Azoud Ismael <42394@etu.he2b.be>
 * @licence GPL-3.0 <https://www.gnu.org/licenses/>
 */

/**
 * the auth model holds the auth-related tuple (login, password)
 */
class Auth {
    private login: string;
    private password: string;

    /**
     * update the hold auth-related tuple
     * @param {string} login - the login to use for authentication
     * @param {string} password - the password to use for authentication
     */
    setAuth(login: string, password: string) {
        this.login = login;
        this.password = password;
    }

    /**
     * get the auth data in a format suitable for http request
     * @return {Object} an object holding the auth data
     */
    getRequestBasicAuth(): any {
        return {
            auth: {
                'user': this.login,
                'pass': this.password
            }
        };
    }
}

export default new Auth();