package org.CatAndDomi.components.pageinventory;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class PageInventory {

    public final String name;
    public int size = 0;
    public final int invsize;
    public final Map<Integer, Inventory> invmap = new HashMap<>();
    public final PageInventoryComponent component;

    public PageInventory(String name, PageInventoryComponent component, Integer invsize) {
        this.name = name;
        this.component = component;
        this.invsize = invsize;
    }

    public PageInventory(PageInventoryComponent component, YamlConfiguration config) {
        this.component = component;
        this.name = config.getString("name");
        this.size = config.getInt("size");
        this.invsize = config.getInt("invsize");
        for(int i = 0; i<size; i++) {
            addpage();
            Inventory inv = invmap.get(i);
            for(int j = 0; j<invsize; j++) {
                if(config.isSet(i+"."+j)) {
                    inv.setItem(j, config.getItemStack(i+"."+j));
                }
            }
        }
    }

    public void save(YamlConfiguration config) {
        config.set("name", name);
        config.set("size", size);
        config.set("invsize", invsize);
        for(int i = 0; i<size; i++) {
            Inventory inv = invmap.get(i);
            for(int j = 0; j<invsize; j++) {
                config.set(i+"."+j, inv.getItem(j));
            }
        }
    }

    public void addpage() {
        Inventory inv = Bukkit.createInventory(null, invsize);
        setinv_creating(inv);
        invmap.put(size, inv);
        size++;
    }

    public void createpage(int page) {
        if(page>-1) {
            while(size<page) {
                addpage();
            }
        }
    }

    public String InventoryName() {
        return name;
    }

    public boolean openInventory(Player p, int page) {
        if(page>=size) {
            return false;
        }
        p.closeInventory();
        Inventory inv = Bukkit.createInventory(null, invsize, InventoryName());
        Inventory in = invmap.get(page);
        for(int i = 0; i<invsize; i++) {
            ItemStack a = in.getItem(i).clone();
            setitem_opening(a, p, page);
            inv.setItem(i, a);
        }
        setinv_opening(inv, p, page);
        p.openInventory(inv);
        component.openermap.put(p, new InventoryOpener(this, page));
        return true;
    }

    public void setItem(int page, int slot, ItemStack i) {
        ItemStack ia = i.clone();
        setitem_setting(ia);
        invmap.get(page).setItem(slot, ia);
    }

    public void setitem_setting(ItemStack i) {

    }

    public void setitem_opening(ItemStack i, Player p, int page) {

    }

    public void setinv_opening(Inventory inv, Player p, int page) {

    }

    public void setinv_creating(Inventory inv) {

    }

    public void close(InventoryCloseEvent e) {

    }

    public void click(InventoryClickEvent e) {

    }

}
