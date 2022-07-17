package util;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;

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

    public Point move(float x, float y) {
        return new Point(this.x + x, this.y + y);
    }

    public void rotate(float pivotX, float pivotY, float theta) {
        var sinTheta = Math.sin(theta);
        var cosTheta = Math.cos(theta);

        float x = this.x - pivotX;
        float y = this.y - pivotY;

        this.x = (float) (x * cosTheta - y * sinTheta + pivotX);
        this.y = (float) (y * cosTheta + x * sinTheta + pivotY);
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
