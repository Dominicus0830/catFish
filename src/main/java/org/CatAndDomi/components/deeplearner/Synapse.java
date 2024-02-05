package org.CatAndDomi.components.deeplearner;

import org.CatAndDomi.utils.CatFishMath;
import org.bukkit.configuration.file.YamlConfiguration;
import org.checkerframework.checker.units.qual.A;
import org.checkerframework.checker.units.qual.N;

import java.util.*;

public class Synapse {

    Map<String, Neuron> inputs = new HashMap<>();
    Map<String, Neuron> outputs = new HashMap<>();
    Map<Integer, ArrayList<Neuron>> lines = new HashMap<>();
    public Map<Integer, Neuron> numneuronmap = new HashMap<>();
    public Map<Neuron, Integer> neuronnummap = new HashMap<>();
    public ArrayList<Integer> line = new ArrayList<>();
    public int neuronnum = 0;
    public double totaladding = 0;
    public double learningper = 0.5D;

    public Synapse(YamlConfiguration config) {
        neuronnum = config.getInt("neuronnum");
        line = (ArrayList<Integer>) config.getIntegerList("line");
        for(int i = 0; i<neuronnum; i++) {
            Neuron n = new Neuron(this, config, "neurons." + i);
            numneuronmap.put(i, n);
            neuronnummap.put(n, i);
        }
        for(int i = 0; i<line.size(); i++) {
            ArrayList<Neuron> list = new ArrayList<>();
            for(int j : config.getIntegerList("lines." + i)) {
                list.add(numneuronmap.get(j));
            }
            lines.put(i, list);
        }
        List<String> inputslist = config.getStringList("inputslist");
        List<Integer> inputsnumlist = config.getIntegerList("inputsnumlist");
        List<String> outputslist = config.getStringList("outputslist");
        List<Integer> outputsnumlist = config.getIntegerList("outputsnumlist");
        for(int i = 0; i<inputslist.size(); i++) {
            inputs.put(inputslist.get(i), numneuronmap.get(inputsnumlist.get(i)));
        }
        for(int i = 0; i<outputslist.size(); i++) {
            outputs.put(outputslist.get(i), numneuronmap.get(outputsnumlist.get(i)));
        }
        for(Map.Entry<Integer, Neuron> entry : numneuronmap.entrySet()) {
            entry.getValue().loadNeurons(config, "neurons."+entry.getKey());
        }
    }

    public Neuron createNeuron() {
        Neuron n = new Neuron(this);
        numneuronmap.put(neuronnum, n);
        neuronnummap.put(n, neuronnum);
        neuronnum++;
        return n;
    }

    public void save(YamlConfiguration config) {
        config.set("neuronnum", neuronnum);
        config.set("line", line);
        for(int i = 0; i<neuronnum; i++) {
            numneuronmap.get(i).save(config, "neurons."+i);
        }
        for(int i = 0; i<line.size(); i++) {
            ArrayList<Integer> numlist = new ArrayList<>();
            for(Neuron n : lines.get(i)) {
                numlist.add(neuronnummap.get(n));
            }
            config.set("lines." + i, numlist);
        }
        ArrayList<String> inputslist = new ArrayList<>();
        ArrayList<Integer> inputsnumlist = new ArrayList<>();
        ArrayList<String> outputslist = new ArrayList<>();
        ArrayList<Integer> outputsnumlist = new ArrayList<>();
        for(Map.Entry<String, Neuron> entry : inputs.entrySet()) {
            inputslist.add(entry.getKey());
            inputsnumlist.add(neuronnummap.get(entry.getValue()));
        }
        for(Map.Entry<String, Neuron> entry : outputs.entrySet()) {
            outputslist.add(entry.getKey());
            outputsnumlist.add(neuronnummap.get(entry.getValue()));
        }
        config.set("inputslist", inputslist);
        config.set("inputsnumlist", inputsnumlist);
        config.set("outputslist", outputslist);
        config.set("outputsnumlist", outputsnumlist);
    }

    public Synapse() {

    }

    public double getErrorDistance() {
        double d = 0;
        for(Map.Entry<String, Neuron> entry : outputs.entrySet()) {
            double a = entry.getValue().errordistance;
            d+=a*a;
        }
        return d;
    }

