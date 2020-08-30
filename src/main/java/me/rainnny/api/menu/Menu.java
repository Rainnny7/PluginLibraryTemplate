package me.rainnny.api.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Braydon
 */
public abstract class Menu {
    protected final Player player;
    private final Inventory inventory;

    private final Map<Integer, Button> buttons = new HashMap<>();

    public Menu(Player player) {
        this.player = player;
        if (!getClass().isAnnotationPresent(MenuInfo.class))
            throw new IllegalStateException("Cannot create menu '" + getClass().getSimpleName() + "', missing @MenuInfo");
        MenuInfo info = getClass().getAnnotation(MenuInfo.class);
        inventory = Bukkit.createInventory(null, info.size() * 9, info.title());
    }

    public Menu(Player player, String title, int size) {
        this.player = player;
        inventory = Bukkit.createInventory(null, size * 9, title);
    }

    /**
     * This get's called when the player opens the menu
     */
    public abstract void onOpen();

    /**
     * Set an item in the menu to the provided button at the provided column
     * and row.
     * @param column - The vertical position of the item
     * @param row - The horizontal position of the item
     * @param button - The button you would like to set at the provided column and slot
     */
    protected void set(int column, int row, Button button) {
        set(9 * column + row, button);
    }

    /**
     * Set an item in the menu to the provided button at the provided slot
     * @param slot - The slot you would like to set your button in
     * @param button - The button you would like to set at the provided slot
     */
    protected void set(int slot, Button button) {
        buttons.put(slot, button);
        inventory.setItem(slot, button.getItem());
    }

    /**
     * Fill the menu with a button
     * @param button - The button you would like to fill the menu with
     */
    protected void fill(Button button) {
        for (int i = 0; i < inventory.getSize(); i++) {
            set(i, button);
        }
    }

    /**
     * Get a button by the provided slot
     * @param slot - The button you would like to get from the slot
     * @return the button in the provided slot
     */
    protected Button getButton(int slot) {
        for (Map.Entry<Integer, Button> entry : buttons.entrySet()) {
            if (entry.getKey() == slot) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * Get a button by the provided item
     * @param item - The item you would like to get the button for
     * @return the button matching the provided item, null if none
     */
    protected Button getButton(ItemStack item) {
        for (Button button : buttons.values()) {
            if (button.getItem().isSimilar(item)) {
                return button;
            }
        }
        return null;
    }

    /**
     * Open the menu to the player
     */
    public void open() {
        MenuManager.setMenu(player, this);
        player.openInventory(inventory);
        onOpen();
    }
}