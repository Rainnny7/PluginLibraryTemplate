package me.rainnny.api.command.impl.arguments;

import me.rainnny.api.command.Command;
import me.rainnny.api.command.CommandProvider;
import me.rainnny.api.command.TabComplete;

import java.util.Arrays;
import java.util.List;

/**
 * @author Braydon
 */
public class TestArgument {
    @Command(name = "test", description = "I am a test!")
    public void onCommand(CommandProvider command) {
        command.getSender().sendMessage("hi there!");
    }

    @TabComplete(name = "test")
    public List<String> onTabComplete(CommandProvider command) {
        return Arrays.asList("test", "testing", "hello", "hi");
    }
}