package org.CatAndDomi.utils;

import org.CatAndDomi.CatFish;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public class ConfigUtils {
    private static final CatFish plugin = CatFish.getInstance();
    private static final Logger log = plugin.log;
    public static final String prefix = plugin.prefix;

    public static YamlConfiguration loadPluginConfig(JavaPlugin plugin) {
        File config = new File(plugin.getDataFolder(), "config.yml");
        if (!config.exists()) {
            log.info(prefix+plugin.getName()+"�÷������� config ���� ��ã��! ���� ���� ����...");
            plugin.saveResource("config.yml", false);
        }
        log.info(prefix+ "���������� config ���� �ε�!");
        return YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "config.yml"));
    }

    public static void savePluginConfig(JavaPlugin plugin, YamlConfiguration config){
        try{
            config.save(new File(plugin.getDataFolder(), "config.yml"));
            log.info(prefix+plugin.getName()+"�÷������� config ���� ���� ����!");
        }catch (Exception e){
            log.warning(prefix+plugin.getName()+"�÷������� config ���� ���� ����!");
            e.printStackTrace();
        }
    }

    public static YamlConfiguration reloadPluginConfig(JavaPlugin plugin, YamlConfiguration config){
        try{
            config.save(new File(plugin.getDataFolder(), "config.yml"));
        }catch (Exception e){
            log.warning(prefix+plugin.getName()+"�÷������� config ���� ���ε� ����! ������ �������� ����!");
            e.printStackTrace();
        }
        return null;
    }
}
