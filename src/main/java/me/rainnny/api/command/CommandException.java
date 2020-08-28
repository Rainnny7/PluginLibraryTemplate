package me.rainnny.api.command;

/**
 * @author Braydon
 * This is used to have custom error messages in commands.
 * If this exception is thrown, it will display the error
 * message in chat as red
 */
public class CommandException extends Exception {
    public CommandException(String message) {
        super(message);
    }
}