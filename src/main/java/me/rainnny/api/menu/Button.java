package me.rainnny.api.menu;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.rainnny.api.util.Callback;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @author Braydon
 */
@AllArgsConstructor @Getter
public class Button {
    private final ItemStack item;
    private Callback<InventoryClickEvent> callback;

    /**
     * Create a new button with only an item and no event
     * @param item - The item you would like your button to have
     */
    public Button(ItemStack item) {
        this.item = item;
    }
}