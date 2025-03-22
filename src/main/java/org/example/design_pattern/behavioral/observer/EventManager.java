package org.example.design_pattern.behavioral.observer;

import java.util.HashMap;
import java.util.Map;

public class EventManager {
    private Map<EventType, EventListener> listeners;

    public EventManager() {
        listeners = new HashMap<>();
    }

    public void subcribe(EventType type, EventListener listener) {
        this.listeners.put(type, listener);
    }

    public void unsubcribe(EventType type, EventListener listener) {
        this.listeners.remove(type, listener);
    }

    public void notify(EventType type, String data) {
        listeners.forEach((k, v) -> {
            v.update(data);
        });
    }
}
