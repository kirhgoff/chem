package org.marina.chem;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import static java.lang.Math.*;

class Corpuscle {
    //имя для отладки
    private String name;
    //координаты центра частицы
    private Double x, y;
    //радиус
    private Double radius;
    //составляющие скорости
    private Double vx, vy;
    // масса
    private Double weight;

    public Corpuscle(String name, Double x, Double y, Double radius, Double weight, Double vx, Double vy) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.weight = weight;
        this.vx = vx;
        this.vy = vy;
    }

    public String info() {
        return (name + " - x = " + x + ", y = " + y + ", radius = " +
                radius + ", weight = " + weight + ", vx = " + vx + ", vy = " + vy);
    }

    public void move() {
        x = x + vx;
        y = y + vy;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public void setVx(Double vx) {
        this.vx = vx;
    }

    public void setVy(Double vy) {
        this.vy = vy;
    }

    public void setRadius(Double radius) {
        this.radius = radius;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public Double getVx() {
        return vx;
    }

    public Double getVy() {
        return vy;
    }

    public Double getRadius() {
        return radius;
    }

    public Double getWeight() {
        return weight;
    }
    public String getName() {
        return name;
    }

}

class Ball extends Corpuscle {
    Color color;

    public Ball(String name, Double x, Double y, Double radius, Double weight, Double vx, Double vy, Color color) {
        super(name, x, y, radius, weight, vx, vy);
        this.color = color;
    }
}

class Impact {
    private String border; // граница, с которой столкнулся шарик
    private Double x, y;   // координаты центра шарика на момент столкновения с границей или другим шариком
    private Ball b;        // шарик, с которым столкнулся шарик
    private Double time;   // часть текущего кванта, время, которое шарик будет двигаться после столкновения

    public Impact(String border, Double x, Double y, Ball b, Double time) {
        this.border = border;
        this.x = x;
        this.y = y;
        this.b = b;
        this.time = time;
    }

    public String getBorder() {
        return border;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public Ball getB() {
        return b;
    }

    public Double getTime() {
        return time;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public void setB(Ball b) {
        this.b = b;
    }

    public void setTime(Double time) {
        this.time = time;
    }

}

public class Box {
    // варианты цветов для шариков
    private Color[] colors = {Color.GREEN, Color.BLUE, Color.RED, Color.AQUAMARINE, Color.CORAL, Color.PLUM, Color.DARKGREY, Color.LAVENDER,
            Color.INDIGO, Color.ORCHID, Color.BLANCHEDALMOND, Color.YELLOW};
    // высота и ширина коробки
    private Double height, width;
    // набор шариков в коробке
    private ArrayList<Ball> balls;
    // ограничения для параметров шариков в коробке
    private final double maxMass = 50.0, maxRadius = 40.0, minMass = 1.0, minRadius = 15.0, minVx = 0.0, minVy = 0.0, maxV = 5.0;

    // создаем коробку
    public Box(Double width, Double height) {
        this.height = height;
        this.width = width;
        balls = new ArrayList<>();
    }

    // добавляем в коробку один шарик
    public void addBall(String name, Double x, Double y, Double radius, Double weight, Double vx, Double vy, Color color) {
        int i = balls.size();
        Ball b = new Ball(name, x, y, radius, weight, vx, vy, color);
        balls.add(i, b);
    }

    // наполняем коробку N шариками
    public void addBalls(Integer count) {
        Double s, dist;
        Double m, r, x, y, vx, vy;
        Color color;
        String name;

        // добавляем нужное количество шариков
        for (int i = 0; i < count; i++) {
            name = "ball" + i;
            m = randbyRange(minMass, maxMass);
            r = randbyRange(minRadius, maxRadius);
            x = randbyRange(r, width - r);
            y = randbyRange(r, height - r);
            vx = getSign() * randbyRange(minVx, min(r,5));
            vy = getSign() * randbyRange(minVy, min(r,5));
            color = colors[i % 12];
            addBall(name, x, y, r, m, vx, vy, color);
        }

        // удаляем пересекающиеся шарики
        Ball b1, b2;
        for (int i = 0; i < balls.size(); i++) {
            b1 = balls.get(i);
            int j = i + 1;
            while (j < balls.size()) {
                b2 = balls.get(j);
                dist = sqrt((b1.getX() - b2.getX()) * (b1.getX() - b2.getX()) + (b1.getY() - b2.getY()) * (b1.getY() - b2.getY()));
                if ((dist < (b1.getRadius() + b2.getRadius())) & (i != j)) balls.remove(j);
                else j = j + 1;
            }
        }

    }

    public void removeBalls() {
        balls.clear();
    }

    public Double getHeight() {
        return height;
    }

    public Double getWidth() {
        return width;
    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }

    public double getSign() {
        Double s = 0.0;
        s = Math.random() - Math.random();
        if (s < 0) s = -1.0;
        else if (s > 0) s = 1.0;
        else s = 0.0;
        return s;
    }

    public double randbyRange(Double minVal, Double maxVal) {
        Double val;
        val = minVal + (Math.random() * ((maxVal - minVal) + 1));
        return val;
    }

    public void move() {

        for (int i = 0; i < balls.size(); i++) {
            step(balls.get(i),1.0);
        }
    }

    public void step(Ball b1, Double q) {
        Impact imp;


        do {
            imp = calcImpact(b1, q);

            if (imp.getBorder() == "no") {
                b1.setX(b1.getX() + b1.getVx() * imp.getTime());
                b1.setY(b1.getY() + b1.getVy() * imp.getTime());
                q = 0.0;

            }
            if ((imp.getBorder() == "up") | (imp.getBorder() == "down")) {
                b1.setX(imp.getX());
                b1.setY(imp.getY());
                b1.setVy(-b1.getVy());
                q = imp.getTime();

            }
            if ((imp.getBorder() == "left") | (imp.getBorder() == "right")) {
                b1.setX(imp.getX());
                b1.setY(imp.getY());
                b1.setVx(-b1.getVx());
                q = imp.getTime();

            }

            if (imp.getBorder() == "ball") {
                Double vx1, vy1,m1,t1,vx2,vy2,m2;
                vx1 = b1.getVx();
                vy1 = b1.getVy();
                m1 = b1.getWeight();
                Integer o = balls.indexOf(imp.getB());
                vx2 = balls.get(o).getVx();
                vy2 = balls.get(o).getVy();
                m2 = balls.get(o).getWeight();
                b1.setVx(((m1-m2)*vx1+2*m2*vx2)/(m1+m2));
                b1.setVy(((m1 - m2) * vy1 + 2 * m2 * vy2) / (m1 + m2));
                b1.setX(imp.getX());
                b1.setY(imp.getY());
                q = imp.getTime();

                // а для этого шарика все должно быть совсем по-другому, но пока так
                balls.get(o).setVx(((m2-m1)*vx2+2*m1*vx1)/(m1+m2));
                balls.get(o).setVy(((m2-m1)*vy2+2*m1*vy1)/(m1+m2));
            }


        } while (q > 0);

    }


    // столкнулся ли шарик в результате перемещения с другим шариком или стенкой
    // на вход получаем шарик, для которого идут расчеты, и сколько времени ему осталось двигаться в текущем кванте времени
    public Impact calcImpact(Ball b, Double q) {

        Ball b1;
        Double x0, y0, vx0, vy0, r0,tdo, x,y, q1,tdoy,tdox;
        Boolean isBorderImpact, isBallImpact;
        Impact imp1, imp2, imp;
        // получаем параметры шарика
        b1 = b;
        x0 = b1.getX();
        y0 = b1.getY();
        vx0 = b1.getVx();
        vy0 = b1.getVy();
        r0 = b1.getRadius();
        // изначально предполагаем, что шарик до конца текущего кванта времени ни с чем не столкнется
        isBorderImpact = false;
        isBallImpact = false;
        imp = new Impact("no", null, null, null,q);
        q1 = q;
        tdo = q;
        imp1 = imp;
        imp2 = imp;
        // проверяем на столкновение со стенками

        if ((y0 + vy0*q +r0)>= height & (x0 + vx0*q - r0) > 0 & (x0 + vx0*q + r0) < width) {
            tdo = abs((height - (y0 + r0)) / vy0); // время до столкновения с верхней границей
            x = x0 + tdo*vx0; // координата центра на момент столкновения с верхней границей
            y = y0 + tdo*vy0; // координата центра на момент столкновения с верхней границей
            isBorderImpact = true;
            q1 = q - tdo; // уменьшаем остаток в данном кванте времени на то время, что потребовалось для перемещения до границы
            imp1 = new Impact("up", x, y, null,q1);
        };
        if ((y0+ vy0*q -r0)<= 0 & (x0 + vx0*q - r0) > 0 & (x0 + vx0*q + r0) < width) {
            tdo = abs((y0 - r0) / vy0); // время до столкновения с нижней границей
            x = x0 + tdo*vx0; // координата центра на момент столкновения с нижней границей
            y = y0 + tdo*vy0; // координата центра на момент столкновения с нижней границей
            isBorderImpact = true;
            q1 = q - tdo; // уменьшаем остаток в данном кванте времени на то время, что потребовалось для перемещения до границы
            imp1 = new Impact("down", x, y, null,q1);
        }
        if ((x0 + vx0*q + r0) >= width & (y0+ vy0*q - r0) > 0 & (y0+ vy0*q +r0) < height) {
            tdo = abs((width - (x0 + r0)) / vx0); // время до столкновения с правой границей
            x = x0 + tdo*vx0; // координата центра на момент столкновения с правой границей
            y = y0 + tdo*vy0; // координата центра на момент столкновения с правой границей
            isBorderImpact = true;
            q1 = q - tdo; // уменьшаем остаток в данном кванте времени на то время, что потребовалось для перемещения до границы
            imp1 = new Impact("right", x, y, null,q1);
        }
        if ((x0 + vx0*q - r0) <=0 & (y0+ vy0*q - r0) > 0 & (y0+ vy0*q +r0) < height) {
            tdo = abs((x0 - r0)/ vx0); // время до столкновения с левой границей
            x = x0 + tdo*vx0; // координата центра на момент столкновения с левой границей
            y = y0 + tdo*vy0; // координата центра на момент столкновения с левой границей
            isBorderImpact = true;
            q1 = q - tdo; // уменьшаем остаток в данном кванте времени на то время, что потребовалось для перемещения до границы
            imp1 = new Impact("left", x, y, null,q1);
        }
        if ((y0 + vy0*q +r0)>= height & (x0 + vx0*q - r0) > 0 & (x0 + vx0*q + r0) >= width) {
            tdoy = abs((height - (y0 + r0)) / vy0); // время до столкновения с верхней границей
            tdox = abs((width - (x0 + r0)) / vx0); // время до столкновения с правой границей
            if (tdox < tdoy) {
                tdo = tdox;
                x = x0 + tdo*vx0; // координата центра на момент столкновения с правой границей
                y = y0 + tdo*vy0; // координата центра на момент столкновения с правой границей
                isBorderImpact = true;
                q1 = q - tdo; // уменьшаем остаток в данном кванте времени на то время, что потребовалось для перемещения до границы
                imp1 = new Impact("right", x, y, null,q1);
            }
            else {
                tdo = tdoy;
                x = x0 + tdo*vx0; // координата центра на момент столкновения с верхней границей
                y = y0 + tdo*vy0; // координата центра на момент столкновения с верхней границей
                isBorderImpact = true;
                q1 = q - tdo; // уменьшаем остаток в данном кванте времени на то время, что потребовалось для перемещения до границы
                imp1 = new Impact("up", x, y, null,q1);
            }
        }
        if ((y0 + vy0*q +r0)>= height & (x0 + vx0*q - r0) <=0 & (x0 + vx0*q + r0)< width) {
            tdoy = abs((height - (y0 + r0)) / vy0); // время до столкновения с верхней границей
            tdox = abs((x0 - r0)/ vx0); // время до столкновения с левой границей
            if (tdox < tdoy) {
                tdo = tdox;
                x = x0 + tdo*vx0; // координата центра на момент столкновения с левой границей
                y = y0 + tdo*vy0; // координата центра на момент столкновения с левой границей
                isBorderImpact = true;
                q1 = q - tdo; // уменьшаем остаток в данном кванте времени на то время, что потребовалось для перемещения до границы
                imp1 = new Impact("left", x, y, null,q1);
            }
            else {
                tdo = tdoy;
                x = x0 + tdo*vx0; // координата центра на момент столкновения с верхней границей
                y = y0 + tdo*vy0; // координата центра на момент столкновения с верхней границей
                isBorderImpact = true;
                q1 = q - tdo; // уменьшаем остаток в данном кванте времени на то время, что потребовалось для перемещения до границы
                imp1 = new Impact("up", x, y, null,q1);
            }
        }
        if ((y0+ vy0*q -r0)<= 0 & (x0 + vx0*q - r0) > 0 & (x0 + vx0*q + r0) >= width) {
            tdoy = abs((y0 - r0) / vy0); // время до столкновения с нижней границей
            tdox = abs((width - (x0 + r0)) / vx0); // время до столкновения с правой границей
            if (tdox < tdoy) {
                tdo = tdox;
                x = x0 + tdo*vx0; // координата центра на момент столкновения с правой границей
                y = y0 + tdo*vy0; // координата центра на момент столкновения с правой границей
                isBorderImpact = true;
                q1 = q - tdo; // уменьшаем остаток в данном кванте времени на то время, что потребовалось для перемещения до границы
                imp1 = new Impact("right", x, y, null,q1);
            }
            else {
                tdo = tdoy;
                x = x0 + tdo*vx0; // координата центра на момент столкновения с нижней границей
                y = y0 + tdo*vy0; // координата центра на момент столкновения с нижней границей
                isBorderImpact = true;
                q1 = q - tdo; // уменьшаем остаток в данном кванте времени на то время, что потребовалось для перемещения до границы
                imp1 = new Impact("down", x, y, null,q1);
            }
        };
        if ((x0 + vx0*q - r0) <=0 & (x0 + vx0*q + r0)< width & (y0+ vy0*q -r0)<= 0) {
            tdoy = abs((y0 - r0) / vy0); // время до столкновения с нижней границей
            tdox = abs((x0 - r0)/ vx0); // время до столкновения с левой границей
            if (tdox < tdoy) {
                tdo = tdox;
                x = x0 + tdo*vx0; // координата центра на момент столкновения с левой границей
                y = y0 + tdo*vy0; // координата центра на момент столкновения с левой границей
                isBorderImpact = true;
                q1 = q - tdo; // уменьшаем остаток в данном кванте времени на то время, что потребовалось для перемещения до границы
                imp1 = new Impact("left", x, y, null,q1);
            }
            else {
                tdo = tdoy;
                x = x0 + tdo*vx0; // координата центра на момент столкновения с нижней границей
                y = y0 + tdo*vy0; // координата центра на момент столкновения с нижней границей
                isBorderImpact = true;
                q1 = q - tdo; // уменьшаем остаток в данном кванте времени на то время, что потребовалось для перемещения до границы
                imp1 = new Impact("down", x, y, null,q1);
            }
        }

        // проверяем на столкновение с другими шариками

        Double [] dst =  new Double[balls.size()];

        // считаем расстояние между текущим шариком и всеми остальными
        for (int i = 0; i < balls.size(); i++) {
            if ((balls.indexOf(b1) != i) & dist(b1, balls.get(i),q) < (r0 + balls.get(i).getRadius()))  dst[i] = dist(b1, balls.get(i),q);
            else dst[i] = height + width;
        }

        // ищем минимум среди посчитанных расстояний
        Double min = dst[0];
        Integer n = 0;
        for (int i = 1; i < balls.size(); i++) {
            if (dst[i] < min) {
                min = dst[i];
                n = i;
            }
        }

        Double t = q,s = 0.0;

        if (min == (height + width)) { // если ни с одинм шариком не столкнемся
            isBallImpact = false;
        }
        // переделать кусок

        else { // если столкнулись с шариком, то уменьшаем время и смотрим, по-прежнему ли есть столкновнеие
            isBallImpact = true;
            Integer k = 0;
            do {
                t = 4 * t / 5;
                k = k + 1;
            } while ((dist(b1, balls.get(n), t) <= (b1.getRadius() + balls.get(n).getRadius())) & (k < 5));
            // если несмотря на все усилия наползания избежать не удается (шарика "заклинило" между другими?)
            if ((dist(b1, balls.get(n), t) <= (b1.getRadius() + balls.get(n).getRadius()))) {
                t = 0.0;
                s = 0.05;
            }
        }

        // с чем раньше столкнется - со стенкой или с другим шариком
        if ((isBorderImpact == true) & (isBallImpact == true)) {
            if (t < tdo) {
                // столкновение с шариком
                // уменьшаем остаток в данном кванте времени на то время, что потребовалось для перемещения до столкновения
                imp2 = new Impact("ball", x0  +vx0 *t, y0  + vy0*t, balls.get(n), q-t-s);
                imp = imp2;
            } else { // столкновение со стенкой
                imp = imp1;
            }
        }
        if ((isBorderImpact == true) & (isBallImpact == false) ){
            imp = imp1;
        }
        if ((isBallImpact == true) & (isBorderImpact == false)) {
            // уменьшаем остаток в данном кванте времени на то время, что потребовалось для перемещения до столкновения
            imp2 = new Impact("ball", x0 + vx0*t, y0 + vy0*t, balls.get(n),q-t-s);
            imp = imp2;
        }

        return  imp;
    }


    // считаем расстояние между центрами двух шариков
    public Double dist(Ball b1, Ball b2, Double t) {
        Double newx1 = b1.getX()+b1.getVx()*t;
        Double newx2 = b2.getX();
        Double newy1 = b1.getY()+b1.getVy()*t;
        Double newy2 = b2.getY();
        Double dist = sqrt((newx1-newx2)*(newx1-newx2)+(newy1-newy2)*(newy1-newy2));
        return  dist;
    }


}