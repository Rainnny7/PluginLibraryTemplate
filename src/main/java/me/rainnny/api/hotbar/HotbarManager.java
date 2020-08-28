package me.rainnny.api.hotbar;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.bukkit.Bukkit.getPluginManager;

/**
 * @author Braydon
 */
public class HotbarManager implements Listener {
    private static final List<Hotbar> hotbars = new ArrayList<>();

    public HotbarManager(JavaPlugin plugin) {
        getPluginManager().registerEvents(this, plugin);
    }

    /**
     * When a player joins, we loop through all of the
     * hotbars to check if the hotbar has the flag
     * {@code GIVE_ON_JOIN}. If the hotbar has the flag
     * we give the player the hotbar
     */
    @EventHandler(priority = EventPriority.LOWEST)
    private void onJoin(PlayerJoinEvent event) {
        for (Hotbar hotbar : hotbars) {
            if (!hotbar.hasFlag(HotbarFlag.GIVE_ON_JOIN))
                continue;
            event.getPlayer().getInventory().clear();
            applyHotbar(event.getPlayer(), hotbar);
            break;
        }
    }

    /**
     * When a player interacts with an item, we loop through
     * all of the hotbars to check if the item they interacted
     * with is a button. If the item they interacted with is a button
     * we check if that button has an interact event. If the button has
     * an interact event, we call it
     */
    @EventHandler(priority = EventPriority.LOWEST)
    private void onInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (item == null)
            return;
        for (Hotbar hotbar : hotbars) {
            Button button = hotbar.getButton(item);
            if (button == null)
                continue;
            if (button.getInteractCallback() != null) {
                event.setCancelled(true);
                button.getInteractCallback().run(event);
            }
            break;
        }
    }

    /**
     * When a player interacts with an entity, we loop through
     * all of the hotbars to check if the item they are holding
     * is a button. If the item they are holding is a button
     * we check if that button has an entity interact event. If the button has
     * an entity interact event, we call it
     */
    @EventHandler(priority = EventPriority.LOWEST)
    private void onInteractEntity(PlayerInteractEntityEvent event) {
        ItemStack item = event.getPlayer().getItemInHand();
        if (item == null)
            return;
        for (Hotbar hotbar : hotbars) {
            Button button = hotbar.getButton(item);
            if (button == null)
                continue;
            if (button.getInteractEntityEvent() != null) {
                event.setCancelled(true);
                button.getInteractEntityEvent().run(event);
            }
            break;
        }
    }

    /**
     * When a player attempts to drop an item, we loop through
     * all of the hotbars to check if the item they attempted to
     * drop is a button. If the item is a button and the hotbar
     * doesn't have the {@code ALLOW_DROP} flag, we cancel it
     */
    @EventHandler(priority = EventPriority.LOWEST)
    private void onDrop(PlayerDropItemEvent event) {
        ItemStack item = event.getItemDrop().getItemStack();
        for (Hotbar hotbar : hotbars) {
            Button button = hotbar.getButton(item);
            if (button == null)
                continue;
            if (hotbar.hasFlag(HotbarFlag.ALLOW_DROP))
                continue;
            event.setCancelled(true);
            break;
        }
    }

    /**
     * When a player clicks an item in their inventory, we loop
     * through all of the hotbars to check if the item they clicked
     * is a button. If the item is a button and the hotbar doesn't
     * have the {@code ALLOW_MOVE} flag, we cancel it
     */
    @EventHandler(priority = EventPriority.LOWEST)
    private void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (player.getGameMode() == GameMode.CREATIVE)
            return;
        ItemStack item = event.getCurrentItem();
        if (item == null || (item.getType() == Material.AIR))
            return;
        for (Hotbar hotbar : hotbars) {
            Button button = hotbar.getButton(item);
            if (button == null)
                continue;
            if (hotbar.hasFlag(HotbarFlag.ALLOW_MOVE))
                continue;
            event.setCancelled(true);
            break;
        }
    }

    /**
     * Register a hotbar
     * @param hotbar - The hotbar you would like to register
     */
    public static void addHotbar(Hotbar hotbar) {
        hotbars.add(hotbar);
    }

    /**
     * Give a player a hotbar by name
     * @param player - The player you would like to give the hotbar to
     * @param hotbarName - The name of the hotbar you would like to give
     */
    public static void applyHotbar(Player player, String hotbarName) {
        for (Hotbar hotbar : hotbars) {
            if (hotbar.getName().equalsIgnoreCase(hotbarName)) {
                applyHotbar(player, hotbar);
                break;
            }
        }
    }

    /**
     * Give a player a hotbar
     * @param player - The player you would like to give the hotbar to
     * @param hotbar - The hotbar you would like to give the player
     */
    public static void applyHotbar(Player player, Hotbar hotbar) {
        hotbar.apply(player);
        for (Map.Entry<Integer, Button> entry : hotbar.getButtons().entrySet()) {
            player.getInventory().setItem(entry.getKey(), entry.getValue().getItem());
        }
        player.updateInventory();
    }
}