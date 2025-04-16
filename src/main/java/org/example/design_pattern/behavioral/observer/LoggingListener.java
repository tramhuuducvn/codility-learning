package org.example.design_pattern.behavioral.observer;

import org.example.util.Logger;

public class LoggingListener implements EventListener {
    private String file;
    private String message;

    public LoggingListener(String file, String message) {
        this.file = file;
        this.message = message;
    }

    @Override
    public void update(String data) {
        Logger.log("The file: " + file + String.format(message, data));
    }
}
