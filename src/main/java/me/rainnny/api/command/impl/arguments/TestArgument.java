package me.rainnny.api.command.impl.arguments;

import me.rainnny.api.command.Command;
import me.rainnny.api.command.CommandProvider;
import me.rainnny.api.command.TabComplete;
import me.rainnny.api.menu.impl.ExampleMenu;

import java.util.Arrays;
import java.util.List;

/**
 * @author Braydon
 */
public class TestArgument {
    @Command(name = "test", description = "I am a test!", playersOnly = true)
    public void onCommand(CommandProvider command) {
        new ExampleMenu(command.getPlayer()).open();
        // TODO: 8/30/20 - open example menu
    }

    @TabComplete(name = "test")
    public List<String> onTabComplete(CommandProvider command) {
        return Arrays.asList("test", "testing", "hello", "hi");
    }
}