package me.rainnny.api.protocol;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import lombok.RequiredArgsConstructor;
import me.rainnny.api.protocol.event.PacketReceiveEvent;
import me.rainnny.api.protocol.event.PacketSendEvent;
import me.rainnny.api.protocol.wrapped.WrappedPacket;
import me.rainnny.api.util.Reflection;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.bukkit.Bukkit.getPluginManager;

/**
 * @author Braydon
 */
public class ProtocolHandler implements Listener {
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public ProtocolHandler(JavaPlugin plugin) {
        for (Player player : Bukkit.getOnlinePlayers())
            inject(player);
        getPluginManager().registerEvents(this, plugin);
    }

    /**
     * When a player joins the server, we want to add them to the
     * Netty channel
     */
    @EventHandler(priority = EventPriority.LOWEST)
    private void onJoin(PlayerJoinEvent event) {
        inject(event.getPlayer());
    }

    /**
     * When a player quits the server, we want to remove them from the
     * Netty channel
     */
    @EventHandler(priority = EventPriority.LOWEST)
    private void onQuit(PlayerQuitEvent event) {
        uninject(event.getPlayer());
    }

    /**
     * Sends the provided packet to the player
     * @param player - The player you would like to send the packet to
     * @param packet - The packet you would like to send
     */
    public void sendPacket(Player player, Object packet) {
        if (packet instanceof WrappedPacket)
            packet = ((WrappedPacket) packet).object;
        Object finalPacket = packet;
        executor.execute(() -> {
            Channel channel = Reflection.getChannel(player);
            channel.eventLoop().execute(() -> {
                if (channel.pipeline().get("custom_packet_listener") != null)
                    channel.pipeline().writeAndFlush(finalPacket);
            });
        });
    }

    private void inject(Player player) {
        executor.execute(() -> {
            Channel channel = Reflection.getChannel(player);
            if (channel == null)
                return;
            NettyListener listener = (NettyListener) channel.pipeline().get("custom_packet_listener");
            if (listener == null) {
                listener = new NettyListener(player);
                if (channel.pipeline().get("custom_packet_listener") != null)
                    channel.pipeline().remove("custom_packet_listener");
                channel.pipeline().addBefore("packet_handler", "custom_packet_listener", listener);
            }
        });
    }

    private void uninject(Player player) {
        executor.execute(() -> {
            Channel channel = Reflection.getChannel(player);
            channel.eventLoop().execute(() -> {
                if (channel.pipeline().get("custom_packet_listener") != null)
                    channel.pipeline().remove("custom_packet_listener");
            });
        });
    }

    @RequiredArgsConstructor
    private static class NettyListener extends ChannelDuplexHandler {
        private final Player player;

        @Override
        public void channelRead(ChannelHandlerContext context, Object object) throws Exception {
            Packet.Client client = Packet.Client.of(object.getClass().getName());
            PacketReceiveEvent event = new PacketReceiveEvent(player, object, client);
            getPluginManager().callEvent(event);

            if (!event.isCancelled())
                super.channelRead(context, object);
        }

        @Override
        public void write(ChannelHandlerContext context, Object object, ChannelPromise promise) throws Exception {
            Packet.Server server = Packet.Server.of(object.getClass().getName());
            PacketSendEvent event = new PacketSendEvent(player, object, server);
            getPluginManager().callEvent(event);

            if (!event.isCancelled())
                super.write(context, object, promise);
        }
    }
}