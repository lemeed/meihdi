/**
 * @author Michaluk David <49762@etu.he2b.be>
 * @author El Amouri Meihdi <49262@etu.he2b.be>
 * @author Rossitto Nicolas <49282@etu.he2b.be>
 * @author Azoud Ismael <42394@etu.he2b.be>
 * @licence GPL-3.0 <https://www.gnu.org/licenses/>
 */

import "reflect-metadata"; // needed to properly init the ORM
import {createConnection} from "typeorm";
import * as fs from "fs";
import * as https from "https";
import * as express from "express";
import HelloRoute from "./route/Hello";
import UserRegistrationRoute from "./route/UserRegistrationRoute";
import PackageRoute from "./route/Package";
import BasicAuth from "./auth/Basic";


const rateLimit = require("express-rate-limit");


const tlsOptions = {
    key: fs.readFileSync('pki/server.key'),
    cert: fs.readFileSync('pki/server.crt'),
    ca: fs.readFileSync('pki/ca.crt')
};

/**
 * server bootstrap
 */
(async () => {

    // create connection to the database trough the ORM
    await createConnection();

    /**
     * the registration app is served on the port 3003
     * it's secured by TLS
     * and it's main purpose is to allow client without a signed client cert to register themselves
     * and to get their certificate signed by the server
     */
    const registrationApp = express();
    registrationApp.use(express.json());

    // prevent brute-force attack with rate limiter
    registrationApp.use(rateLimit({
        windowMs: 15 * 60 * 1000, // 15 minutes
        max: 100 // limit each IP to 100 requests per windowMs
    }));

    new HelloRoute(registrationApp);
    new UserRegistrationRoute(registrationApp);

    https.createServer(tlsOptions, registrationApp).listen(3003, () => console.log("Registration server started"));


    /**
     * the main app is served on the port 3000
     * it's secured by TLS
     * and it requires every user to authenticate itself with a client certificate that was signed earlier by this server
     * in addition to a basic authentication
     */
    const mainApp = express();
    mainApp.use(express.json());

    // prevent brute-force attack with rate limiter
    mainApp.use(rateLimit({
        windowMs: 15 * 60 * 1000, // 15 minutes
        max: 100 // limit each IP to 100 requests per windowMs
    }));

    new HelloRoute(mainApp);
    new BasicAuth(mainApp);
    mainApp.get('/auth-test', (req, res) => res.send(200));
    new PackageRoute(mainApp);

    https.createServer({
        ...tlsOptions,
        requestCert: true,
        rejectUnauthorized: true
    }, mainApp).listen(3000, () => console.log("Main server started"));

})();