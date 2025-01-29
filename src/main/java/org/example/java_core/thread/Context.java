package org.example.java_core.thread;

public class Context {
    private String username;

    public Context(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "Context [username=" + username + "]";
    }
}
