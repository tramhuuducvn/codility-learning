package org.example.design_pattern.behavioral.chain_of_responsibility.demo_1.middleware;

/**
 * Base middleware class
 */
public abstract class Middleware {
    private Middleware next;

    /**
     * Build chain of middleware objects
     * 
     * @param first
     * @param chain
     * @return middleware
     */
    public static Middleware link(Middleware first, Middleware... chain) {
        Middleware head = first;
        for (Middleware nextInChain : chain) {
            head.next = nextInChain;
            head = nextInChain;
        }
        return first;
    }

    /**
     * Subclasses will implement this method with concrete checks
     * 
     * @param email
     * @param password
     * @return
     */
    public abstract boolean check(String email, String password);

    /**
     * Runs checks in the next object in chain or ends traversing if we're in the
     * last object in chain.
     * 
     * @param email
     * @param password
     * @return
     */
    protected boolean checkNext(String email, String password) {
        if (next == null) {
            return true;
        }

        return next.check(email, password);
    }
}
