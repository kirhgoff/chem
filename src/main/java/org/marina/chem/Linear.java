package org.marina.chem;

import java.util.ArrayList;
import java.util.List;



class Linear  {

    private Double a;
    private Double b;

    public Linear (Double paramA, Double paramB) {
        a = paramA;
        b = paramB;
    }

    public void setParam(Double paramA, Double paramB) {
        a = paramA;
        b = paramB;
    }

    public ArrayList<Double> solve() {
        ArrayList<Double> ar  = new ArrayList<Double>();
        ar.add(-b/a);
        return ar;
    }

    public String show() {
        return "(" + a + ")" + "x +" + "(" + b + ")" + " = 0";
    }

    public void demo() {
        System.out.println(show());
        System.out.println(solve());
    }

}
