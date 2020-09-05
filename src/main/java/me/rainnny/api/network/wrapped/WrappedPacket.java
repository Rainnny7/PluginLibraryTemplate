package me.rainnny.api.network.wrapped;

import org.bukkit.entity.Player;

/**
 * A wrapped packet makes it a lot easier to access
 * fields from a packet as most fields in a packet
 * or protected or private and most field names are
 * things like a, b, c, etc
 */
public abstract class WrappedPacket {
    public final Player player;
    public final Object object;

    /**
     * @param player - The player sending/receving the packet
     * @param object - The class of the packet
     */
    public WrappedPacket(Player player, Object object) {
        this.player = player;
        this.object = object;
    }
}