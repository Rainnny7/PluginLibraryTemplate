package me.rainnny.api.protocol.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.rainnny.api.protocol.Packet;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Braydon
 * This packet gets called when the player sends a packet
 * to the server.
 *
 * player: The player sending the packet
 * packetClass: The class of the packet being sent
 * packet: The enum of the packet being sent
 */
@RequiredArgsConstructor @Setter @Getter
public class PacketReceiveEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final Object packetClass;
    private final Packet.Client packet;

    private boolean cancelled;

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}