package me.rainnny.api.hotbar.impl;

import me.rainnny.api.hotbar.Button;
import me.rainnny.api.hotbar.Hotbar;
import me.rainnny.api.hotbar.HotbarFlag;
import me.rainnny.api.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * @author Braydon
 * This class is just an example of how to use
 * the hotbar system :)
 * An example usgae of this would be for a hub, with a server selector, lobby selector, etc
 */
public class ExampleHotbar extends Hotbar {
    public ExampleHotbar() {
        super("Example");
        addFlag(HotbarFlag.GIVE_ON_JOIN);
    }

    @Override
    public void addItems(Player player) {
        set(0, new Button(new ItemBuilder(Material.DIRT)
                .setName("§6I am dirt!").toItemStack(), event -> {
            player.sendMessage("you clicked on a piece of dirt!");
        }));

        set(4, new Button(new ItemBuilder(Material.APPLE)
                .setName("§cApple").toItemStack()).withInteractEntityEvent(event -> {
                    player.sendMessage("you clicked on an entity: " + event.getRightClicked().getType().name());
        }));
    }
}