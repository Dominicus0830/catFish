package org.CatAndDomi.api;

import org.bukkit.craftbukkit.v1_20_R3.inventory.CraftInventoryCustom;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;

public class InventoryAPI extends CraftInventoryCustom {


    public InventoryAPI(InventoryHolder holder, String title, int size, JavaPlugin plugin) {
        super(holder, size, title);
    }

    public InventoryAPI(InventoryHolder holder, String title, int size, boolean usePage, JavaPlugin plugin) {
        super(holder, size, title);
    }
}
