package util;

import java.util.Objects;

public class Vector {

    private static final Vector nullVector = new Vector(0, 0);
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

    public double crossProduct(Vector v) {
        return x * v.getY() - y * v.getX();
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

    public double dotProduct(Vector v) {
        return v.getX() * x + v.getY() * y;
    }

    public double angle(Vector v) {
        double sin = Math.asin(crossProduct(v)/(length() * v.length()));
        if(dotProduct(v) < 0) {
            sin *= -1;
            sin += Math.PI;
        }
        return sin;
    }

    public Vector rotate(double theta) {
        var sinTheta = Math.sin(theta);
        var cosTheta = Math.cos(theta);
        x = (x * cosTheta - y * sinTheta);
        y = (y * cosTheta + x * sinTheta);
        return this;
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

    public Vector normalize() {
        double length = length();
        if(length == 0) return this;
        multiply(1/length);
        return this;
    }

    public static Vector getNullVector() {
        return nullVector;
    }

    @Override
    public String toString() {
        return "Vector{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Double.compare(vector.x, x) == 0 && Double.compare(vector.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
