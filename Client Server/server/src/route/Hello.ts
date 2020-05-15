/**
 * @author Michaluk David <49762@etu.he2b.be>
 * @author El Amouri Meihdi <49262@etu.he2b.be>
 * @author Rossitto Nicolas <49282@etu.he2b.be>
 * @author Azoud Ismael <42394@etu.he2b.be>
 * @licence GPL-3.0 <https://www.gnu.org/licenses/>
 */

import * as packageFile from "../../package.json"

/**
 * the hello route handler
 * @param req - the incoming request
 * @param res - the outgoing response
 */
const routeHandler = (req, res) => {
    res.send(`Hello from ${packageFile.name}`);
};

/**
 * the hello route handler registerer
 * @param app - the express app
 */
export default app => {
    app.get('/', routeHandler);
};