package org.CatAndDomi.utils;

import org.CatAndDomi.CatFish;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public class FileUtils {
    private static final CatFish plugin = CatFish.getInstance();
    private static final Logger log = plugin.log;
    private static final String prefix = plugin.prefix;


    //create file
    public static YamlConfiguration createCustomFile(JavaPlugin plugin,String fileName) {
        try {
            File file = new File(plugin.getDataFolder(), fileName + ".yml");
            if (!file.exists()) {
                file.createNewFile();
                log.info(prefix + "성공적으로"+plugin.getName()+"플러그인의"+ fileName + " 파일 생성!");
                return YamlConfiguration.loadConfiguration(file);
            }
            log.info(prefix + "성공적으로"+plugin.getName()+"플러그인의"+ fileName + " 파일 로드!");
            return YamlConfiguration.loadConfiguration(file);
        } catch (Exception e) {
            log.warning(prefix +plugin.getName()+"플러그인의"+ fileName + " 파일 생성 실패!");
            e.printStackTrace();
            return null;
        }
    }
    //경로 커스텀
    public static YamlConfiguration createCustomFile(JavaPlugin plugin,String fileName, String path) {
        try {
            File file = new File(plugin.getDataFolder() + "/" + path, fileName + ".yml");
            if (!file.exists()) {
                file.createNewFile();
                log.info(prefix + "성공적으로"+plugin.getName()+"플러그인의"+ fileName + " 파일 생성!");
                return YamlConfiguration.loadConfiguration(file);
            }
            log.info(prefix + "성공적으로"+plugin.getName()+"플러그인의"+ fileName + " 파일 로드!");
            return YamlConfiguration.loadConfiguration(file);
        } catch (Exception e) {
            log.warning(prefix +plugin.getName()+"플러그인의"+ fileName + " 파일 생성 실패!");
            e.printStackTrace();
            return null;
        }
    }


    //파일 로드
    public static YamlConfiguration loadPluginFile(JavaPlugin plugin,String fileName) {
        try {
            YamlConfiguration file = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder(), fileName + ".yml"));
            log.info(prefix + "성공적으로"+plugin.getName()+"플러그인의"+ fileName + " 파일 로드!");
            return file;
        } catch (Exception e) {
            log.warning(prefix + ""+plugin.getName()+"플러그인의"+ fileName + " 파일 로드 실패!");
            e.printStackTrace();
        }
        return null;
    }
    //경로 커스텀
    public static YamlConfiguration loadPluginFile(JavaPlugin plugin, String fileName, String path) {
        try {
            YamlConfiguration file = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder() + "/" + path, fileName + ".yml"));
            log.info(prefix + "성공적으로"+plugin.getName()+"플러그인의"+ fileName + " 파일 로드!");
            return file;
        } catch (Exception e) {
            log.warning(prefix + ""+plugin.getName()+"플러그인의"+ fileName + " 파일 로드 실패!");
            e.printStackTrace();
        }
        return null;
    }


    //파일 저장
    public static void savePluginFile(JavaPlugin plugin, YamlConfiguration file, String fileName) {
        try {
            file.save(new File(plugin.getDataFolder(), fileName + ".yml"));
            log.info(prefix + "성공적으로"+plugin.getName()+"플러그인의"+ fileName + " 파일 저장!");
        } catch (Exception e) {
            log.warning(prefix + ""+plugin.getName()+"플러그인의"+ fileName + " 파일 저장 실패!");
            e.printStackTrace();
        }
    }
    //경로 커스텀
    public static void savePluginFile(JavaPlugin plugin, YamlConfiguration file, String fileName, String path) {
        try {
            file.save(new File(plugin.getDataFolder() + "/" + path, fileName + ".yml"));
            log.info(prefix + "성공적으로"+plugin.getName()+"플러그인의"+ fileName + " 파일 저장!");
        } catch (Exception e) {
            log.warning(prefix + ""+plugin.getName()+"플러그인의"+ fileName + " 파일 저장 실패!");
            e.printStackTrace();
        }
    }
}
