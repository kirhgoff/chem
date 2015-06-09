package org.marina.chem;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.scene.paint.Color;

import javax.swing.text.StyledEditorKit;
import java.util.ArrayList;
import static java.lang.Math.abs;
import static java.lang.Math.min;
import static java.lang.Math.sqrt;



public class Box {
    // �������� ������ ��� �������
    private Color[] colors = {Color.GREEN, Color.BLUE, Color.RED, Color.AQUAMARINE, Color.CORAL, Color.PLUM, Color.DARKGREY, Color.LAVENDER};
    // ������ � ������ �������
    private Double height, width;
    // ����� ������� � �������
    private ArrayList<Ball> balls;
    // ����������� ��� ���������� ������� � �������
    private final double maxMass = 50.0, maxRadius = 40.0, minMass = 1.0, minRadius = 15.0, minVx = 0.0, minVy = 0.0;

    // ������� �������
    public Box(Double width, Double height) {
        this.height = height;
        this.width = width;
        balls = new ArrayList<>();
    }

    // ��������� � ������� ���� �����
    public void addBall(String name, Double x, Double y, Double radius, Double weight, Double vx, Double vy, Color color) {
        int i = balls.size();
        Ball b = new Ball(name, x, y, radius, weight, vx, vy, 0, color);
        balls.add(i, b);
    }

    // ��������� ������� N ��������
    public void addBalls(Integer count) {
        Double s, dist;
        Double m, r, x, y, vx, vy;
        Color color;
        String name;

        // ��������� ������ ���������� �������
        for (int i = 0; i < count; i++) {
            name = "ball" + i;
            m = randbyRange(minMass, maxMass);
            r = randbyRange(minRadius, maxRadius);
            x = randbyRange(r, width - r);
            y = randbyRange(r, height - r);
            vx = getSign() * randbyRange(minVx, 1.0);
            vy = getSign() * randbyRange(minVy, 1.0);
            color = colors[i % 6];
            addBall(name, x, y, r, m, vx, vy, color);
        }

        // ������� �������������� ������
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
        Double q, x0,y0,vx0,vy0,r0,m0;
        Ball b1;
        Double p[] = new Double[4];
        // ���������� �������� ������ �����
        for (int i = 0; i < balls.size(); i++) {
            // �������� ������ � ������
            b1 = balls.get(i);
            x0 = b1.getX();
            y0 = b1.getY();
            vx0 = b1.getVx();
            vy0 = b1.getVy();
            r0 = b1.getRadius();
            m0 = b1.getWeight();
            // ���������� �������������, ��� ���� ����� ����� ����� ��������� � ��������� ���������
            q = 1.0;
            // ���� � ���������� ����������� �� ���������� �� ������� ��� ������ �������
            if (isImpact(b1) == true) {
                // �������� ������� �����
                p = calcImpact(b1,q);
            }
            else {
                // �������� ���������� ����������� �������, �������� �� ����������
                b1.setX(b1.getX()+b1.getVx());
                b1.setY(b1.getY()+b1.getVy());
            }

        }
    }

    // ���������� �� ����� � ���������� ����������� �� �������
    public Boolean isBorderImpact (Ball b1) {
        Boolean isImpact = false;
        String s = borderImpact (b1);
        if (s == "no") isImpact = false;
        else isImpact = true;

        return isImpact;
    }

    public Boolean isBallImpact(Ball b1) {
        Boolean isImpact = false;
        Ball b2;

        int j = balls.indexOf(b1);
        for (int i = 0; i < balls.size(); i++) {
            b2 = balls.get(i);
            if (j != i) {
                if (isBallsImpact(b1, b2) == true) {
                    isImpact = true;
                    break;
                }
            }

        }

        return isImpact;
    }

    // ���������� �� ������ ����� � ���������� ����������� �� ������ �������
    public Boolean isBallsImpact(Ball b1, Ball b2) {
        Boolean isImpact = false;
        Double dist = Dist(b1,b2);
        if (dist > (b1.getRadius() + b2.getRadius()))isImpact = false;
        else isImpact = true;

        return isImpact;
    }

    // ���������� �� ����� � ���������� ����������� � ������ ������� ��� �������
    public Boolean isImpact(Ball b1) {
        Ball b2;
        Boolean borderImpact = isBorderImpact(b1);
        Boolean ballImpact = false;
        int j = balls.indexOf(b1);
        for (int i = 0; i < balls.size(); i++) {
            b2 = balls.get(i);
            if (j != i) {
                if (isBallsImpact(b1, b2) == true) {
                    ballImpact = true;
                    break;
                }
            }

        }

        return (borderImpact & ballImpact);

    }

