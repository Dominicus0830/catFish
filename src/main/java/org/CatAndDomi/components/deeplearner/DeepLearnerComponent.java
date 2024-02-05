package org.CatAndDomi.components.deeplearner;

import org.CatAndDomi.components.Component;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DeepLearnerComponent extends Component {

    Map<String, Synapse> map = new HashMap<>();

    public DeepLearnerComponent(JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public void load() {
        super.load();
        Bukkit.getPluginManager().registerEvents(new Events(this), plugin);
        File file = new File(plugin.getDataFolder()+"/synapses.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        if(config.isSet("list")) {
            for(String str : config.getStringList("list")) {
                File file1 = new File(plugin.getDataFolder()+"/synapse/" + str+".yml");
                YamlConfiguration config1 = YamlConfiguration.loadConfiguration(file1);
                map.put(str, new Synapse(config1));
            }
        }
    }

    public void save() {
        ArrayList<String> strlist = new ArrayList<>();
        File file = new File(plugin.getDataFolder()+"/synapses.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        for(Map.Entry<String, Synapse> entry : map.entrySet()) {
            strlist.add(entry.getKey());
            File file1 = new File(plugin.getDataFolder()+"/synapse/"+entry.getKey()+".yml");
            YamlConfiguration config1 = YamlConfiguration.loadConfiguration(file1);
            entry.getValue().save(config1);
            try{
                config1.save(file1);
            }catch(Exception e) {
            }
        }
        config.set("list", strlist);
        try{
            config.save(file);
        }catch(Exception e) {
        }
    }

    @Override
    public void serverclose() {
        super.serverclose();
        save();
    }

    public Synapse getSynapse(String string) {
        if(map.get(string)!=null) {
            return map.get(string);
        }
        return null;
    }

    public void deleteSynapse(String string) {
        map.remove(string);
    }

    public boolean isSynapse(String string) {
        return map.get(string)!=null;
    }

    public void createSynapse(String string) {
        map.put(string, new Synapse());
    }
}
