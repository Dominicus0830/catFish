package org.CatAndDomi.components.deeplearner;

import org.CatAndDomi.utils.CatFishMath;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Neuron {

    public Synapse synapse;
    public Map<Neuron, Double> weight = new HashMap<>();
    public double input;
    public double output;
    public double errordistance = 0;

    public Neuron(Synapse synapse) {
        this.synapse = synapse;
    }

    public Neuron(Synapse synapse, YamlConfiguration config, String loc) {
        this.synapse = synapse;
        input = config.getDouble(loc+".input");
        output = config.getDouble(loc+".output");
        output = config.getDouble(loc+".errordistance");
    }

    public void getMidlinedelta(Neuron from, Neuron to, double value, int num) {
        double a = value*output*(1D-output);
        if(num==0) {
            a*=errordistance;
            from.weight.put(to, from.weight.get(to) - synapse.learningper*a);
            return;
        }
        for(Map.Entry<Neuron, Double> entry : weight.entrySet()) {
            entry.getKey().getMidlinedelta(from, to, a* entry.getValue()*2D/((double)synapse.outputnum), num-1);
        }
    }

    public void setpredict(double d) {
        errordistance = output-d;
    }

    public void intoout() {
        output = CatFishMath.sigmoid(1, input);
    }

    public void loadNeurons(YamlConfiguration config, String loc) {
        if(!config.isSet(loc+".numlist")) {
            return;
        }
        List<Integer> numlist = config.getIntegerList(loc+".numlist");
        List<Double> weightlist = config.getDoubleList(loc+".weightlist");
        for(int i = 0; i<numlist.size(); i++) {
            weight.put(synapse.numneuronmap.get(numlist.get(i)), weightlist.get(i));
        }
    }

    public void save(YamlConfiguration config, String loc) {
        config.set(loc+".input", input);
        config.set(loc+".output", output);
        config.set(loc+".errordistance", errordistance);
        ArrayList<Integer> numlist = new ArrayList<>();
        ArrayList<Double> weightlist = new ArrayList<>();
        for(Map.Entry<Neuron, Double> entry : weight.entrySet()) {
            numlist.add(synapse.neuronnummap.get(entry.getKey()));
            weightlist.add(entry.getValue());
        }
        config.set(loc+".numlist", numlist);
        config.set(loc+".weightlist", weightlist);
    }

}
