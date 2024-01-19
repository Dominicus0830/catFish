package org.CatAndDomi.components.pageinventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.WorldSaveEvent;

public class Events implements Listener {

    private final PageInventoryComponent component;

    public Events(PageInventoryComponent component) {
        this.component = component;
    }

    @EventHandler
    public void close(InventoryCloseEvent e) {
        if(e.getPlayer() instanceof Player p) {
            if(component.openermap.get(p)!=null) {
                InventoryOpener opener = component.openermap.get(p);
                opener.openning.close(e);
                component.openermap.remove(p);
            }
        }
    }

    @EventHandler
    public void save(WorldSaveEvent e) {
        component.save();
    }

    @EventHandler
    public void click(InventoryClickEvent e) {
        if(e.getWhoClicked() instanceof Player p) {
            if(component.openermap.get(p)!=null) {
                component.openermap.get(p).openning.click(e);
            }
        }
    }

    @EventHandler
    public void pquit(PlayerQuitEvent e) {
        component.openermap.remove(e.getPlayer());
    }

}
