package me.rainnny.api.command.impl.arguments;

import me.rainnny.api.command.Command;
import me.rainnny.api.command.CommandProvider;

/**
 * @author Braydon
 */
public class TestArgument {
    @Command(name = "test", description = "I am a test!")
    public void onCommand(CommandProvider command) {
        command.getSender().sendMessage("hi there!");
    }
}