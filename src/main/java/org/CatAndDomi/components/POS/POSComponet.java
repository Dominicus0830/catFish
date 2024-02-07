package org.CatAndDomi.components.POS;

import org.CatAndDomi.components.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class POSComponet extends Component {
    Location minloc, maxloc;
    int minx, miny, minz, maxx, maxy, maxz;
    World world;
    private POSEvent posEvent;

    public POSComponet(JavaPlugin plugin) {
        super(plugin);
        Bukkit.getPluginManager().registerEvents(new POSEvent(this), plugin);
    }

    public void load() {
        super.load();
        File file = new File(plugin.getDataFolder() + "/POS.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        world = plugin.getServer().getWorld(config.getString("settings.world"));
        try {
            minx = config.getInt("settings.minx");
            miny = config.getInt("settings.miny");
            minz = config.getInt("settings.minz");
            maxx = config.getInt("settings.maxx");
            maxy = config.getInt("settings.maxy");
            maxz = config.getInt("settings.maxz");
            Location minloc = new Location(world, minx, miny, minz);
            Location maxloc = new Location(world, maxx, maxy, maxz);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void serverclose() {
        super.serverclose();
        save();
    }

    public Location getConfigMinloc() {
        return minloc;

    }

    public Location getConfigMaxloc() {
        return maxloc;
    }

    public Location getMinloc() {
        return posEvent.minloc;
    }

    public Location getMaxloc() {
        return posEvent.maxloc;
    }

    public void save() {
        File file = new File(plugin.getDataFolder() + "/POS.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        try {
            config.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
