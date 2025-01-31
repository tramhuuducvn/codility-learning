package org.example.java_core.thread;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.example.util.Logger;

public class SharedMapWithUserContext implements Runnable {
    private Integer userId;
    public static ThreadLocal<Context> userContext = new ThreadLocal<>();
    private UserRepository userRepository = new UserRepository();

    public SharedMapWithUserContext(Integer userId) {
        this.userId = userId;
    }

    @Override
    public void run() {
        String userName = userRepository.getUserNameForUserId(userId);
        userContext.set(new Context(userName));
        Logger.log("thread context for given userId: " + userId + " is: " + userContext.get());
    }

}
