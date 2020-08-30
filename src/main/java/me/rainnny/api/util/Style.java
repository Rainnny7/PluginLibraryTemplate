package me.rainnny.api.util;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Braydon
 */
public class Style {
    /**
     * Colors the provided string
     * @param s - The string that you want to color
     * @return the colored version of the provided string
     */
    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    /**
     * Colors the provided String list
     * @param list - The list you want to color
     * @return the colored list
     */
    public static List<String> colorList(List<String> list) {
        List<String> coloredList = new ArrayList<>();
        for (String s : list) {
            coloredList.add(color(s));
        }
        return coloredList;
    }

    /**
     * Returns a colored true/false based on the provided boolean
     * @param bool - The boolean you would like to get true/false for
     * @return the colored true/false
     */
    public static String tf(boolean bool) {
        return bool ? "§aTrue" : "§cFalse";
    }
}