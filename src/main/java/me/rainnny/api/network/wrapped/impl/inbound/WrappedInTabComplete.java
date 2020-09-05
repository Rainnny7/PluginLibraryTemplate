package me.rainnny.api.network.wrapped.impl.inbound;

import lombok.Getter;
import me.rainnny.api.network.Packet;
import me.rainnny.api.network.wrapped.WrappedClass;
import me.rainnny.api.network.wrapped.WrappedField;
import me.rainnny.api.network.wrapped.WrappedPacket;
import org.bukkit.entity.Player;

/**
 * @author Braydon
 */
@Getter
public class WrappedInTabComplete extends WrappedPacket {
    private static final WrappedClass wrapped = new WrappedClass(Packet.Client.TAB_COMPLETE);

    /**
     * Gets each field from the PacketPlayInTabComplete packet using reflection
     * so it can be used later to construct the wrapped packet
     */
    private static final WrappedField fieldMessage = wrapped.getFieldByType(String.class, 0);

    /**
     * The fields from the PacketPlayInTabComplete class
     */
    private final String message;

    /**
     * Creates a wrapped tab complete packet with the provided player and provided packet
     * @param player - The player sending the packet
     * @param object - The class of the packet
     *
     * Takes each field from the PacketPlayInTabComplete packet and
     * updates it to it's respective field in the wrapped class
     */
    public WrappedInTabComplete(Player player, Object object) {
        super(player, object);
        message = fieldMessage.get(object);
    }
}