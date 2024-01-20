package org.CatAndDomi.utils;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class InventoryUtils {
    public static boolean hasEnoughSpace(ItemStack[] content, ItemStack... item) {
        Inventory inv = Bukkit.createInventory(null, 36);
        inv.setContents(content);
        HashMap<Integer, ItemStack> leftover = new HashMap<>();
        leftover.putAll(inv.addItem(item));
        return leftover.isEmpty();
    }
}