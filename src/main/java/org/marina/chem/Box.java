
package org.marina.chem;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import static java.lang.Math.*;

class Corpuscle {
    //èìÿ äëÿ îòëàäêè
    private String name;
    //êîîðäèíàòû öåíòðà ÷àñòèöû
    private Double x, y;
    //ðàäèóñ
    private Double radius;
    //ñîñòàâëÿþùèå ñêîðîñòè
    private Double vx, vy;
    // ìàññà
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
    private String border; // ãðàíèöà, ñ êîòîðîé ñòîëêíóëñÿ øàðèê
    private Double x, y;   // êîîðäèíàòû öåíòðà øàðèêà íà ìîìåíò ñòîëêíîâåíèÿ ñ ãðàíèöåé èëè äðóãèì øàðèêîì
    private Ball b;        // øàðèê, ñ êîòîðûì ñòîëêíóëñÿ øàðèê
    private Double time;   // ÷àñòü òåêóùåãî êâàíòà, âðåìÿ, êîòîðîå øàðèê áóäåò äâèãàòüñÿ ïîñëå ñòîëêíîâåíèÿ

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
    // âàðèàíòû öâåòîâ äëÿ øàðèêîâ
    private Color[] colors = {Color.GREEN, Color.BLUE, Color.RED, Color.AQUAMARINE, Color.CORAL, Color.PLUM, Color.DARKGREY, Color.LAVENDER,
            Color.INDIGO, Color.ORCHID, Color.BLANCHEDALMOND, Color.YELLOW};
    // âûñîòà è øèðèíà êîðîáêè
    private Double height, width;
    // íàáîð øàðèêîâ â êîðîáêå
    private ArrayList<Ball> balls;
    // îãðàíè÷åíèÿ äëÿ ïàðàìåòðîâ øàðèêîâ â êîðîáêå
    private final double maxMass = 50.0, maxRadius = 40.0, minMass = 1.0, minRadius = 15.0, minVx = 0.0, minVy = 0.0, maxV = 5.0;

    // ñîçäàåì êîðîáêó
    public Box(Double width, Double height) {
        this.height = height;
        this.width = width;
        balls = new ArrayList<>();
    }

    // äîáàâëÿåì â êîðîáêó îäèí øàðèê
    public void addBall(String name, Double x, Double y, Double radius, Double weight, Double vx, Double vy, Color color) {
        int i = balls.size();
        Ball b = new Ball(name, x, y, radius, weight, vx, vy, color);
        balls.add(i, b);
    }

