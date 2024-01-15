package org.CatAndDomi.components;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageComponent extends Component {

    Map<String, Object> messagelist = new HashMap<>();

    public MessageComponent(JavaPlugin plugin) {
        super(plugin);
    }

    public MessageComponent addMessages(String string, Object obj) {
        messagelist.put(string, obj);
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T extends List<?>> T cast(Object obj) {
        return (T) obj;
    }

    public boolean cancast(Object obj) {
        try{
            List<String> list = cast(obj);
            return true;
        }catch(Exception e) {
            return false;
        }
    }

    public String getString(String key) {
        return (String) messagelist.get(key);
    }

    public List<String> getList(String key) {
        return cast(messagelist.get(key));
    }

    public Object get(String key) {
        return messagelist.get(key);
    }

    public void load() {
        File file = new File(plugin.getDataFolder()+"/message.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        for(Map.Entry<String, Object> entry : messagelist.entrySet()) {
            if(config.isSet(entry.getKey())) {
                messagelist.put(entry.getKey(), config.get(entry.getKey()));
            }else {
                config.set(entry.getKey(), entry.getValue());
            }
            if(entry.getValue() instanceof String str) {
                messagelist.put(entry.getKey(), str.replace("&", "§"));
            }
            if(cancast(entry.getValue())) {
                List<String> list = cast(entry.getValue());
                for(int i = 0; i<list.size(); i++) {
                    list.set(i, list.get(i).replace("&", "§"));
                }
                messagelist.put(entry.getKey(), entry.getValue());
            }
        }
        try{
            config.save(file);
        }catch(Exception e) {
        }

    }

}