    // ����������, � ����� �������� ���������� �����
    public String borderImpact (Ball b1) {
        Double h = height, w = width;
        String border = "no";
        Double y0 = b1.getY();
        Double x0 = b1.getX();
        Double vx0 = b1.getVx();
        Double vy0 = b1.getVy();
        Double r = b1.getRadius();
        if ((y0+ vy0 +r)>= h & (x0 + vx0 + r) > 0 & (x0 + vx0 - r) < w & (vy0 != 0)) border = "up";
        if ((y0+ vy0 -r)<= 0 & (x0 + vx0 + r) > 0 & (x0 + vx0 - r) < w & (vy0 != 0)) border = "down";
        if ((x0 + vx0 + r) >= w & (y0+ vy0 - r) > 0 & (y0+ vy0 +r) < h & (vx0 != 0)) border = "right";
        if ((x0 + vx0 - r) <=0 & (y0+ vy0 - r) > 0 & (y0+ vy0 +r) < h & (vx0 != 0)) border = "left";
        if ((y0+ vy0 +r)>= h & (x0 + vx0 + r) > 0 & (x0 + vx0 + r) >= w) border = "upright";
        if ((y0+ vy0 +r)>= h & (x0 + vx0 - r) <=0 & (x0 + vx0 - r) < w) border = "upleft";
        if ((y0+ vy0 -r)<= 0 & (x0 + vx0 + r) > 0 & (x0 + vx0 + r) >= w) border = "downright";
        if ((y0+ vy0 -r)<= 0 & (x0 + vx0 - r) <=0 & (x0 + vx0 - r) < w) border = "downleft";
        return border;
    }

    // ������� ���������� ����� �������� ���� �������
    public Double Dist(Ball b1, Ball b2) {
        Double dist = sqrt((b1.getX()+b1.getVx()-b2.getX()+b2.getVx())*(b1.getX()+b1.getVx()-b2.getX()+b2.getVx()) +
                (b1.getY()+b1.getVy()-b2.getY()+b2.getVy())*(b1.getY()+b1.getVy()-b2.getY()+b2.getVy()));
        return  dist;
    }

    // ������� �������� � ����������, ������� �������� � ������� � ���������� ����� ������������
    public Double[] calcImpact (Ball b1, Double q) {
        Double p[] = new Double[4];
        String s;
        Double tdo;

        // ������������� ������ �� ������, ���� ����� ������� ����������
        // ����� ������� ��������� ��� �������� ����� ������������

        while (q > 0) {
            // ���� � ���������� ���������� ���� ������� �� � ��� �� ����������, �� ������ ��������
            if (isImpact(b1) == false) {
                b1.setX(b1.getX()+b1.getVx());
                b1.setY(b1.getY()+b1.getVy());
                q = 0.0;
            }
            else {
                // ���� ����������� �� �������
                if (isBorderImpact(b1) == true) {
                    // ��������, � ����� ������� �� ���� ��� �����������
                    s = borderImpact(b1);
                    // �������, ������� ������� ����� �� ��, ����� �� ��� ��������
                    tdo = tdoToBorder(b1, s, q);
                    // ������� �����, � ������� ���������� ����� � ������
                    p = borderCoord(b1, s, tdo);
                    // ������� ����� �� ������������ ��������� � ��������� ����� ������� �� tdo
                    q = q -tdo;
                    b1.setX(p[2]);
                    b1.setY(p[3]);
                    b1.setVx(p[0]);
                    b1.setVy(p[1]);
                    // � ������� ��������� ������� �� ������
                    p = calcImpact(b1, q);
                }
                // ���� ����������� � ������ �������
                if (isBallImpact(b1) == true) {
                    // ��������, ��� �����������
                    // �������, ������� ����� ������� �� ������������
                    // ������������� �������� ������� ������
                    // ������������� �������� ������� ������
                    // �������� ����� �������������� ���������� ��� ������� ������
                    // �������� ����� �������������� ���������� ��� ������� ������
                    // ��������� ����� ������� ��� ������� ������ �� tdo
                    // ������������� ����� ������� ��� ������� ������ ������ ������ ������� ��� ������� ������
                    // ��������� calcImpact ��� ������� ������
                    // ��������� calcImpact ��� ������� ������
                }

            }

        }


        return p;
    }

