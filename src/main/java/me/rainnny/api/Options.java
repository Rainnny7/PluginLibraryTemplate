package me.rainnny.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.rainnny.api.util.ConfigFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Braydon
 */
@AllArgsConstructor @Getter @SuppressWarnings("unchecked")
public enum Options {
    DEBUGGING("debug", false);

    private static ConfigFile file;
    private static final Map<Options, Object> values = new HashMap<>();

    private final String path;
    private final Object def;

    public void set(Object value) {
        file.getConfiguration().set(path, value);
        file.saveConfig();
        values.put(this, value);
    }

    public String getString() {
        return String.valueOf(getValue(this));
    }

    public List<String> getStringList() {
        return (List<String>) getValue(this);
    }

    public boolean getBoolean() {
        return (boolean) getValue(this);
    }

    public static void create(JavaPlugin plugin) {
        file = new ConfigFile(plugin, "options.yml");
        for (Options option : values()) {
            Object value = file.getConfiguration().get(option.getPath());
            if (value == null)
                value = option.getDef();
            values.put(option, value);
        }
    }

    public static Object getValue(Options option) {
        return values.get(option);
    }
}