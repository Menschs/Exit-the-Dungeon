package entities;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Point {

    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void paint(Graphics2D g) {
        g.setColor(Color.red);
        g.drawRect(x, y, 1, 1);
    }

    public boolean is(double x, double y) {
        return ((this.x >= x - 1  && this.y  >= y - 1 ) || (this.x  <= x + 1 && this.y <= y + 1));
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void rotate(int pivotX, int pivotY, double theta) {
        var sinTheta = Math.sin(theta);
        var cosTheta = Math.cos(theta);

        int x = this.x - pivotX;
        int y = this.y - pivotY;

        this.x = (int) (x * cosTheta - y * sinTheta + pivotX);
        this.y = (int) (y * cosTheta + x * sinTheta + pivotY);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
