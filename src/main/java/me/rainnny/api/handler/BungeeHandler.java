package me.rainnny.api.handler;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.bukkit.Bukkit.getMessenger;
import static org.bukkit.Bukkit.getServer;

/**
 * @author Braydon
 */
public class BungeeHandler implements PluginMessageListener {
    private static final List<String> servers = new ArrayList<>();
    private static final HashMap<String, Integer> onlineCount = new HashMap<>();
    private static final HashMap<String, List<String>> players = new HashMap<>();

    private static final Pattern PLAYER_COUNT_REGEX = Pattern.compile("(%online:((?:[a-z][a-z0-9_]*))%)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
    private static final Pattern STATUS_REGEX = Pattern.compile("(%status:((?:[a-z][a-z0-9_]*))%)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);

    private static JavaPlugin plugin;
    
    public BungeeHandler(JavaPlugin plugin) {
        BungeeHandler.plugin = plugin;
        getMessenger().registerIncomingPluginChannel(plugin, "BungeeCord", this);
        getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
    }
    
    @Override @SuppressWarnings("UnstableApiUsage")
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (message.length < 1)
            return;
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subChannel = in.readUTF();
        switch (subChannel) {
            case "GetServers": {
                String[] serverList = in.readUTF().split(", ");
                servers.clear();
                servers.addAll(Arrays.asList(serverList));
                break;
            }
            case "PlayerCount": {
                onlineCount.put(in.readUTF(), in.readInt());
                break;
            }
            case "PlayerList": {
                String[] players = in.readUTF().split(", ");
                BungeeHandler.players.put(in.readUTF(), Arrays.asList(players));
                break;
            }
        }
    }

    public static void sendToServer(Player player, String server) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);
        try {
            out.writeUTF("Connect");
            out.writeUTF(server);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        player.sendPluginMessage(plugin, "BungeeCord", stream.toByteArray());
    }

    public static String formatBungeeServerString(String s) {
        s = s.replaceAll("%online%", "" + getPlayersOnline("ALL"));

        Matcher matcher = PLAYER_COUNT_REGEX.matcher(s);
        if (matcher.find()) {
            String server = matcher.group(2);
            int online = getPlayersOnline(server);
            s = s.replaceAll("%online:" + server + "%", "" + online);
        }

        matcher = STATUS_REGEX.matcher(s);
        if (matcher.find()) {
            String server = matcher.group(2);
            boolean online = isOnline(server);
            s = s.replaceAll("%status:" + server + "%", online ? "§aOnline" : "§cOffline");
        }

        return s;
    }

    public static boolean isOnline(String server) {
        return getPlayersOnline(server) != -1;
    }

    public static Collection<String> getServers() {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);
        try {
            out.writeUTF("GetServers");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        getServer().sendPluginMessage(plugin, "BungeeCord", stream.toByteArray());
        return servers;
    }

    public static int getPlayersOnline(String server) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);
        try {
            out.writeUTF("PlayerCount");
            out.writeUTF(server);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        getServer().sendPluginMessage(plugin, "BungeeCord", stream.toByteArray());
        return onlineCount.getOrDefault(server, -1);
    }

    public static List<String> getPlayers(String server) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);
        try {
            out.writeUTF("PlayerList");
            out.writeUTF(server);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        getServer().sendPluginMessage(plugin, "BungeeCord", stream.toByteArray());
        return players.getOrDefault(server, new ArrayList<>());
    }
}