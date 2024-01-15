package org.CatAndDomi;

import org.CatAndDomi.utils.ConfigUtils;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public class CatFish extends JavaPlugin {
    public final String prefix = "§7[§eCatFish§7] ";
    public Logger log;
    public YamlConfiguration config;
    private static CatFish plugin;
    public static CatFish getInstance() {
        return plugin;
    }

    @Override
    public void onEnable() {
        plugin = this;
        log = getLogger();
        log.info("CatFish is enabled!");
        config = ConfigUtils.loadPluginConfig(this);
    }
}