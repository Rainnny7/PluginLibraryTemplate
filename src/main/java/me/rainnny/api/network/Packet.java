package me.rainnny.api.network;

import lombok.AllArgsConstructor;
import me.rainnny.api.util.Reflection;

/**
 * @author Braydon
 * The use of these two enums is to make it easier to identify
 * packets. An example of this would be for an anticheat, you
 * could just check if the packet is == to the enum, instead of
 * doing instance of
 */
public class Packet {
    @AllArgsConstructor
    public enum Client {
        FLYING("Flying"),
        TAB_COMPLETE("TabComplete"),
        UNKNOWN("Unknown");

        private final String name;

        public String getName() {
            return "net.minecraft.server." + Reflection.getVersion() + ".PacketPlayIn" + name;
        }

        public static Client of(String name) {
            for (Client packet : values()) {
                if ((packet.getName().equals(name))) {
                    return packet;
                }
            }
            return UNKNOWN;
        }
    }

    @AllArgsConstructor
    public enum Server {
        TAB_COMPLETE("TabComplete"),
        UNKNOWN("Unknown");

        private final String name;

        public String getName() {
            return "net.minecraft.server." + Reflection.getVersion() + ".PacketPlayOut" + name;
        }

        public static Server of(String name) {
            for (Server packet : values()) {
                if ((packet.getName().equals(name))) {
                    return packet;
                }
            }
            return UNKNOWN;
        }
    }
}