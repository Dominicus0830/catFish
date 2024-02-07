package org.CatAndDomi.components.POS;

import org.CatAndDomi.api.NBT;
import org.CatAndDomi.utils.ConfigUtils;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.WorldSaveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class POSEvent implements Listener {
    private final POSComponet component;
    public Location minloc, maxloc;

    public POSEvent(POSComponet component) {
        this.component = component;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event, JavaPlugin plugin) {
        Player player = event.getPlayer();
        File file = new File(plugin.getDataFolder() + "/POS.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (player.hasPermission("domi.setMeteorArea")) {
            if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                ItemStack item = player.getInventory().getItemInMainHand();
                if (NBT.hasTagKey(item, "pos")) {
                    Location[] locations = (new Location[2]);
                    locations[0] = event.getClickedBlock().getLocation();
                    minloc = locations[0];
                    player.sendMessage("pos1 설정완료! x 자표: " + event.getClickedBlock().getLocation().getBlockX() + " z 자표:" + event.getClickedBlock().getLocation().getBlockZ());
                    int x = event.getClickedBlock().getLocation().getBlockX();
                    int y = event.getClickedBlock().getLocation().getBlockY();
                    int z = event.getClickedBlock().getLocation().getBlockZ();
                    config.set("settings.minx", x);
                    config.set("settings.miny", y);
                    config.set("settings.minz", z);
                    ConfigUtils.savePluginConfig(plugin, config);
                    event.setCancelled(true);
                }
            }
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                ItemStack item = player.getInventory().getItemInMainHand();
                if (NBT.hasTagKey(item, "pos")) {
                    Location[] locations = (new Location[3]);
                    locations[1] = event.getClickedBlock().getLocation();
                    maxloc = locations[1];
                    player.sendMessage("pos2 설정완료! 자표: " + event.getClickedBlock().getLocation().getBlockX() + " z 자표:" + event.getClickedBlock().getLocation().getBlockZ());
                    int x = event.getClickedBlock().getLocation().getBlockX();
                    int y = event.getClickedBlock().getLocation().getBlockY();
                    int z = event.getClickedBlock().getLocation().getBlockZ();
                    config.set("settings.maxx", x);
                    config.set("settings.maxy", y);
                    config.set("settings.maxz", z);
                    ConfigUtils.savePluginConfig(plugin, config);
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void save(WorldSaveEvent e) {
        component.save();
    }
}