package org.CatAndDomi.components.deeplearner;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldSaveEvent;

public class Events implements Listener {

    DeepLearnerComponent component;

    public Events(DeepLearnerComponent component) {
        this.component = component;
    }

    @EventHandler
    public void save(WorldSaveEvent e) {
        component.save();
    }

}
