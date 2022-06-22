package util;

public class Vector {

    private double x, y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector(double x, double y, double x2, double y2) {
        this.x = x2 - x;
        this.y = y2 - y;
    }

    public Vector add(double x, double y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public void add(Vector v) {
        x += v.getX();
        y += v.getY();
    }

    public void subtract(double x, double y) {
        this.x -= x;
        this.y -= y;
    }

    public void subtract(Vector v) {
        x -= v.getX();
        y -= v.getY();
    }

    public Vector multiply(double multi) {
        x *= multi;
        y *= multi;
        return this;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void rotate(double theta) {
        var sinTheta = Math.sin(theta);
        var cosTheta = Math.cos(theta);
        x = (x * cosTheta - y * sinTheta);
        y = (y * cosTheta + x * sinTheta);
    }

    public double getY() {
        return y;
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public double lengthSquared() {
        return Math.pow(x, 2) + Math.pow(y, 2);
    }

    @Override
    public String toString() {
        return "Vector{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
