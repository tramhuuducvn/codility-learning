package org.example.design_pattern.behavioral.chain_of_responsibility.demo_1.middleware;

import org.example.util.Logger;

/**
 * Concrete handler. Checks whether there are two many failed login requests.
 */
public class ThrottlingMiddleware extends Middleware {
    private int requestPerMinute;
    private int request;
    private long currentTime;

    public ThrottlingMiddleware(int requestPerMinute) {
        this.requestPerMinute = requestPerMinute;
        this.currentTime = System.currentTimeMillis();
    }

    /**
     * Please, note that checkNext() call can be inserted both in the beginning
     * of this method and in the end.
     *
     * This gives much more flexibility than a simple loop over all middleware
     * objects. For instance, an element of a chain can change the order of
     * checks by running its check after all other checks.
     */
    @Override
    public boolean check(String email, String password) {
        if (System.currentTimeMillis() > this.currentTime + 60_000) {
            request = 0;
            currentTime = System.currentTimeMillis();
        }

        request++;
        if (request > requestPerMinute) {
            Logger.log("Request limit exceeded!");
            Thread.currentThread().stop();
        }

        return checkNext(email, password);
    }

}
