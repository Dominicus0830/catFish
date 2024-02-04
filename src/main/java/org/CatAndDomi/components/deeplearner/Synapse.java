package org.CatAndDomi.components.deeplearner;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Synapse {

    Map<String, Neuron> inputs = new HashMap<>();
    Map<String, Neuron> outputs = new HashMap<>();
    Map<Integer, ArrayList<Neuron>> lines = new HashMap<>();
    ArrayList<Integer> line = new ArrayList<>();

    public Synapse() {

    }

    public void setLine(ArrayList<Integer> line) {
        this.line = line!=null?line:new ArrayList<>();
    }

    public void build() {
        lines.clear();
        for(int i = 0; i<line.size(); i++) {
            ArrayList<Neuron> neurons = new ArrayList<>(line.get(i));
            for(int j = 0; j<line.get(i); j++) {
                neurons.add(new Neuron());
            }
            lines.put(i, neurons);
        }
    }

    public void createInputs(String string) {
        inputs.put(string, new Neuron());
    }

    public void deleteInputs(String string) {
        inputs.remove(string);
    }

    public boolean isInputs(String string) {
        return inputs.get(string)!=null;
    }

    public void createOutputs(String string) {
        outputs.put(string, new Neuron());
    }

    public void deleteOutputs(String string) {
        outputs.remove(string);
    }

    public boolean isOutputs(String string) {
        return outputs.get(string)!=null;
    }

}