    public Double[] borderCoord (Ball b1, String s, Double tdo) {
        Double p[] = new Double [4];
        Double tdo1, tdo2, tdo3;
        Double r0 = b1.getRadius();
        p[2] = b1.getX();
        p[3] = b1.getY();
        p[0] = b1.getVx();
        p[1] = b1.getVy();

        // � ������� ��� � ������
        if ((s == "up")|(s == "down")) {
            p[2] = p[2] + p[0]*tdo;
            p[3] = p[3] + p[1]*tdo;
            p[1] = -p[1];
        }
        // � ������ ��� � �����
        if ((s == "right")|(s == "left")) {
            p[2] = p[2] + p[0]*tdo;
            p[3] = p[3] + p[1]*tdo;
            p[0] = -p[0];
        }
        // � ������ ������ �����
        if (s == "downright") {
            tdo1 = abs((p[3] - r0) / p[1]); //����� �� ������������ � ������ �������
            tdo2 = abs((width - (p[2] + r0)) / p[0]); //����� �� ������������ � ������ �������
            if (tdo1 < tdo2) {
                tdo3 = tdo1; //����� �� ������������ �� �������
                p[2] = p[2] + p[0]*tdo3;
                p[3] = p[3] + p[1]*tdo3;
                p[1] = -p[1];
            } else {
                tdo3 = tdo2; //����� �� ������������ �� �������
                p[2] = p[2] + p[0]*tdo3;
                p[3] = p[3] + p[1]*tdo3;
                p[0] = -p[0];

            }
        }
        // � ������ ����� �����
        if (s == "downleft") {
            if (((p[2] - r0) ==0) & ((p[3] - r0)==0)) {
                tdo1 = 0.0; tdo2 = 0.0;
            }
            else {
                tdo1 = abs((p[3] - r0) / p[1]); //����� �� ������������ � ������ �������
                tdo2 = abs((p[2] - r0) / p[0]); //����� �� ������������ � ����� �������
            }
            if (tdo1 < tdo2) {
                tdo3 = tdo1; //����� �� ������������ �� �������
                p[2] = p[2] + p[0]*tdo3;
                p[3] = p[3] + p[1]*tdo3;
                p[1] = -p[1];
            } else {
                tdo3 = tdo2; //����� �� ������������ �� �������
                p[2] = p[2] + p[0]*tdo3;
                p[3] = p[3] + p[1]*tdo3;
                p[0] = -p[0];
            }
        }
        // � ������� ������ �����
        if (s == "upright") {
            tdo1 = abs((height - (p[3] + r0)) / p[1]); //����� �� ������������ � ������� �������
            tdo2 = abs((width - (p[2] + r0)) / p[0]); //����� �� ������������ � ������ �������
            if (tdo1 < tdo2) {
                tdo3 = tdo1; //����� �� ������������ �� �������
                p[2] = p[2] + p[0]*tdo3;
                p[3] = p[3] + p[1]*tdo3;
                p[1] = -p[1];
            }
            else {
                tdo3 = tdo2; //����� �� ������������ �� �������
                p[2] = p[2] + p[0]*tdo3;
                p[3] = p[3] + p[1]*tdo3;
                p[0] = -p[0];
            }
        }
        // � ������� ����� �����
        if (s == "upleft") {
            tdo1 = abs((height - (p[3] + r0)) / p[1]); //����� �� ������������ � ������� �������
            tdo2 = abs((p[2] - r0) / p[0]); //����� �� ������������ � ����� �������
            if (tdo1 < tdo2) {
                tdo3 = tdo1; //����� �� ������������ �� �������
                p[2] = p[2] + p[0]*tdo3;
                p[3] = p[3] + p[1]*tdo3;
                p[1] = -p[1];
            } else {
                tdo3 = tdo2; //����� �� ������������ �� �������
                p[2] = p[2] + p[0]*tdo3;
                p[3] = p[3] + p[1]*tdo3;
                p[0] = -p[0];
            }
        }

        return p;

    }



    // ����� ������� ���������� �� �������
    public  Double tdoToBorder(Ball b1, String s, Double q) {
        Double tdo = 0.0;
        Double y0 = b1.getY();
        Double x0 = b1.getX();
        Double vx0 = b1.getVx();
        Double vy0 = b1.getVy();
        Double r0 = b1.getRadius();

        if (s == "right") tdo = (x0 + r0 - width)/vx0;
        if (s == "left") tdo = (-x0-r0)/vx0;
        if (s == "up") tdo = (y0 + r0 -height)/vy0;
        if (s == "down") tdo = (-y0 - r0)/vy0;
        if (s == "downright") tdo = min((-y0 - r0)/vy0,(x0 + r0 - width)/vx0);
        if (s == "downleft" )tdo = min((-y0 - r0)/vy0, (-x0-r0)/vx0);
        if (s == "upright") tdo = min((y0 + r0 -height)/vy0,(x0 + r0 - width)/vx0);
        if (s == "upleft" )tdo = min((y0 + r0 -height)/vy0, (-x0-r0)/vx0);

        return  (q-tdo);
    }
}






//import com.sun.org.apache.xpath.internal.operations.Bool;
//import javafx.scene.paint.Color;

//import javax.swing.text.StyledEditorKit;
//import java.util.ArrayList;
//import static java.lang.Math.abs;
//import static java.lang.Math.min;
//import static java.lang.Math.sqrt;

class Corpuscle {
    //��� ��� �������
    private String name;
    //���������� ������ �������
    private Double x, y;
    //������
    private Double radius;
    //������������ ��������
    private Double vx, vy;
    // �����
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
    Integer state;
    Color color;
    //0 - ��������� � �������, 1 - ��������, 2 - ���������� ������������

    public Ball(String name, Double x, Double y, Double radius, Double weight, Double vx, Double vy, Integer state, Color color) {
        super(name, x, y, radius, weight, vx, vy);
        this.state = state;
        this.color = color;

    }
}
