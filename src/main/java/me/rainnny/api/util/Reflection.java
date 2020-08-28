package me.rainnny.api.util;

import lombok.Getter;
import me.rainnny.api.protocol.wrapped.WrappedClass;
import me.rainnny.api.protocol.wrapped.WrappedField;
import me.rainnny.api.protocol.wrapped.WrappedMethod;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getServer;

/**
 * @author Braydon
 */
public class Reflection {
    @Getter private static final String version = getServer().getClass().getPackage().getName().split("\\.")[3];
    private static final String craftBukkitPath = "org.bukkit.craftbukkit." + version + ".",
            nmsPath = "net.minecraft.server." + version + ".";

    private static final WrappedClass craftPlayer = getCraftBukkitClass("entity.CraftPlayer");
    private static final WrappedMethod entityPlayerInstance = craftPlayer.getMethodByName("getHandle");

    private static final WrappedClass networkManager = getNMSClass("NetworkManager"),
            playerConnection = getNMSClass("PlayerConnection"),
            entityPlayer = Reflection.getNMSClass("EntityPlayer"),
            channel = new WrappedClass("io.netty.channel.Channel");

    private static final WrappedField fieldNetworkManager = playerConnection.getFieldByType(networkManager.getParent(), 0),
            fieldPlayerConnection = entityPlayer.getFieldByType(playerConnection.getParent(), 0),
            fieldChannel = networkManager.getFieldByType(channel.getParent(), 0);

    public static WrappedClass getCraftBukkitClass(String name) {
        return new WrappedClass(craftBukkitPath + name);
    }

    public static WrappedClass getNMSClass(String name) {
        return new WrappedClass(nmsPath + name);
    }

    public static <T> T getEntityPlayer(Player player) {
        return entityPlayerInstance.invoke(player);
    }

    public static <T> T getNetworkManager(Object playerConnection) {
        return fieldNetworkManager.get(playerConnection);
    }

    public static <T> T getPlayerConnection(Object entityPlayer) {
        return fieldPlayerConnection.get(entityPlayer);
    }

    public static <T> T getChannel(Player player) {
        return fieldChannel.get(getNetworkManager(getPlayerConnection(getEntityPlayer(player))));
    }
}