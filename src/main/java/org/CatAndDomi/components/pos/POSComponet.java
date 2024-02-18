package org.CatAndDomi.components.pos;

import org.CatAndDomi.components.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class POSComponet extends Component {

    public Map<Player, PosSetter> possetter = new HashMap<>();
    public Map<String, PosLocation> poslocations = new HashMap<>();
    public String possetMessage = "좌클릭하여 <POS>를 설정하십시오.";
    public String possetMessage1 = "<POS>를 설정하였습니다.";
    public String pos1 = "pos1";
    public String pos2 = "pos2";

    public POSComponet(JavaPlugin plugin) {
        super(plugin);
        Bukkit.getPluginManager().registerEvents(new POSEvent(this), plugin);
    }

    public void load() {
        super.load();
        File file = new File(plugin.getDataFolder() + "/poses.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        for(String string : config.getStringList("pos")) {
            File file1 = new File(plugin.getDataFolder()+"/poses/" + string+".yml");
            YamlConfiguration config1 = YamlConfiguration.loadConfiguration(file1);
            poslocations.put(string, new PosLocation(config1));
        }
    }

    public void playerPosSetter(Player p, String name) {
        possetter.put(p, new PosSetter(name));
        p.sendMessage(possetMessage.replace("<POS>", pos1));
    }

    public void removePos(String string) {
        poslocations.remove(string);
    }

    public PosLocation getPos(String string) {
        return poslocations.get(string)!=null?poslocations.get(string):null;
    }

    public boolean isPos(String string) {
        return poslocations.get(string)!=null;
    }

    @Override
    public void serverclose() {
        super.serverclose();
        save();
    }

    public void save() {
        File file = new File(plugin.getDataFolder() + "/poses.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        ArrayList<String> list = new ArrayList<>();
        for(Map.Entry<String, PosLocation> entry : poslocations.entrySet()) {
            list.add(entry.getKey());
            File file1 = new File(plugin.getDataFolder()+"/poses/" + entry.getKey()+".yml");
            YamlConfiguration config1 = YamlConfiguration.loadConfiguration(file1);
            entry.getValue().save(config1);
            try{
                config1.save(file1);
            }catch(Exception e) {
            }
        }
        config.set("pos", list);
        try {
            config.save(file);
        } catch (Exception e) {
        }
    }
}