    public void learning() {
        if(line.size()>0) {
            for(Neuron n : lines.get(line.size()-1)) {
                for(Map.Entry<String, Neuron> entry : outputs.entrySet()) {
                    double a = CatFishMath.sigmoid_pri(1, entry.getValue().input)*2D*entry.getValue().errordistance*learningper;
                    n.errordistance = n.weight.get(entry.getValue())*a;
                    n.weight.put(entry.getValue(), n.weight.get(entry.getValue())+n.output*a);
                    totaladding+=a;
                }
            }
            if(line.size()>1) {
                for(int i = line.size()-2; i>=0; i++) {
                    for(Neuron n : lines.get(i)) {
                        for(Neuron n1 : lines.get(i+1)) {
                            double a = CatFishMath.sigmoid_pri(1, n1.input)*2D*n1.errordistance*learningper;
                            n.errordistance = n.weight.get(n1)*a;
                            n.weight.put(n1, n.weight.get(n1)+n.output*a);
                            totaladding+=a;
                        }
                    }
                }
            }
            for(Map.Entry<String, Neuron> entry : inputs.entrySet()) {
                for(Neuron n : lines.get(0)) {
                    double a = CatFishMath.sigmoid_pri(1, n.input)*2D*n.errordistance*learningper;
                    entry.getValue().errordistance = entry.getValue().weight.get(n)*a;
                    entry.getValue().weight.put(n, entry.getValue().weight.get(n)+entry.getValue().output*a);
                    totaladding+=a;
                }
            }
        }else {
            for(Map.Entry<String, Neuron> entry : inputs.entrySet()) {
                for(Map.Entry<String, Neuron> entry1: inputs.entrySet()) {
                    double a = CatFishMath.sigmoid_pri(1, entry1.getValue().input)*2D*entry1.getValue().errordistance*learningper;
                    entry.getValue().errordistance = entry.getValue().weight.get(entry1.getValue())*a;
                    entry.getValue().weight.put(entry1.getValue(), entry.getValue().weight.get(entry1.getValue())+entry.getValue().output*a);
                    totaladding+=a;
                }
            }
        }
    }

    public void getResult() {
        if(line.size()>0) {
            for(Neuron n : lines.get(0)) {
                double d = 0;
                for(Map.Entry<String, Neuron> entry : inputs.entrySet()) {
                    d+=entry.getValue().weight.get(n)*entry.getValue().input;
                }
                n.input = d - totaladding;
                n.intoout();
            }
            if(line.size()>1) {
                for(int i = 0; i<line.size()-1; i++) {
                    for(Neuron n : lines.get(i+1)) {
                        double d = 0;
                        for(Neuron n1 : lines.get(i)) {
                            d+=n1.weight.get(n)*n1.output;
                        }
                        n.input = d - totaladding;
                        n.intoout();
                    }
                }
            }
            for(Map.Entry<String, Neuron> entry : outputs.entrySet()) {
                double d = 0;
                for(Neuron n : lines.get(line.size()-1)) {
                    d+=n.weight.get(entry.getValue())*n.output;
                }
                entry.getValue().input = d - totaladding;
                entry.getValue().intoout();
            }
        }else {
            for(Map.Entry<String, Neuron> entry : outputs.entrySet()) {
                double d = 0;
                for(Map.Entry<String, Neuron> entry1 : inputs.entrySet()) {
                    d+=entry1.getValue().weight.get(entry.getValue())*entry1.getValue().input;
                }
                entry.getValue().input = d - totaladding;
                entry.getValue().intoout();
            }
        }
    }

    public void setLine(ArrayList<Integer> line) {
        this.line = line!=null?line:new ArrayList<>();
    }

    public void build() {
        lines.clear();
        for(int i = 0; i<line.size(); i++) {
            ArrayList<Neuron> neurons = new ArrayList<>(line.get(i));
            for(int j = 0; j<line.get(i); j++) {
                neurons.add(createNeuron());
            }
            lines.put(i, neurons);
        }
        Random r = new Random();
        if(line.size()>0) {
            for(Map.Entry<String, Neuron> entry : inputs.entrySet()) {
                entry.getValue().input = r.nextDouble();
                for(Neuron n : lines.get(0)) {
                    entry.getValue().weight.put(n, r.nextDouble());
                }
            }
            if(line.size()>1) {
                for(int i = 0; i<line.size()-1; i++) {
                    for(Neuron n : lines.get(i)) {
                        for(Neuron n1 : lines.get(i+1)) {
                            n.weight.put(n1, r.nextDouble());
                        }
                    }
                }
            }
            for(Neuron n : lines.get(line.size())) {
                for(Map.Entry<String, Neuron> entry : outputs.entrySet()) {
                    n.weight.put(entry.getValue(), r.nextDouble());
                }
            }
        }else {
            for(Map.Entry<String, Neuron> entry : inputs.entrySet()) {
                for(Map.Entry<String, Neuron> entry1 : inputs.entrySet()) {
                    entry.getValue().weight.put(entry1.getValue(), r.nextDouble());
                }
            }
        }
    }

    public void createInputs(String string) {
        inputs.put(string, createNeuron());
    }

    public void deleteInputs(String string) {
        inputs.remove(string);
    }

    public boolean isInputs(String string) {
        return inputs.get(string)!=null;
    }

    public Neuron getInputs(String string) {
        if(isInputs(string)) {
            return inputs.get(string);
        }
        return null;
    }

    public void createOutputs(String string) {
        outputs.put(string, createNeuron());
    }

    public void deleteOutputs(String string) {
        outputs.remove(string);
    }

    public boolean isOutputs(String string) {
        return outputs.get(string)!=null;
    }

    public Neuron getOutputs(String string) {
        return outputs.get(string);
    }

}
