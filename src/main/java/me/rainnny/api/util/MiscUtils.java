package me.rainnny.api.util;

import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Braydon
 */
public class MiscUtils {
    public MiscUtils() throws Exception {
        throw new Exception("You cannot initialize a utility class");
    }

    /**
     * Turns an array of strings into a string separated by \n
     * @param array - The array of strings
     * @return the string separated by \n
     */
    public static String arrayToString(String... array) {
        return arrayToString(Arrays.asList(array));
    }

    /**
     * Turns a list of strings into a string separated by \n
     * @param array - The list of strings
     * @return the string separated by \n
     */
    public static String arrayToString(List<String> array) {
        StringBuilder message = new StringBuilder();
        for (String m : array)
            message.append(m).append("\n");
        return message.toString().substring(0, message.toString().length()-1);
    }

    /**
     * Returns whether or not the provided plugin has the provided dependency
     * @param plugin - The dependency you would like to check
     * @param dependency - The dependency you would like to check for
     * @return whether or not the provided plugin has the provided dependency
     */
    public static boolean dependsOn(Plugin plugin, String dependency) {
        List<String> dependencies = new ArrayList<>();
        dependencies.addAll(plugin.getDescription().getDepend());
        dependencies.addAll(plugin.getDescription().getSoftDepend());
        for (String depend : dependencies) {
            if (depend.equalsIgnoreCase(dependency)) {
                return true;
            }
        }
        return false;
    }
}