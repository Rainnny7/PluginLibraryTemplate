package me.rainnny.api.command.impl;

import me.rainnny.api.command.Command;
import me.rainnny.api.command.CommandProvider;

/**
 * @author Braydon
 * The method for this command is empty as a help menu
 * is automatically sent because {@code displayHelp} in the
 * annotation is true, and because this command has arguments
 * registered
 */
public class ExampleCommand {
    @Command(name = "example")
    public void onCommand(CommandProvider command) {}
}