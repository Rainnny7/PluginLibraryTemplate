package me.rainnny.api.util;

import lombok.Getter;
import net.md_5.bungee.api.chat.*;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * @author Braydon
 */
@Getter
public class JsonMessage {
    private final TextComponent component;

    /**
     * Create a new json message
     */
    public JsonMessage() {
        component = new TextComponent();
    }

    /**
     * Create a json message with a string
     * @param text - The text you would like to use in your json message
     */
    public JsonMessage(String text) {
        component = new TextComponent(text);
    }

    /**
     * Append an extra text component to the existing text component
     * @param component - The component to append
     * @return the json builder
     */
    public JsonMessage addExtra(TextComponent component) {
        this.component.addExtra(component);
        return this;
    }

    /**
     * Append an extra string to the existing text component
     * @param s - The string to append
     * @return the json builder
     */
    public JsonMessage addExtra(String s) {
        component.addExtra(s);
        return this;
    }

    /**
     * Add a click event to the last appended component
     * @param action - The action that gets called when you click on the json message
     * @param value - The value of the action (E.g: OPEN_URL -> "https://google.ca")
     * @return the json builder
     */
    public JsonMessage withClick(ClickAction action, String value) {
        component.setClickEvent(new ClickEvent(ClickEvent.Action.valueOf(action.name()), value));
        return this;
    }

    /**
     * Add a hover message to the last appended component
     * @param list - The list of strings you would like to append
     * @return the json builder
     */
    public JsonMessage withHover(List<String> list) {
        return withHover(HoverAction.SHOW_TEXT, new ComponentBuilder(MiscUtils.arrayToString(list)).create());
    }

    /**
     * Add a hover message to the last appended component
     * @param array - The array of strings you would like to append
     * @return the json builder
     */
    public JsonMessage withHover(String... array) {
        return withHover(HoverAction.SHOW_TEXT, new ComponentBuilder(MiscUtils.arrayToString(array)).create());
    }

    /**
     * Add a hover message to the last appended component
     * @param s - The string you would like to append
     * @return the json builder
     */
    public JsonMessage withHover(String s) {
        return withHover(HoverAction.SHOW_TEXT, new ComponentBuilder(s).create());
    }

    /**
     * Add a hover event to the last appended component
     * @param action - The action that gets called when you hover over the json message
     * @param value - The value of the action (E.g: SHOW_TEXT -> "Hello world!")
     * @return the json builder
     */
    public JsonMessage withHover(HoverAction action, BaseComponent[] value) {
        component.setHoverEvent(new HoverEvent(HoverEvent.Action.valueOf(action.name()), value));
        return this;
    }

    public void send(CommandSender sender) {
        if (sender instanceof ConsoleCommandSender)
            throw new IllegalStateException("Cannot send json message to terminal");
        send((Player) sender);
    }

    /**
     * Send the json message to the provided player
     * @param player - The player you would like to send the json message to
     */
    public void send(Player player) {
        player.sendMessage(component);
    }

    public enum ClickAction {
        OPEN_URL,
        OPEN_FILE,
        RUN_COMMAND,
        SUGGEST_COMMAND,
        CHANGE_PAGE
    }

    public enum HoverAction {
        SHOW_TEXT,
        SHOW_ACHIEVEMENT,
        SHOW_ITEM,
        SHOW_ENTITY
    }
}