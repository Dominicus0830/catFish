package org.CatAndDomi.utils;

import org.CatAndDomi.CatFish;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class FileUtils {
    private static final CatFish plugin = CatFish.getInstance();
    private static final Logger log = plugin.log;
    private static final String prefix = plugin.prefix;



    public static YamlConfiguration loadPluginConfig(JavaPlugin plugin) {
        try {

        } catch (Exception e) {

        }
        return null;
    }
    //경로 커스텀
    public static YamlConfiguration loadPluginConfig(JavaPlugin plugin, String fileName) {
        try {
            YamlConfiguration file = YamlConfiguration.loadConfiguration(plugin.getDataFolder());
        } catch (Exception e) {

        }
    }
}
