package me.rainnny.api.util;

import lombok.Getter;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

import static org.bukkit.Bukkit.getServer;

/**
 * @author Braydon
 */
public class VaultAttachment {
    @Getter private static Chat chat;
    @Getter private static Permission permission;
    @Getter private static Economy economy;

    public static void initialize() {
        chat = getServer().getServicesManager().getRegistration(Chat.class).getProvider();
        permission = getServer().getServicesManager().getRegistration(Permission.class).getProvider();
        economy = getServer().getServicesManager().getRegistration(Economy.class).getProvider();
    }
}