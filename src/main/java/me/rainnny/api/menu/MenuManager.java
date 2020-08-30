package me.rainnny.api.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

import static org.bukkit.Bukkit.getPluginManager;

/**
 * @author Braydon
 */
public class MenuManager implements Listener {
    private static final Map<Player, Menu> menus = new HashMap<>();

    private final JavaPlugin plugin;

    public MenuManager(JavaPlugin plugin) {
        this.plugin = plugin;
        getPluginManager().registerEvents(this, plugin);
    }

    /**
     * Set the provided player's menu to the provided menu
     * @param player - The player you would like to update the menu for
     * @param menu - The menu you would like to update for the player
     */
    protected static void setMenu(Player player, @Nullable Menu menu) {
        if (menu == null)
            menus.remove(player);
        else menus.put(player, menu);
    }

    /**
     * When a player clicks on an item in an inventory, we check if
     * they are in the {@code menus} map. If the player is in the
     * map, we get the menu they have opened and we loop through all
     * of the buttons that menu has to find the button matching the item
     * the player clicked. If the button found has a callback, we run the
     * callback to call the event for that button
     */
    @EventHandler(priority = EventPriority.LOWEST)
    private void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getAction() == InventoryAction.NOTHING || event.getAction() == InventoryAction.UNKNOWN)
            return;
        ItemStack item = event.getCurrentItem();
        if (item == null || (item.getType() == Material.AIR))
            return;
        if (!menus.containsKey(player))
            return;
        Button button = menus.get(player).getButton(item);
        if (button == null)
            return;
        event.setCancelled(true);
        if (button.getCallback() != null)
            button.getCallback().run(event);
    }

    /**
     * When a player closes a menu, we remove them from the
     * {@code menus} map
     */
    @EventHandler(priority = EventPriority.LOWEST)
    private void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        menus.remove(player);
    }

    /**
     * When a player quits, we remove them from the
     * {@code menus} map
     */
    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        menus.remove(event.getPlayer());
    }

    /**
     * When the library get's disabled, we loop through all of
     * the online players and close their opened menu
     */
    @EventHandler
    private void onDisable(PluginDisableEvent event) {
        if (plugin.equals(event.getPlugin())) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.closeInventory();
            }
        }
    }
}