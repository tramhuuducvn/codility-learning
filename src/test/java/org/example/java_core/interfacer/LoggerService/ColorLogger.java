package org.example.java_core.interfacer.LoggerService;

import org.example.util.Logger;

public class ColorLogger implements LoggerService {

    @Override
    public void log(String message) {
        Logger.log("[ColorLogger]: " + message);
    }

    @Override
    public void debug(String message) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'debug'");
    }
}
