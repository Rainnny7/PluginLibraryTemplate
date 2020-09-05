package me.rainnny.api.network.wrapped.impl.inbound;

import lombok.Getter;
import me.rainnny.api.network.Packet;
import me.rainnny.api.network.wrapped.WrappedClass;
import me.rainnny.api.network.wrapped.WrappedField;
import me.rainnny.api.network.wrapped.WrappedPacket;
import org.bukkit.entity.Player;

/**
 * @author Braydon
 * This is a wrapped version of the PacketPlayInFlying packet
 */
@Getter
public class WrappedInFlyingPacket extends WrappedPacket {
    private static final WrappedClass wrapped = new WrappedClass(Packet.Client.FLYING);

    /**
     * Gets each field from the PacketPlayInFlying packet using reflection
     * so it can be used later to construct the wrapped packet
     */
    private static final WrappedField fieldX = wrapped.getFieldByType(double.class, 0);
    private static final WrappedField fieldY = wrapped.getFieldByType(double.class, 1);
    private static final WrappedField fieldZ = wrapped.getFieldByType(double.class, 2);

    private static final WrappedField fieldYaw = wrapped.getFieldByType(float.class, 0);
    private static final WrappedField fieldPitch = wrapped.getFieldByType(float.class, 1);

    private static final WrappedField fieldGround = wrapped.getFieldByType(boolean.class, 0);
    private static final WrappedField fieldPosition = wrapped.getFieldByType(boolean.class, 1);
    private static final WrappedField fieldLook = wrapped.getFieldByType(boolean.class, 2);

    /**
     * The fields from the PacketPlayInFlying class
     */
    private final double x, y, z;
    private final float yaw, pitch;
    private final boolean ground, position, look;

    /**
     * Creates a wrapped flying packet with the provided player and provided packet
     * @param player - The player sending the packet
     * @param object - The class of the packet
     *
     * Takes each field from the PacketPlayInFlying packet and
     * updates it to it's respective field in the wrapped class
     */
    public WrappedInFlyingPacket(Player player, Object object) {
        super(player, object);
        x = fieldX.get(object);
        y = fieldY.get(object);
        z = fieldZ.get(object);
        yaw = fieldYaw.get(object);
        pitch = fieldPitch.get(object);
        ground = fieldGround.get(object);
        position = fieldPosition.get(object);
        look = fieldLook.get(object);
    }
}