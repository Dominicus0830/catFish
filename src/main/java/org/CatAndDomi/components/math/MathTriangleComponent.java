package org.CatAndDomi.components.math;

import org.CatAndDomi.components.Component;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class MathTriangleComponent extends Component {

    Map<Integer, Double> cos = new HashMap<>();
    Map<Integer, Double> sin = new HashMap<>();

    public MathTriangleComponent(JavaPlugin plugin) {
        super(plugin);
    }

    @Override
    public void load() {
        super.load();
        for(int a = 0; a<3600; a++) {
            double d = ((double) a)/10.0d;
            d*=Math.PI/180D;
            cos.put(a, Math.cos(d));
            sin.put(a, Math.sin(d));
        }
    }

    public double cos(double r) {
        int b = (int) (r*10);
        if(b>0) {
            b%=3600;
        }else {
            while(b<0) {
                b+=3600;
            }
        }
        return cos.get(b);
    }

    public double sin(double r) {
        int b = (int) (r*10);
        if(b>0) {
            b%=3600;
        }else {
            while(b<0) {
                b+=3600;
            }
        }
        return sin.get(b);
    }
}
