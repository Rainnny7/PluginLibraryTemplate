package me.rainnny.api.util;

import lombok.Getter;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;

import static org.bukkit.Bukkit.getServer;

/**
 * @author Braydon
 */
public class VaultAttachment {
    @Getter private static Chat chat;
    @Getter private static Permission permission;
    @Getter private static Economy economy;

    public VaultAttachment() {
        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(Chat.class);
        RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(Permission.class);
        RegisteredServiceProvider<Economy> ecoProvider = getServer().getServicesManager().getRegistration(Economy.class);
        chat = chatProvider.getProvider();
        permission = permissionProvider.getProvider();
        economy = ecoProvider.getProvider();
    }
}