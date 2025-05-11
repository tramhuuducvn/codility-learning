package org.example.design_pattern.behavioral.command.commands;

import java.util.Stack;

public class CommandHistory {
    private Stack<Command> commands = new Stack<>();

    public void push(Command command) {
        this.commands.add(command);
    }

    public Command pop() {
        return this.commands.pop();
    }

    public boolean isEmpty() {
        return this.commands.isEmpty();
    }
}
