package me.rainnny.api.hotbar;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Braydon
 */
@RequiredArgsConstructor @Getter
public abstract class Hotbar {
    private final String name;
    private final List<HotbarFlag> flags = new ArrayList<>();
    private final Map<Integer, Button> buttons = new HashMap<>();

    public abstract void addItems(Player player);

    /**
     * Give a player the hotbar
     * @param player - The player you would like to give the hotbar to
     */
    public void apply(Player player) {
        buttons.clear();
        addItems(player);
    }

    /**
     * Add a flag to the hotbar
     * @param flag - The flag you would like to add
     */
    protected void addFlag(HotbarFlag flag) {
        flags.add(flag);
    }

    /**
     * Returns whether or not the hotbar has the provided flag
     * @param flag - The flag you would like to check for
     * @return whether or not the hotbar has the provided flag
     */
    protected boolean hasFlag(HotbarFlag flag) {
        return flags.contains(flag);
    }

    /**
     * Loops through all of the buttons and checks if the provided item is the same
     * as the looped button's item
     * @param item - The item you would like to get the button for
     * @return the button matching the provided item, null if none
     */
    protected Button getButton(ItemStack item) {
        for (Button button : buttons.values()) {
            if (button.getItem().equals(item)) {
                return button;
            }
        }
        return null;
    }

    /**
     * Add a button to the hotbar with a provided slot
     * @param slot - The slot you would like your button to be in
     * @param button - The button you would like to add
     */
    protected void set(int slot, Button button) {
        buttons.put(slot, button);
    }
}