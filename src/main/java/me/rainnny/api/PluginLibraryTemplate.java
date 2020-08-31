package me.rainnny.api;

import lombok.Getter;
import me.rainnny.api.command.CommandHandler;
import me.rainnny.api.command.impl.ExampleCommand;
import me.rainnny.api.command.impl.arguments.TestArgument;
import me.rainnny.api.handler.BungeeHandler;
import me.rainnny.api.hotbar.HotbarManager;
import me.rainnny.api.menu.MenuManager;
import me.rainnny.api.protocol.ProtocolHandler;
import me.rainnny.api.util.MiscUtils;
import me.rainnny.api.util.VaultAttachment;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Bukkit.getPluginManager;

/**
 * @author Braydon
 */
@Getter
public class PluginLibraryTemplate extends JavaPlugin implements Listener {
    @Getter private static PluginLibraryTemplate instance;

    private final List<Plugin> hookedPlugins = new ArrayList<>();

    @Override
    public void onEnable() {
        instance = this;
        Options.create(this);
        boolean debugging = Options.DEBUGGING.getBoolean();

        if (debugging)
            Bukkit.getLogger().info("Setting up " + getName() + " v" + getDescription().getVersion() + "...");

        if (debugging)
            Bukkit.getLogger().info("Initializing commands...");
        new CommandHandler(this);

        // If you want your plugin library to have any commands
        // associated with it, I would recommend registering
        // them inside of the CommandHandler class instead
        // of the main class
        CommandHandler.addCommand(new ExampleCommand());
        CommandHandler.addArgument(ExampleCommand.class, new TestArgument());

        if (debugging)
            Bukkit.getLogger().info("Initializing protocol...");
        new ProtocolHandler(this);

        if (debugging)
            Bukkit.getLogger().info("Initializing menus...");
        new MenuManager(this);

        if (debugging)
            Bukkit.getLogger().info("Initializing hotbar...");
        new HotbarManager(this);

        if (getPluginManager().isPluginEnabled("Vault"))
            new VaultAttachment();

        new BungeeHandler(this);

        if (debugging)
            Bukkit.getLogger().info("Loaded " + getName() + "! Using server version: " + Bukkit.getVersion());

        getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        instance = null;

        if (Options.DEBUGGING.getBoolean())
            Bukkit.getLogger().info("Disabling depending plugins...");

        for (Plugin plugin : hookedPlugins) {
            getPluginManager().disablePlugin(plugin);
            if (Options.DEBUGGING.getBoolean())
                Bukkit.getLogger().info("Disabled '" + plugin.getName() + "' automatically due to " + getName() + " disabling...");
        }
        hookedPlugins.clear();
    }

    /**
     * When a plugin enables and it's depended on the library, we want
     * to add it to the {@code hookedPlugins} list
     */
    @EventHandler
    private void onPluginEnable(PluginEnableEvent event) {
        Plugin plugin = event.getPlugin();
        if (!MiscUtils.dependsOn(plugin, getName()))
            return;
        hookedPlugins.add(plugin);
        if (Options.DEBUGGING.getBoolean())
            Bukkit.getLogger().info("Plugin '" + plugin.getName() + "' was hooked into " + getName());
    }

    /**
     * When a plugin disables and it's inside of the {@code hookedPlugins}
     * list, we wanna remove it and then log it to the terminal
     * if debugging is enabled
     */
    @EventHandler
    private void onPluginDisable(PluginDisableEvent event) {
        Plugin plugin = event.getPlugin();
        if (hookedPlugins.remove(plugin)) {
            if (Options.DEBUGGING.getBoolean())
                Bukkit.getLogger().info("Removed '" + plugin.getName() + "' hook from " + getName());
        }
    }
}