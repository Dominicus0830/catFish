package org.CatAndDomi.utils;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class LangUtils {
    private final JavaPlugin plugin;
    private YamlConfiguration currentLang;
    private Map<String, YamlConfiguration> langFiles = new HashMap<>();

    public LangUtils(String langKey, JavaPlugin plugin) {
        this.plugin = plugin;
        loadDefaultLangFiles();
        try {
            currentLang = langFiles.get(langKey);
        } catch (Exception e) {
            System.out.println("[언어] Error: 랭파일 못찾음!");
        }
    }

    public void setLangFile(YamlConfiguration lang) {
        currentLang = lang;
    }

    public YamlConfiguration getCurrentLang() {
        return currentLang;
    }

    public void setCurrentLang(YamlConfiguration currentLang) {
        this.currentLang = currentLang;
    }

    public Map<String, YamlConfiguration> getLangFiles() {
        return langFiles;
    }

    public void setLangFiles(Map<String, YamlConfiguration> langFiles) {
        this.langFiles = langFiles;
    }

    public void setLang(String lang) {
        try {
            currentLang = langFiles.get(lang);
        } catch (Exception e) {
            System.out.println("[언어] 랭 파일 못찾음");
            System.out.println("[언어] input: " + lang);
        }
    }


    public String get(String key) {
        String s = currentLang.getString(key);
        if(s == null) {
            return null;
        }
        return ChatColor.translateAlternateColorCodes('&', s);
    }


    public String getWithArgs(String key, String... args) {
        String s = currentLang.getString(key);
        if (s != null) {
            for (int i = 0; i < args.length; i++) {
                s = s.replace("{" + i + "}", args[i]);
            }
            return ChatColor.translateAlternateColorCodes('&', s);
        }
        return null;
    }

    public void loadDefaultLangFiles() {
        File f = new File(plugin.getDataFolder() + "/lang", "Korean.yml");
        if (!f.exists()) {
            plugin.saveResource("lang/Korean.yml", false);
        }
        f = new File(plugin.getDataFolder() + "/lang", "English.yml");
        if (!f.exists()) {
            plugin.saveResource("lang/English.yml", false);
        }
    }
}