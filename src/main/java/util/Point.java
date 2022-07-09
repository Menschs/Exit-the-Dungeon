package util;

import java.awt.*;

public class Point {

    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void paint(Graphics2D g) {
        g.setColor(Color.red);
        g.drawRect((int) x, (int) y, 1, 1);
    }

    public boolean is(double x, double y) {
        return (this.x >= x - 1  && this.x  <= x + 1  && this.y <= y + 1 && this.y >= y - 1);
    }

    public void move(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void rotate(double pivotX, double pivotY, double theta) {
        var sdoubleheta = Math.sin(theta);
        var cosTheta = Math.cos(theta);

        double x = this.x - pivotX;
        double y = this.y - pivotY;

        this.x = (x * cosTheta - y * sdoubleheta + pivotX);
        this.y = (y * cosTheta + x * sdoubleheta + pivotY);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public Point changeY(double y) {
        return new Point(x, y);
    }
    public Point changeX(double x) {
        return new Point(x, y);
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
