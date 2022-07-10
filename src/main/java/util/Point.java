package util;

import java.awt.*;

public class Point {

    private float x;
    private float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void paint(Graphics2D g) {
        g.setColor(Color.red);
        g.drawRect((int) x, (int) y, 1, 1);
    }

    public boolean is(float x, float y) {
        return (this.x >= x - 1  && this.x  <= x + 1  && this.y <= y + 1 && this.y >= y - 1);
    }

    public void move(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void rotate(float pivotX, float pivotY, float theta) {
        var sfloatheta = Math.sin(theta);
        var cosTheta = Math.cos(theta);

        float x = this.x - pivotX;
        float y = this.y - pivotY;

        this.x = (float) (x * cosTheta - y * sfloatheta + pivotX);
        this.y = (float) (y * cosTheta + x * sfloatheta + pivotY);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public Point changeY(float y) {
        return new Point(x, y);
    }
    public Point changeX(float x) {
        return new Point(x, y);
    }

    public void setY(float y) {
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
