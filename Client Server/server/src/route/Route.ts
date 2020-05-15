/**
 * @author Michaluk David <49762@etu.he2b.be>
 * @author El Amouri Meihdi <49262@etu.he2b.be>
 * @author Rossitto Nicolas <49282@etu.he2b.be>
 * @author Azoud Ismael <42394@etu.he2b.be>
 * @licence GPL-3.0 <https://www.gnu.org/licenses/>
 */

/**
 * a route interface
 * a route is a path on the API
 * it can be called with multiple verbs
 * and this interface defines the default ones
 */
export default abstract class Route {
    /**
     * a get action mostly read data
     * @param req - the incoming request
     * @param res - the outgoing response
     * @param {function} next - a reference to the next middleware
     */
    async get(req, res, next): Promise<void> {
        next()
    };

    /**
     * a post action mostly creates data
     * @param req - the incoming request
     * @param res - the outgoing response
     * @param {function} next - a reference to the next middleware
     */
    async post(req, res, next): Promise<void> {
        next()
    };

    /**
     * a put action mostly edit data
     * @param req - the incoming request
     * @param res - the outgoing response
     * @param {function} next - a reference to the next middleware
     */
    async put(req, res, next): Promise<void> {
        next()
    };

    /**
     * a delete action mostly removes data
     * @param req - the incoming request
     * @param res - the outgoing response
     * @param {function} next - a reference to the next middleware
     */
    async delete(req, res, next): Promise<void> {
        next()
    };
}