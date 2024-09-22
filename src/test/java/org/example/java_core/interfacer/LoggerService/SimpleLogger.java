package org.example.java_core.interfacer.LoggerService;

public class SimpleLogger implements LoggerService { 
    @Override
    public void log(String message) {
        System.out.println("[SimpleLogger]: " + message);
    }

    @Override
    public void debug(String message) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'debug'");
    }

}
