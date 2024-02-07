package org.CatAndDomi.components.pos;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;

public class PosLocation {

    public Location mid;
    public Vector size;

    public PosLocation(Location mid, Vector size) {
        this.mid = mid;
        this.size = size;
    }

    public PosLocation(YamlConfiguration config) {
        mid = config.getLocation("mid");
        size = config.getVector("size");
    }

    public Collection<Entity> getEntities() {
        return mid.getWorld().getNearbyEntities(mid, size.getX(), size.getY(), size.getZ());
    }

    public void save(YamlConfiguration config) {
        config.set("mid", mid);
        config.set("size", size);
    }

}
