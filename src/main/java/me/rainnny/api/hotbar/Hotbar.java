package me.rainnny.api.hotbar;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.rainnny.api.util.Tuple;
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
     * @return a tuple with the button's slot and the button matching the provided item, null if none
     */
    protected Tuple<Integer, Button> getButton(ItemStack item) {
        for (Map.Entry<Integer, Button> entry : buttons.entrySet()) {
            Button button = entry.getValue();
            if (button.getItem().isSimilar(item)) {
                return new Tuple<>(entry.getKey(), entry.getValue());
            }
        }
        return null;
    }

    /**
     * Loops through all of the buttons and checks if the provided id is the same
     * as the looped button's id
     * @param id - The item you would like to get the button for
     * @return a tuple with the button's slot and the button matching the provided id, null if none
     */
    protected Tuple<Integer, Button> getButton(String id) {
        for (Map.Entry<Integer, Button> entry : buttons.entrySet()) {
            Button button = entry.getValue();
            if (button.getId().equalsIgnoreCase(id)) {
                return new Tuple<>(entry.getKey(), entry.getValue());
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

    /**
     * Switch to another button in the hotbar
     * @param player - The player you would like to switch items for
     * @param id - The id of the button you would like to switch to
     */
    protected void switchTo(Player player, String id) {
        Tuple<Integer, Button> button = getButton(id);
        if (button == null)
            throw new IllegalStateException("Cannot switch to button '" + id + "', it is not valid");
        player.getInventory().setItem(button.getLeft(), button.getRight().getItem());
        player.updateInventory();
    }
}