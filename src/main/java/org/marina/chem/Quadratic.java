package org.marina.chem;

import java.util.ArrayList;

public class Quadratic  {
    private Double a;
    private Double b;
    private Double c;

    public Quadratic (Double paramA, Double paramB, Double paramC) {
        a = paramA;
        b = paramB;
        c = paramC;
    }

    public void setParam (Double paramA, Double paramB, Double paramC) {
        a = paramA;
        b = paramB;
        c = paramC;
    }

    private Double Discriminant() {
        return (b*b-4*a*c);
    }

    public ArrayList<Double> solve() {
        ArrayList<Double> ar  = new ArrayList<Double>();
        Double d = Discriminant();
        if (d == 0) {
            ar.add(-b/2*a);
        }
        else {
            if (d > 0) {
                ar.add((-b + Math.sqrt(d))/(2*a));
                ar.add((-b - Math.sqrt(d))/(2*a));
            }
        }
        return ar;
    }

    public String show() {
        return "(" + a + ")" + "x^2 + " + "(" + b + ")" + "x +" + "(" + c + ")" + " = 0";
    }


    public void demo() {
        System.out.println(show());
        System.out.println(solve());
    }
}