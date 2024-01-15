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
            log.info(prefix+plugin.getName()+"플러그인의 config 파일 못찾음! 파일 새로 생성...");
            plugin.saveResource("config.yml", false);
        }
        log.info(prefix+ "성공적으로 config 파일 로드!");
        return YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), "config.yml"));
    }

    public static void savePluginConfig(JavaPlugin plugin, YamlConfiguration config){
        try{
            config.save(new File(plugin.getDataFolder(), "config.yml"));
            log.info(prefix+plugin.getName()+"플러그인의 config 파일 저장 성공!");
        }catch (Exception e){
            log.warning(prefix+plugin.getName()+"플러그인의 config 파일 저장 실패!");
            e.printStackTrace();
        }
    }

    public static YamlConfiguration reloadPluginConfig(JavaPlugin plugin, YamlConfiguration config){
        try{
            config.save(new File(plugin.getDataFolder(), "config.yml"));
        }catch (Exception e){
            log.warning(prefix+plugin.getName()+"플러그인의 config 파일 리로드 실패! 파일이 존재하지 않음!");
            e.printStackTrace();
        }
        return null;
    }
}
