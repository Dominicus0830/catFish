package org.CatAndDomi.components.pos;

import org.CatAndDomi.api.NBT;
import org.CatAndDomi.utils.ConfigUtils;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.io.File;

public class POSEvent implements Listener {
    private final POSComponet component;

    public POSEvent(POSComponet component) {
        this.component = component;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if(component.possetter.get(p)!=null) {
            if(e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                PosSetter ps = component.possetter.get(p);
                if(ps.loc!=null) {
                    Location loc1 = ps.loc;
                    Location loc2 = e.getClickedBlock().getLocation().add(0.5, 0.5, 0.5);
                    Location mid = loc1.clone().add(loc2.clone()).multiply(0.5D);
                    Vector v = new Vector(Math.abs(loc1.getX()-loc2.getX())*0.5D, Math.abs(loc1.getY()-loc2.getY())*0.5D, Math.abs(loc1.getZ()-loc2.getZ())*0.5D);
                    component.poslocations.put(ps.name, new PosLocation(mid, v));
                    component.possetter.remove(e.getPlayer());
                    p.sendMessage(component.possetMessage1.replace("<POS>", component.pos2));
                }else {
                    ps.loc = e.getClickedBlock().getLocation().add(0.5, 0.5, 0.5);
                    p.sendMessage(component.possetMessage1.replace("<POS>", component.pos1));
                    p.sendMessage(component.possetMessage.replace("<POS>", component.pos2));
                }
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void quit(PlayerQuitEvent e) {
        component.possetter.remove(e.getPlayer());
    }

    @EventHandler
    public void save(WorldSaveEvent e) {
        component.save();
    }
}