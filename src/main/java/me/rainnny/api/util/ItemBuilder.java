package me.rainnny.api.util;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Braydon
 */
public class ItemBuilder implements Cloneable {
    private final ItemStack item;

    /**
     * Create a new item builder from an existing itemstack
     * @param item - The itemstack you would like to create the item builder with
     */
    public ItemBuilder(ItemStack item) {
        this.item = item;
    }

    /**
     * Create a new item builder with the provided material
     * @param material - The material of the item you would like to create
     */
    public ItemBuilder(Material material) {
        this(material, 1);
    }

    /**
     * Create a new item builder with the provided material, and amount
     * @param material - The material of the item you would like to create
     * @param amount - The amount of the item you would like to create
     */
    public ItemBuilder(Material material, int amount) {
        item = new ItemStack(material, amount);
    }

    /**
     * Create a new item builder with the provided material, amount, and dye color
     * @param material - The material of the item you would like to create
     * @param amount - The amount of the item you would like to create
     * @param color - The dye color of the item you would like to create
     */
    public ItemBuilder(Material material, int amount, DyeColor color) {
        this(material, amount, color.getDyeData());
    }

    /**
     * Create a new item builder with the provided material, amount, and data
     * @param material - The material of the item you would like to create
     * @param amount - The amount of the item you would like to create
     * @param data - The data of the item you would like to create
     */
    public ItemBuilder(Material material, int amount, byte data) {
        item = new ItemStack(material, amount, data);
    }

    /**
     * Sets the display name of your item to the provided string
     * @param name - The name you would like to set your item to
     * @return the item builder
     */
    public ItemBuilder setName(String name) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        item.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Sets the lore of your item to the provided string array
     * @param array - The array of strings you would like to set your lore to
     * @return the item builder
     */
    public ItemBuilder setLore(String... array) {
        return setLore(Arrays.asList(array));
    }

    /**
     * Sets the lore of your item to the provided string list
     * @param list - The list of strings you would like to set your lore to
     * @return the item builder
     */
    public ItemBuilder setLore(List<String> list) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setLore(list);
        item.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Add a string to the item lore at the provided index
     * @param index - The index you would like to add the lore line to
     * @param s - The text you would like to add at the provided index
     * @return the item builder
     */
    public ItemBuilder addLoreLine(int index, String s) {
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = new ArrayList<>(itemMeta.getLore());
        lore.set(index, s);
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Add a string to the item lore
     * @param s - The text you would like to add to your item lore
     * @return the item builder
     */
    public ItemBuilder addLoreLine(String s) {
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        if (itemMeta.hasLore())
            lore = new ArrayList<>(itemMeta.getLore());
        lore.add(s);
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Remove a string from the lore at the provided index
     * @param index - The index you would like to remove the lore line from
     * @return the item builder
     */
    public ItemBuilder removeLoreLine(int index) {
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = new ArrayList<>(itemMeta.getLore());
        if (index < 0 || index > lore.size())
            return this;
        lore.remove(index);
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Remove a string from the lore that matches the provided string
     * @param s - The string you would like to remove from the lore
     * @return the item builder
     */
    public ItemBuilder removeLoreLine(String s) {
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = new ArrayList<>(itemMeta.getLore());
        if (!lore.contains(s))
            return this;
        lore.remove(s);
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Clears the item lore
     * @return the item builder
     */
    public ItemBuilder clearLore() {
        ItemMeta itemMeta = item.getItemMeta();
        List<String> lore = new ArrayList<>(itemMeta.getLore());
        lore.clear();
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Set the amount of the itemstack
     * @param amount - The amount you would like your item to be
     * @return the item builder
     */
    public ItemBuilder setAmount(int amount) {
        item.setAmount(amount);
        return this;
    }

    /**
     * Set the durability of the itemstack
     * @param durability - The durability you would like your item to have
     * @return the item builder
     */
    public ItemBuilder setDurability(short durability) {
        item.setDurability(durability);
        return this;
    }

    /**
     * Add a glow effect to your item
     * @return the item builder
     */
    public ItemBuilder addGlow() {
        ItemMeta itemMeta = item.getItemMeta();
        item.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Adds enchants to your item from a map (Enchant, Level)
     * @param enchantments - A map of enchants with the key as the enchant, and the value as the level
     * @return the item builder
     */
    public ItemBuilder addEnchantments(Map<Enchantment, Integer> enchantments) {
        item.addEnchantments(enchantments);
        return this;
    }

    /**
     * Add an enchant to your item
     * @param enchantment - The enchant you would like to add
     * @param level - The level of the enchant
     * @return the item builder
     */
    public ItemBuilder addEnchant(Enchantment enchantment, int level) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.addEnchant(enchantment, level, true);
        item.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Add an unsafe enchant to your item
     * @param enchantment - The enchant you would like to add
     * @param level - The level of the enchant
     * @return the item builder
     */
    public ItemBuilder addUnsafeEnchantment(Enchantment enchantment, int level) {
        item.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    /**
     * Remove an enchant from your item
     * @param enchantment - The enchant you would like to remove
     * @return the item builder
     */
    public ItemBuilder removeEnchantment(Enchantment enchantment) {
        item.removeEnchantment(enchantment);
        return this;
    }


    /**
     * Clear all enchants from your item
     * @return the item builder
     */
    public ItemBuilder clearEnchantments() {
        item.getItemMeta().getEnchants().forEach((enchantment, integer) -> item.getItemMeta().removeEnchant(enchantment));
        return this;
    }

    /**
     * Set the skull texture of your item with the provided player's skin
     * @param playerName - The player's name for the skull texture
     * @return the item builder
     */
    public ItemBuilder setSkullOwner(String playerName) {
        if (item.getType() != Material.SKULL_ITEM)
            throw new IllegalStateException("You cannot set the skullOwner with type '" + item.getType().name() + "', it must be of type SKULL_ITEM");
        SkullMeta itemMeta = (SkullMeta) item.getItemMeta();
        itemMeta.setOwner(playerName);
        item.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Set the color of your item
     * @param color - The color you would like to set your leather armor to
     * @return the item builder
     */
    public ItemBuilder setLeatherArmorColor(Color color) {
        if (!item.getType().name().contains("LEATHER") || item.getType() == Material.LEATHER)
            throw new IllegalStateException("You cannot set the leather armor color with type '" + item.getType().name() + "', it must be a piece of leather armor");
        LeatherArmorMeta itemMeta = (LeatherArmorMeta) item.getItemMeta();
        itemMeta.setColor(color);
        item.setItemMeta(itemMeta);
        return this;
    }

    /**
     * Create the item we just created with the item builder
     * @return the constructed itemstack
     */
    public ItemStack toItemStack() {
        return item;
    }

    @Override
    public ItemBuilder clone() {
        return new ItemBuilder(item.clone());
    }
}