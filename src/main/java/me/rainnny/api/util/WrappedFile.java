package me.rainnny.api.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;

/**
 * @author Braydon
 */
@AllArgsConstructor @Getter
public class WrappedFile {
    private final File parent;

    public void mkdir() {
        if (!parent.mkdir()) {
            Bukkit.getLogger().severe("Failed to create directory, do you have permission?");
        }
    }

    public void mkdirs() {
        if (!parent.mkdirs()) {
            Bukkit.getLogger().severe("Failed to create directories, do you have permission?");
        }
    }

    public void createNewFile() {
        try {
            if (!parent.createNewFile()) {
                Bukkit.getLogger().severe("Failed to create file, do you have permission?");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void delete() {
        if (!parent.delete()) {
            Bukkit.getLogger().severe("Failed to delete directory/file, do you have permission?");
        }
    }
}