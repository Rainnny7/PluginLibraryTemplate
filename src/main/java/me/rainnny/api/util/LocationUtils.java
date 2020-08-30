package me.rainnny.api.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * @author Braydon
 */
public class LocationUtils {
    /**
     * Take a {@code Bukkit} {@code Location} and turn it into a string
     * @param location - The location you would like to serialize
     * @return the serialized location (E.g: world,x,y,z,yaw,pitch)
     */
    public static String toString(Location location) {
        if (location == null)
            return "";
        return location.getWorld().getName() + "," +
                MathUtils.roundDecimal(1, location.getX()) + "," +
                MathUtils.roundDecimal(1, location.getY()) + "," +
                MathUtils.roundDecimal(1, location.getZ()) + "," +
                MathUtils.roundDecimal(1, location.getYaw()) + "," +
                MathUtils.roundDecimal(1, location.getPitch());
    }

    /**
     * Take the serialized {@code Bukkit} {@code Location} and turn it into a location
     * @param string - The serialized location
     * @return the un-serialized location
     */
    public static Location fromString(String string) {
        if (string.length() == 0)
            return null;
        String[] split = string.split(",");
        for (World world : Bukkit.getWorlds()) {
            if (!world.getName().equalsIgnoreCase(split[0]))
                continue;
            float yaw = 0f;
            float pitch = 0f;
            if (split.length >= 5) {
                yaw = Float.parseFloat(split[4]);
                pitch = Float.parseFloat(split[5]);
            }
            return new Location(world, Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]), yaw, pitch);
        }
        return null;
    }
}
