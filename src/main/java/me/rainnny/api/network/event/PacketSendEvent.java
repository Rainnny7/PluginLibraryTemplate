package me.rainnny.api.network.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.rainnny.api.network.Packet;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Braydon
 * This packet gets called when the server sends a packet
 * to the client.
 *
 * player: The player receiving the packet
 * packetClass: The class of the packet being received
 * packet: The enum of the packet being received
 */
@RequiredArgsConstructor @Setter @Getter
public class PacketSendEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final Object packetClass;
    private final Packet.Server packet;

    private boolean cancelled;

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}