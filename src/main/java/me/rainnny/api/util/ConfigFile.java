package me.rainnny.api.util;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.sun.org.apache.xml.internal.security.utils.Constants.configurationFile;

/**
 * @author Braydon
 */
@Getter
public class ConfigFile {
    private final JavaPlugin plugin;
    private final String fileName;
    private final WrappedFile file;
    private FileConfiguration configuration;

    /**
     * @param plugin - The plugin you would like to create the configuration file for
     * @param fileName - The name of the configuration file (E.g: options.yml)
     */
    public ConfigFile(JavaPlugin plugin, String fileName) {
        this(plugin, fileName, null);
    }

    /**
     * @param plugin - The plugin you would like to create the configuration file for
     * @param fileName - The name of the configuration file (E.g: options.yml)
     * @param folder - The folder you would like to create the configuration file in
     */
    public ConfigFile(JavaPlugin plugin, String fileName, String folder) {
        this.plugin = plugin;
        this.fileName = fileName;
        file = new WrappedFile(new File(plugin.getDataFolder() + (folder == null ? "" : "/" + folder), fileName));
        saveDefaultConfig();
    }

    /**
     * Get Bukkit's FileConfiguration from the file
     * @return Bukkit's FileConfiguration
     */
    public FileConfiguration getConfiguration() {
        if (configuration == null)
            reloadConfig();
        return configuration;
    }

    /**
     * Reloads the configuration file
     */
    public void reloadConfig() {
        configuration = YamlConfiguration.loadConfiguration(file.getParent());
        InputStream defConfigStream = plugin.getResource(fileName);
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream));
            configuration.setDefaults(defConfig);
        }
    }

    /**
     * Saves the current configuration file
     */
    public void saveConfig() {
        if (file.getParent() != null && configuration != null) {
            try {
                getConfiguration().save(configurationFile);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Copies the contents from resources/configName.yml to the newly
     * created configuration file
     */
    public void saveDefaultConfig() {
        if (!file.getParent().exists())
            plugin.saveResource(fileName, false);
    }

    /**
     * Deletes the configuration file
     */
    public void deleteConfig() {
        if (!file.getParent().exists())
            return;
        file.delete();
    }
}