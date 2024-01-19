package org.CatAndDomi;

import org.CatAndDomi.components.Component;
import org.CatAndDomi.components.ComponentType;
import org.CatAndDomi.utils.ConfigUtils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class CatFish extends JavaPlugin {

    public Map<JavaPlugin, ArrayList<Component>> componentmap = new HashMap<>();

    public final String prefix = "§7[§eCatFish§7] ";
    public Logger log;
    public YamlConfiguration config;
    private static CatFish plugin;

    public static CatFish getInstance() {
        return plugin;
    }

    public Component getComponent(JavaPlugin plugin, ComponentType componentType) {
        for (Component component : componentmap.get(plugin)) {
            if (component.getClass().equals(componentType.clz)) {
                return component;
            }
        }
        return null;
    }

    @Override
    public void onEnable() {
        plugin = this;
        log = getLogger();
        log.info("CatFish is enabled!");
        config = ConfigUtils.loadPluginConfig(this);
    }

    @Override
    public void onDisable() {
        for (Map.Entry<JavaPlugin, ArrayList<Component>> entry : componentmap.entrySet()) {
            for (Component component : entry.getValue()) {
                component.serverclose();
            }
        }
    }
}