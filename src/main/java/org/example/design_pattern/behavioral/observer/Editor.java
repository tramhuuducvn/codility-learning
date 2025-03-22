package org.example.design_pattern.behavioral.observer;

public class Editor {
    public EventManager eventManager;
    private String file;

    public Editor() {
        eventManager = new EventManager();
    }

    public void openFile(String path) {
        this.file = path;
        eventManager.notify(EventType.OPEN, this.file);
    }

    public void saveFile(String path) {
        this.file = path;
        eventManager.notify(EventType.SAVE, this.file);
    }

    public static void main(String[] args) {
        Editor editor = new Editor();
        LoggingListener loggingListener = new LoggingListener("file B", " the file %s has been updated");
        EmailAlertListener emailAlertListener = new EmailAlertListener("tramhuuducz@gmail.com",
                " the file %s has been updated");

        editor.eventManager.subcribe(EventType.OPEN, loggingListener);
        editor.eventManager.subcribe(EventType.SAVE, loggingListener);
        editor.eventManager.subcribe(EventType.SAVE, emailAlertListener);

        editor.openFile("hello.java");
    }
}
