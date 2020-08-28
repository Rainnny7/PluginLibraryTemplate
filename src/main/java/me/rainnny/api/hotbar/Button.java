package me.rainnny.api.hotbar;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.rainnny.api.util.Callback;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @author Braydon
 */
@RequiredArgsConstructor @Getter
public class Button {
    private final ItemStack item;
    private Callback<PlayerInteractEvent> interactCallback;
    private Callback<PlayerInteractEntityEvent> interactEntityEvent;

    /**
     * Create a new button with an itemstack and an event
     * @param item - The item you would like the button to be
     * @param event - The event that gets called when you click on the item
     */
    public Button(ItemStack item, Callback<PlayerInteractEvent> event) {
        this.item = item;
        interactCallback = event;
    }

    /**
     * Adds a entity interact event to your button
     * @param callback - The entity interact event
     * @return the button
     */
    public Button withInteractEntityEvent(Callback<PlayerInteractEntityEvent> callback) {
        interactEntityEvent = callback;
        return this;
    }
}