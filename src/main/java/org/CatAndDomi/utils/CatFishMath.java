package org.CatAndDomi.utils;

public class CatFishMath {

    public static final double e = 2.7182818284590455D;

    public double sigmoid(double dx, double value) {
        return 1D/(1D+(1/Math.pow(e, dx*value)));
    }

    public double sigmoid_pri(double dx, double value) {
        double s = sigmoid(dx, value);
        return s*(1-s);
    }

}
