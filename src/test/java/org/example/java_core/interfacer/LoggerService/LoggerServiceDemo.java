package org.example.java_core.interfacer.LoggerService;

import org.example.util.Logger;

public class LoggerServiceDemo {
    public LoggerService loggerService;

    public LoggerServiceDemo() {
        this.loggerService = new LoggerService() {

            @Override
            public void log(String message) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'log'");
            }

            @Override
            public void debug(String message) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'debug'");
            }
            
        };
    }

    private void handleLog(String message) {
        Logger.print("[Handle Log]: " + message);
    }

    public static void main(String[] args) {
        LoggerServiceDemo demo = new LoggerServiceDemo();
        demo.loggerService.log("Hello");
    }
}
