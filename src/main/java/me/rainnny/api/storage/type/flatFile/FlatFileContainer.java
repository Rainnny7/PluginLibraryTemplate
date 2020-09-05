package me.rainnny.api.storage.type.flatFile;

import me.rainnny.api.PluginLibraryTemplate;
import me.rainnny.api.network.wrapped.WrappedClass;
import me.rainnny.api.storage.StorageContainer;
import me.rainnny.api.storage.StorageElement;
import me.rainnny.api.util.ConfigFile;
import me.rainnny.api.util.Tuple;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Braydon
 * This is an extension of the {@code StorageContainer}.
 * This container makes it easier to load and save data from
 * a file.
 */
public class FlatFileContainer<E> extends StorageContainer<E> {
    protected final ConfigFile file;
    protected final Class<? extends FlatFileToken> token;

    /**
     * Create a new flat file container
     * @param file - The file you would like to associate with your container
     * @param token - The token of the container
     */
    public FlatFileContainer(ConfigFile file, @Nullable Class<? extends FlatFileToken> token) {
        this(StorageContainer.DEFAULT_CAPACITY, file, token);
    }

    /**
     * Create a new flat file container
     * @param capacity - The initial capacity of the container
     * @param file - The file you would like to associate with your container
     */
    public FlatFileContainer(int capacity, ConfigFile file) {
        this(capacity, file, null);
    }

    /**
     * Create a new flat file container
     * @param capacity - The initial capacity of the container
     * @param file - The file you would like to associate with your container
     * @param token - The token of the container
     */
    public FlatFileContainer(int capacity, ConfigFile file, @Nullable Class<? extends FlatFileToken> token) {
        super(capacity);
        this.file = file;
        this.token = token;
    }

    /**
     * This will clear the container of it's elements and loop
     * through the configuration section matching the provided
     * sectionName. If it finds any keys under the section, it will
     * attempt to load the key and it's value from the file into the
     * container
     * @param sectionName - The name of the section you would like to load elements from
     */
    public void load(String sectionName) {
        cleanup();

        FileConfiguration configuration = file.getConfiguration();
        ConfigurationSection section = configuration.getConfigurationSection(sectionName);
        if (section == null)
            throw new IllegalArgumentException("Section with name '" + sectionName + "' was not found");
        PluginLibraryTemplate.getInstance().getTimings().start("flatFileContainer:load:" + file.getFileName());
        for (String key : section.getKeys(false)) {
            if (token == null)
                add(key, (E) configuration.get(sectionName + "." + key));
            else {
                try {
                    WrappedClass wrapped = new WrappedClass(token);
                    FlatFileToken token = (FlatFileToken) wrapped.getParent().newInstance();
                    List<Tuple<String, Object>> entries = new ArrayList<>();
                    List<String> keys = token.getKeys();
                    if (keys == null || (keys.isEmpty()))
                        entries.add(new Tuple<>(key, configuration.get(sectionName + "." + key)));
                    else {
                        for (String tokenKey : keys) {
                            entries.add(new Tuple<>(tokenKey, configuration.getString(sectionName + "." + key + "." + tokenKey)));
                        }
                    }
                    add(key, wrapped.getMethodByName("load", String.class, FlatFileEntries.class)
                            .invoke(token, key, new FlatFileEntries(entries)));
                } catch (InstantiationException | IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        }
        PluginLibraryTemplate.getInstance().getTimings().stop("flatFileContainer:load:" + file.getFileName());
    }

    /**
     * For saving, we loop through the elements in the container and call the
     * {@code token.save()} method to get the entries of the token. If the token
     * is null, we save the element key and it's value to the path
     * "sectionName.elementKey.elementValue"
     * @param sectionName - The name of the section you would like to save the container under
     */
    public void save(String sectionName) {
        FileConfiguration configuration = file.getConfiguration();
        ConfigurationSection section = configuration.getConfigurationSection(sectionName);
        if (section == null)
            throw new IllegalArgumentException("Section with name '" + sectionName + "' was not found");
        PluginLibraryTemplate.getInstance().getTimings().start("flatFileContainer:save:" + file.getFileName());
        for (StorageElement element : getElements()) {
            if (token == null)
                configuration.set(sectionName + "." + element.getKey(), element.getValue());
            else {
                E value = (E) element.getValue();
                if (!value.getClass().isAssignableFrom(token))
                    throw new IllegalStateException("Cannot save element '" + element.getKey() + "', it has a mismatched token");
                FlatFileToken token = (FlatFileToken) value;
                for (Tuple<String, Object> entry : token.save().getEntries()) {
                    configuration.set(sectionName + "." + element.getKey() + "." + entry.getLeft(), entry.getRight());
                }
            }
        }
        file.saveConfig();
        PluginLibraryTemplate.getInstance().getTimings().stop("flatFileContainer:save:" + file.getFileName());
    }
}