package org.CatAndDomi.components.deeplearner;

import org.CatAndDomi.components.Component;
import org.bukkit.plugin.java.JavaPlugin;

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

    }

    @Override
    public void serverclose() {
        super.serverclose();

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
