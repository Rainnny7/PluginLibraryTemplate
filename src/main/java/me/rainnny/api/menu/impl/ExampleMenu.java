package me.rainnny.api.menu.impl;

import me.rainnny.api.menu.Button;
import me.rainnny.api.menu.Menu;
import me.rainnny.api.menu.MenuInfo;
import me.rainnny.api.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * @author Braydon
 */
@MenuInfo(title = "A Menu!", size = 3)
public class ExampleMenu extends Menu {
    public ExampleMenu(Player player) {
        super(player);
    }

    @Override
    public void onOpen() {
        set(0, new Button(new ItemBuilder(Material.DIRT).toItemStack()));

        set(8, new Button(new ItemBuilder(Material.WOOD).toItemStack(), event -> {
            player.sendMessage("clicked wood!");
        }));
    }
}