    // íàïîëíÿåì êîðîáêó N øàðèêàìè
    public void addBalls(Integer count) {
        Double s, dist;
        Double m, r, x, y, vx, vy;
        Color color;
        String name;

        // äîáàâëÿåì íóæíîå êîëè÷åñòâî øàðèêîâ
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

        // óäàëÿåì ïåðåñåêàþùèåñÿ øàðèêè
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

                // à äëÿ ýòîãî øàðèêà âñå äîëæíî áûòü ñîâñåì ïî-äðóãîìó, íî ïîêà òàê
                balls.get(o).setVx(((m2-m1)*vx2+2*m1*vx1)/(m1+m2));
                balls.get(o).setVy(((m2-m1)*vy2+2*m1*vy1)/(m1+m2));
            }


        } while (q > 0);

    }


    // ñòîëêíóëñÿ ëè øàðèê â ðåçóëüòàòå ïåðåìåùåíèÿ ñ äðóãèì øàðèêîì èëè ñòåíêîé
    // íà âõîä ïîëó÷àåì øàðèê, äëÿ êîòîðîãî èäóò ðàñ÷åòû, è ñêîëüêî âðåìåíè åìó îñòàëîñü äâèãàòüñÿ â òåêóùåì êâàíòå âðåìåíè
    public Impact calcImpact(Ball b, Double q) {

        Ball b1;
        Double x0, y0, vx0, vy0, r0,tdo, x,y, q1,tdoy,tdox;
        Boolean isBorderImpact, isBallImpact;
        Impact imp1, imp2, imp;
        // ïîëó÷àåì ïàðàìåòðû øàðèêà
        b1 = b;
        x0 = b1.getX();
        y0 = b1.getY();
        vx0 = b1.getVx();
        vy0 = b1.getVy();
        r0 = b1.getRadius();
        // èçíà÷àëüíî ïðåäïîëàãàåì, ÷òî øàðèê äî êîíöà òåêóùåãî êâàíòà âðåìåíè íè ñ ÷åì íå ñòîëêíåòñÿ
        isBorderImpact = false;
        isBallImpact = false;
        imp = new Impact("no", null, null, null,q);
        q1 = q;
        tdo = q;
        imp1 = imp;
        imp2 = imp;
        // ïðîâåðÿåì íà ñòîëêíîâåíèå ñî ñòåíêàìè

        if ((y0 + vy0*q +r0)>= height & (x0 + vx0*q - r0) > 0 & (x0 + vx0*q + r0) < width) {
            tdo = abs((height - (y0 + r0)) / vy0); // âðåìÿ äî ñòîëêíîâåíèÿ ñ âåðõíåé ãðàíèöåé
            x = x0 + tdo*vx0; // êîîðäèíàòà öåíòðà íà ìîìåíò ñòîëêíîâåíèÿ ñ âåðõíåé ãðàíèöåé
            y = y0 + tdo*vy0; // êîîðäèíàòà öåíòðà íà ìîìåíò ñòîëêíîâåíèÿ ñ âåðõíåé ãðàíèöåé
            isBorderImpact = true;
            q1 = q - tdo; // óìåíüøàåì îñòàòîê â äàííîì êâàíòå âðåìåíè íà òî âðåìÿ, ÷òî ïîòðåáîâàëîñü äëÿ ïåðåìåùåíèÿ äî ãðàíèöû
            imp1 = new Impact("up", x, y, null,q1);
        };
        if ((y0+ vy0*q -r0)<= 0 & (x0 + vx0*q - r0) > 0 & (x0 + vx0*q + r0) < width) {
            tdo = abs((y0 - r0) / vy0); // âðåìÿ äî ñòîëêíîâåíèÿ ñ íèæíåé ãðàíèöåé
            x = x0 + tdo*vx0; // êîîðäèíàòà öåíòðà íà ìîìåíò ñòîëêíîâåíèÿ ñ íèæíåé ãðàíèöåé
            y = y0 + tdo*vy0; // êîîðäèíàòà öåíòðà íà ìîìåíò ñòîëêíîâåíèÿ ñ íèæíåé ãðàíèöåé
            isBorderImpact = true;
            q1 = q - tdo; // óìåíüøàåì îñòàòîê â äàííîì êâàíòå âðåìåíè íà òî âðåìÿ, ÷òî ïîòðåáîâàëîñü äëÿ ïåðåìåùåíèÿ äî ãðàíèöû
            imp1 = new Impact("down", x, y, null,q1);
        }
        if ((x0 + vx0*q + r0) >= width & (y0+ vy0*q - r0) > 0 & (y0+ vy0*q +r0) < height) {
            tdo = abs((width - (x0 + r0)) / vx0); // âðåìÿ äî ñòîëêíîâåíèÿ ñ ïðàâîé ãðàíèöåé
            x = x0 + tdo*vx0; // êîîðäèíàòà öåíòðà íà ìîìåíò ñòîëêíîâåíèÿ ñ ïðàâîé ãðàíèöåé
            y = y0 + tdo*vy0; // êîîðäèíàòà öåíòðà íà ìîìåíò ñòîëêíîâåíèÿ ñ ïðàâîé ãðàíèöåé
            isBorderImpact = true;
            q1 = q - tdo; // óìåíüøàåì îñòàòîê â äàííîì êâàíòå âðåìåíè íà òî âðåìÿ, ÷òî ïîòðåáîâàëîñü äëÿ ïåðåìåùåíèÿ äî ãðàíèöû
            imp1 = new Impact("right", x, y, null,q1);
        }
        if ((x0 + vx0*q - r0) <=0 & (y0+ vy0*q - r0) > 0 & (y0+ vy0*q +r0) < height) {
            tdo = abs((x0 - r0)/ vx0); // âðåìÿ äî ñòîëêíîâåíèÿ ñ ëåâîé ãðàíèöåé
            x = x0 + tdo*vx0; // êîîðäèíàòà öåíòðà íà ìîìåíò ñòîëêíîâåíèÿ ñ ëåâîé ãðàíèöåé
            y = y0 + tdo*vy0; // êîîðäèíàòà öåíòðà íà ìîìåíò ñòîëêíîâåíèÿ ñ ëåâîé ãðàíèöåé
            isBorderImpact = true;
            q1 = q - tdo; // óìåíüøàåì îñòàòîê â äàííîì êâàíòå âðåìåíè íà òî âðåìÿ, ÷òî ïîòðåáîâàëîñü äëÿ ïåðåìåùåíèÿ äî ãðàíèöû
            imp1 = new Impact("left", x, y, null,q1);
        }
        if ((y0 + vy0*q +r0)>= height & (x0 + vx0*q - r0) > 0 & (x0 + vx0*q + r0) >= width) {
            tdoy = abs((height - (y0 + r0)) / vy0); // âðåìÿ äî ñòîëêíîâåíèÿ ñ âåðõíåé ãðàíèöåé
            tdox = abs((width - (x0 + r0)) / vx0); // âðåìÿ äî ñòîëêíîâåíèÿ ñ ïðàâîé ãðàíèöåé
            if (tdox < tdoy) {
                tdo = tdox;
                x = x0 + tdo*vx0; // êîîðäèíàòà öåíòðà íà ìîìåíò ñòîëêíîâåíèÿ ñ ïðàâîé ãðàíèöåé
                y = y0 + tdo*vy0; // êîîðäèíàòà öåíòðà íà ìîìåíò ñòîëêíîâåíèÿ ñ ïðàâîé ãðàíèöåé
                isBorderImpact = true;
                q1 = q - tdo; // óìåíüøàåì îñòàòîê â äàííîì êâàíòå âðåìåíè íà òî âðåìÿ, ÷òî ïîòðåáîâàëîñü äëÿ ïåðåìåùåíèÿ äî ãðàíèöû
                imp1 = new Impact("right", x, y, null,q1);
            }
            else {
                tdo = tdoy;
                x = x0 + tdo*vx0; // êîîðäèíàòà öåíòðà íà ìîìåíò ñòîëêíîâåíèÿ ñ âåðõíåé ãðàíèöåé
                y = y0 + tdo*vy0; // êîîðäèíàòà öåíòðà íà ìîìåíò ñòîëêíîâåíèÿ ñ âåðõíåé ãðàíèöåé
                isBorderImpact = true;
                q1 = q - tdo; // óìåíüøàåì îñòàòîê â äàííîì êâàíòå âðåìåíè íà òî âðåìÿ, ÷òî ïîòðåáîâàëîñü äëÿ ïåðåìåùåíèÿ äî ãðàíèöû
                imp1 = new Impact("up", x, y, null,q1);
            }
        }
        if ((y0 + vy0*q +r0)>= height & (x0 + vx0*q - r0) <=0 & (x0 + vx0*q + r0)< width) {
            tdoy = abs((height - (y0 + r0)) / vy0); // âðåìÿ äî ñòîëêíîâåíèÿ ñ âåðõíåé ãðàíèöåé
            tdox = abs((x0 - r0)/ vx0); // âðåìÿ äî ñòîëêíîâåíèÿ ñ ëåâîé ãðàíèöåé
            if (tdox < tdoy) {
                tdo = tdox;
                x = x0 + tdo*vx0; // êîîðäèíàòà öåíòðà íà ìîìåíò ñòîëêíîâåíèÿ ñ ëåâîé ãðàíèöåé
                y = y0 + tdo*vy0; // êîîðäèíàòà öåíòðà íà ìîìåíò ñòîëêíîâåíèÿ ñ ëåâîé ãðàíèöåé
                isBorderImpact = true;
                q1 = q - tdo; // óìåíüøàåì îñòàòîê â äàííîì êâàíòå âðåìåíè íà òî âðåìÿ, ÷òî ïîòðåáîâàëîñü äëÿ ïåðåìåùåíèÿ äî ãðàíèöû
                imp1 = new Impact("left", x, y, null,q1);
            }
            else {
                tdo = tdoy;
                x = x0 + tdo*vx0; // êîîðäèíàòà öåíòðà íà ìîìåíò ñòîëêíîâåíèÿ ñ âåðõíåé ãðàíèöåé
                y = y0 + tdo*vy0; // êîîðäèíàòà öåíòðà íà ìîìåíò ñòîëêíîâåíèÿ ñ âåðõíåé ãðàíèöåé
                isBorderImpact = true;
                q1 = q - tdo; // óìåíüøàåì îñòàòîê â äàííîì êâàíòå âðåìåíè íà òî âðåìÿ, ÷òî ïîòðåáîâàëîñü äëÿ ïåðåìåùåíèÿ äî ãðàíèöû
                imp1 = new Impact("up", x, y, null,q1);
            }
        }
        if ((y0+ vy0*q -r0)<= 0 & (x0 + vx0*q - r0) > 0 & (x0 + vx0*q + r0) >= width) {
            tdoy = abs((y0 - r0) / vy0); // âðåìÿ äî ñòîëêíîâåíèÿ ñ íèæíåé ãðàíèöåé
            tdox = abs((width - (x0 + r0)) / vx0); // âðåìÿ äî ñòîëêíîâåíèÿ ñ ïðàâîé ãðàíèöåé
            if (tdox < tdoy) {
                tdo = tdox;
                x = x0 + tdo*vx0; // êîîðäèíàòà öåíòðà íà ìîìåíò ñòîëêíîâåíèÿ ñ ïðàâîé ãðàíèöåé
                y = y0 + tdo*vy0; // êîîðäèíàòà öåíòðà íà ìîìåíò ñòîëêíîâåíèÿ ñ ïðàâîé ãðàíèöåé
                isBorderImpact = true;
                q1 = q - tdo; // óìåíüøàåì îñòàòîê â äàííîì êâàíòå âðåìåíè íà òî âðåìÿ, ÷òî ïîòðåáîâàëîñü äëÿ ïåðåìåùåíèÿ äî ãðàíèöû
                imp1 = new Impact("right", x, y, null,q1);
            }
            else {
                tdo = tdoy;
                x = x0 + tdo*vx0; // êîîðäèíàòà öåíòðà íà ìîìåíò ñòîëêíîâåíèÿ ñ íèæíåé ãðàíèöåé
                y = y0 + tdo*vy0; // êîîðäèíàòà öåíòðà íà ìîìåíò ñòîëêíîâåíèÿ ñ íèæíåé ãðàíèöåé
                isBorderImpact = true;
                q1 = q - tdo; // óìåíüøàåì îñòàòîê â äàííîì êâàíòå âðåìåíè íà òî âðåìÿ, ÷òî ïîòðåáîâàëîñü äëÿ ïåðåìåùåíèÿ äî ãðàíèöû
                imp1 = new Impact("down", x, y, null,q1);
            }
        };
        if ((x0 + vx0*q - r0) <=0 & (x0 + vx0*q + r0)< width & (y0+ vy0*q -r0)<= 0) {
            tdoy = abs((y0 - r0) / vy0); // âðåìÿ äî ñòîëêíîâåíèÿ ñ íèæíåé ãðàíèöåé
            tdox = abs((x0 - r0)/ vx0); // âðåìÿ äî ñòîëêíîâåíèÿ ñ ëåâîé ãðàíèöåé
            if (tdox < tdoy) {
                tdo = tdox;
                x = x0 + tdo*vx0; // êîîðäèíàòà öåíòðà íà ìîìåíò ñòîëêíîâåíèÿ ñ ëåâîé ãðàíèöåé
                y = y0 + tdo*vy0; // êîîðäèíàòà öåíòðà íà ìîìåíò ñòîëêíîâåíèÿ ñ ëåâîé ãðàíèöåé
                isBorderImpact = true;
                q1 = q - tdo; // óìåíüøàåì îñòàòîê â äàííîì êâàíòå âðåìåíè íà òî âðåìÿ, ÷òî ïîòðåáîâàëîñü äëÿ ïåðåìåùåíèÿ äî ãðàíèöû
                imp1 = new Impact("left", x, y, null,q1);
            }
            else {
                tdo = tdoy;
                x = x0 + tdo*vx0; // êîîðäèíàòà öåíòðà íà ìîìåíò ñòîëêíîâåíèÿ ñ íèæíåé ãðàíèöåé
                y = y0 + tdo*vy0; // êîîðäèíàòà öåíòðà íà ìîìåíò ñòîëêíîâåíèÿ ñ íèæíåé ãðàíèöåé
                isBorderImpact = true;
                q1 = q - tdo; // óìåíüøàåì îñòàòîê â äàííîì êâàíòå âðåìåíè íà òî âðåìÿ, ÷òî ïîòðåáîâàëîñü äëÿ ïåðåìåùåíèÿ äî ãðàíèöû
                imp1 = new Impact("down", x, y, null,q1);
            }
        }

        // ïðîâåðÿåì íà ñòîëêíîâåíèå ñ äðóãèìè øàðèêàìè

        Double [] dst =  new Double[balls.size()];

        // ñ÷èòàåì ðàññòîÿíèå ìåæäó òåêóùèì øàðèêîì è âñåìè îñòàëüíûìè
        for (int i = 0; i < balls.size(); i++) {
            if ((balls.indexOf(b1) != i) & dist(b1, balls.get(i),q) < (r0 + balls.get(i).getRadius()))  dst[i] = dist(b1, balls.get(i),q);
            else dst[i] = height + width;
        }

        // èùåì ìèíèìóì ñðåäè ïîñ÷èòàííûõ ðàññòîÿíèé
        Double min = dst[0];
        Integer n = 0;
        for (int i = 1; i < balls.size(); i++) {
            if (dst[i] < min) {
                min = dst[i];
                n = i;
            }
        }

        Double t = q,s = 0.0;

        if (min == (height + width)) { // åñëè íè ñ îäèíì øàðèêîì íå ñòîëêíåìñÿ
            isBallImpact = false;
        }
        // ïåðåäåëàòü êóñîê

        else { // åñëè ñòîëêíóëèñü ñ øàðèêîì, òî óìåíüøàåì âðåìÿ è ñìîòðèì, ïî-ïðåæíåìó ëè åñòü ñòîëêíîâíåèå
            isBallImpact = true;
            Integer k = 0;
            do {
                t = 4 * t / 5;
                k = k + 1;
            } while ((dist(b1, balls.get(n), t) <= (b1.getRadius() + balls.get(n).getRadius())) & (k < 5));
            // åñëè íåñìîòðÿ íà âñå óñèëèÿ íàïîëçàíèÿ èçáåæàòü íå óäàåòñÿ (øàðèêà "çàêëèíèëî" ìåæäó äðóãèìè?)
            if ((dist(b1, balls.get(n), t) <= (b1.getRadius() + balls.get(n).getRadius()))) {
                t = 0.0;
                s = 0.05;
            }
        }

        // ñ ÷åì ðàíüøå ñòîëêíåòñÿ - ñî ñòåíêîé èëè ñ äðóãèì øàðèêîì
        if ((isBorderImpact == true) & (isBallImpact == true)) {
            if (t < tdo) {
                // ñòîëêíîâåíèå ñ øàðèêîì
                // óìåíüøàåì îñòàòîê â äàííîì êâàíòå âðåìåíè íà òî âðåìÿ, ÷òî ïîòðåáîâàëîñü äëÿ ïåðåìåùåíèÿ äî ñòîëêíîâåíèÿ
                imp2 = new Impact("ball", x0  +vx0 *t, y0  + vy0*t, balls.get(n), q-t-s);
                imp = imp2;
            } else { // ñòîëêíîâåíèå ñî ñòåíêîé
                imp = imp1;
            }
        }
        if ((isBorderImpact == true) & (isBallImpact == false) ){
            imp = imp1;
        }
        if ((isBallImpact == true) & (isBorderImpact == false)) {
            // óìåíüøàåì îñòàòîê â äàííîì êâàíòå âðåìåíè íà òî âðåìÿ, ÷òî ïîòðåáîâàëîñü äëÿ ïåðåìåùåíèÿ äî ñòîëêíîâåíèÿ
            imp2 = new Impact("ball", x0 + vx0*t, y0 + vy0*t, balls.get(n),q-t-s);
            imp = imp2;
        }

        return  imp;
    }


    // ñ÷èòàåì ðàññòîÿíèå ìåæäó öåíòðàìè äâóõ øàðèêîâ
    public Double dist(Ball b1, Ball b2, Double t) {
        Double newx1 = b1.getX()+b1.getVx()*t;
        Double newx2 = b2.getX();
        Double newy1 = b1.getY()+b1.getVy()*t;
        Double newy2 = b2.getY();
        Double dist = sqrt((newx1-newx2)*(newx1-newx2)+(newy1-newy2)*(newy1-newy2));
        return  dist;
    }


}
