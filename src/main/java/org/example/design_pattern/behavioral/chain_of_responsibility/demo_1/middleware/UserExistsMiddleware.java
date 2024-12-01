package org.example.design_pattern.behavioral.chain_of_responsibility.demo_1.middleware;

import org.example.design_pattern.behavioral.chain_of_responsibility.demo_1.server.Server;
import org.example.util.Logger;

public class UserExistsMiddleware extends Middleware {

    private Server server;

    public UserExistsMiddleware(Server server) {
        this.server = server;
    }

    @Override
    public boolean check(String email, String password) {
        if (!server.hasEmail(email)) {
            Logger.log("This email is not registered!");
            return false;
        }
        if (!server.isValidPassword(email, password)) {
            Logger.log("Wrong password");
            return false;
        }

        return true;
    }

}
