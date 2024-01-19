package org.CatAndDomi.components.pageinventory;

import org.CatAndDomi.components.Component;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class PageInventoryComponent extends Component {

    Map<String, PageInventory> invmap = new HashMap<>();
    Map<Player, InventoryOpener> openermap = new HashMap<>();

    Class<? extends PageInventory> clz;
    Class<? extends InventoryOpener> clz1;

    public PageInventoryComponent(JavaPlugin plugin) {
        super(plugin);
        Bukkit.getPluginManager().registerEvents(new Events(this), plugin);
        clz = PageInventory.class;
        clz1 = InventoryOpener.class;
        File file = new File(plugin.getDataFolder()+"/inventorylist.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        for(String key : config.getKeys(true)) {
            File file1 = new File(plugin.getDataFolder()+"/inventorydata/" + key + ".yml");
            YamlConfiguration config1 = YamlConfiguration.loadConfiguration(file);
            try{
                PageInventory inv = clz.getDeclaredConstructor(new Class[]{PageInventoryComponent.class, YamlConfiguration.class}).newInstance(this, config1);
                invmap.put(key, inv);
            }catch(Exception e) {
            }
        }
    }

    public boolean isOpener(Player p) {
        return openermap.get(p)!=null;
    }

    public InventoryOpener getOpener(Player p) {
        if(isOpener(p)) {
            return openermap.get(p);
        }
        return null;
    }

    public void save() {
        File file = new File(plugin.getDataFolder()+"/inventorylist.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        for(Map.Entry<String, PageInventory> entry : invmap.entrySet()) {
            File file1 = new File(plugin.getDataFolder()+"/inventorydata/" + entry.getKey() + ".yml");
            YamlConfiguration config1 = YamlConfiguration.loadConfiguration(file);
            config.set(entry.getKey(), true);
            entry.getValue().save(config1);
            try{
                config1.save(file1);
            }catch(Exception e) {
            }
        }
        try{
            config.save(file);
        }catch(Exception e) {
        }
    }

    public void setPageInventoryClass(Class<? extends PageInventory> clz) {
        this.clz = clz;
    }

    public void setInventoryOpenerClass(Class<? extends InventoryOpener> clz1) {
        this.clz1 = clz1;
    }

    public boolean createInventory(String name, int invsize) {
        if(invmap.get(name)!=null) {
            return false;
        }
        try{
            invmap.put(name, clz.getDeclaredConstructor(new Class[]{String.class, PageInventoryComponent.class, Integer.class}).newInstance(name, this, invsize));
            return true;
        }catch(Exception e) {
            return false;
        }
    }

    public boolean removeInventory(String name) {
        if(invmap.get(name)!=null) {
            invmap.remove(name);
            return true;
        }
        return false;
    }

    public boolean isInventory(String name) {
        return invmap.get(name)!=null;
    }

    public PageInventory getInventory(String name) {
        if(invmap.get(name)!=null) {
            return invmap.get(name);
        }
        return null;
    }
}
