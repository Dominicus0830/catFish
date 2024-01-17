package org.CatAndDomi.utils;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class DataUtils {
    private JavaPlugin plugin;
    private YamlConfiguration config;
    private final Map<String, Object> data = new HashMap<>();

    public DataUtils(JavaPlugin plugin) {
        this.plugin = plugin;
        this.config = ConfigUtils.loadPluginConfig(plugin);
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public void setConfig(YamlConfiguration config) {
        this.config = config;
    }

    public void initUserData(UUID uuid) {
        if (!hasUserData(uuid)) {
            YamlConfiguration data = FileUtils.loadPluginFile(plugin, uuid + ".yml", "userdata");
            addUserData(uuid, data);
        }
    }

    public void addUserData(UUID uuid, YamlConfiguration data) {
        if (this.data.containsKey("userdata")) {
            Map<UUID, YamlConfiguration> userdata = (Map<UUID, YamlConfiguration>) this.data.get("userdata");
            userdata.put(uuid, data);
        } else {
            Map<UUID, YamlConfiguration> userdata = new HashMap<>();
            userdata.put(uuid, data);
            this.data.put("userdata", userdata);
        }
    }

    public void removeUserData(UUID uuid) {
        if (this.data.containsKey("userdata")) {
            Map<UUID, YamlConfiguration> userdata = (Map<UUID, YamlConfiguration>) this.data.get("userdata");
            userdata.remove(uuid);
        }
    }

    public YamlConfiguration getUserData(UUID uuid) {
        if (this.data.containsKey("userdata")) {
            Map<UUID, YamlConfiguration> userdata = (Map<UUID, YamlConfiguration>) this.data.get("userdata");
            return userdata.get(uuid);
        }
        return null;
    }

    public boolean hasUserData(UUID uuid) {
        if (this.data.containsKey("userdata")) {
            Map<UUID, YamlConfiguration> userdata = (Map<UUID, YamlConfiguration>) this.data.get("userdata");
            return userdata.containsKey(uuid);
        }
        return false;
    }

    public void clearUserData() {
        if (this.data.containsKey("userdata")) {
            Map<UUID, YamlConfiguration> userdata = (Map<UUID, YamlConfiguration>) this.data.get("userdata");
            userdata.clear();
        }
    }

    public void saveUserData(UUID uuid) {
        if (this.data.containsKey("userdata")) {
            Map<UUID, YamlConfiguration> userdata = (Map<UUID, YamlConfiguration>) this.data.get("userdata");
            FileUtils.savePluginFile(plugin, userdata.get(uuid), uuid + ".yml", "userdata");
        }
    }

    public void saveAndLeave(UUID uuid) {
        if (this.data.containsKey("userdata")) {
            Map<UUID, YamlConfiguration> userdata = (Map<UUID, YamlConfiguration>) this.data.get("userdata");
            FileUtils.savePluginFile(plugin, userdata.get(uuid), uuid + ".yml", "userdata");
            userdata.remove(uuid);
        }
    }

    public void set(String key, Object value) {
        data.put(key, value);
    }

    public Object get(String key) {
        return data.get(key);
    }

    public void reload() {
        config = ConfigUtils.reloadPluginConfig(plugin, config);
    }

    public void save() {
        ConfigUtils.savePluginConfig(plugin, config);
    }
}
