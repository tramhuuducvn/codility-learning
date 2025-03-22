package org.example.design_pattern.behavioral.observer;

import org.example.util.Logger;

public class EmailAlertListener implements EventListener {
    private String email;
    private String message;

    public EmailAlertListener(String email, String message) {
        this.email = email;
        this.message = message;
    }

    @Override
    public void update(String data) {
        Logger.log("Sent email to " + email + " with message " + String.format(message, data));
    }

}
