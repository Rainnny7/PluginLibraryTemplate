package me.rainnny.api.network;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.rainnny.api.util.Reflection;

/**
 * @author Braydon
 * This class contains a list of Minecraft versions
 * including their respective version name and id
 * More can be found here: https://wiki.vg/Protocol_version_numbers
 */
@AllArgsConstructor @Getter
public enum ProtocolVersion {
    V1_8("v1_8_R1", 45),
    V1_8_5("v1_8_R2", 46),
    V1_8_9("v1_8_R3", 47),
    UNKNOWN("Unknown", -1);

    /**
     * serverVersion: The version of the Minecraft server
     * paperSpigot: Whether or not the server is using paperspigot
     */
    @Getter private static ProtocolVersion serverVersion;
    @Getter private static boolean paperSpigot;

    static {
        for (ProtocolVersion version : values()) {
            if (version.getName() != null && (version.getName().equals(Reflection.getVersion()))) {
                serverVersion = version;
            }
        }
        if (serverVersion == null)
            serverVersion = UNKNOWN;

        try {
            Class.forName("org.github.paperspigot.PaperSpigotConfig");
            paperSpigot = true;
        } catch (Exception ignored) {}
}

    private final String name;
    private final int version;

    /**
     * Returns the clean name of the protocol version (E.g: v1_8_R3 -> 1.8)
     * @return the clean name of the protcol version
     */
    public String getCleanName() {
        if (this == UNKNOWN)
            return name;
        String version = name.replaceAll("v", "").replaceAll("_", ".");
        for (int i = 0; i <= 9; i++)
            version = version.replaceAll(".R" + i, "");
        return version;
    }

    /**
     * Returns whether or not the version is equal to the provided protocol version
     * @param version - The protocol version you would like to compare to
     * @return whether or not the version is equal to the provided protocol version
     */
    public boolean is(ProtocolVersion version) {
        return this.version == version.getVersion();
    }

    /**
     * Returns whether or not the version is not equal to the provided protocol version
     * @param version - The protocol version you would like to compare to
     * @return whether or not the version is not equal to the provided protocol version
     */
    public boolean isNot(ProtocolVersion version) {
        return this.version != version.getVersion();
    }

    /**
     * Returns whether or not the version is equal to or above the provided protocol version
     * @param version - The protocol version you would like to compare to
     * @return whether or not the version is equal to or above the provided protocol version
     */
    public boolean isOrAbove(ProtocolVersion version) {
        return this.version >= version.getVersion();
    }

    /**
     * Returns whether or not the version is above the provided protocol version
     * @param version - The protocol version you would like to compare to
     * @return whether or not the version is above the provided protocol version
     */
    public boolean isAbove(ProtocolVersion version) {
        return this.version > version.getVersion();
    }

    /**
     * Returns whether or not the version is equal to or below the provided protocol version
     * @param version - The protocol version you would like to compare to
     * @return whether or not the version is equal to or below the provided protocol version
     */
    public boolean isOrBelow(ProtocolVersion version) {
        return this.version <= version.getVersion();
    }

    /**
     * Returns whether or not the version is below the provided protocol version
     * @param version - The protocol version you would like to compare to
     * @return whether or not the version is below the provided protocol version
     */
    public boolean isBelow(ProtocolVersion version) {
        return this.version < version.getVersion();
    }

    /**
     * Loops through all of the protocol versions and compares the protocol version
     * to the provided versionID
     * @param versionId - The version id of the protocol version you would like to get
     * @return the protocol version matching the version id provided
     */
    public static ProtocolVersion of(int versionId) {
        for (ProtocolVersion version : values()) {
            if (version.getVersion() == versionId) {
                return version;
            }
        }
        return UNKNOWN;
    }
}