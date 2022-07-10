package util;

import objects.entities.Player;

import java.util.Objects;

public class Vector {

    private static final Vector nullVector = new Vector(0, 0);
    private float x, y;

    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector(float x, float y, float x2, float y2) {
        this.x = x2 - x;
        this.y = y2 - y;
    }

    public Vector add(float x, float y) {
        this.x += x;
        this.y += y;
        return this;
    }

    public void add(Vector v) {
        x += v.getX();
        y += v.getY();
    }

    public void subtract(float x, float y) {
        this.x -= x;
        this.y -= y;
    }

    public void subtract(Vector v) {
        x -= v.getX();
        y -= v.getY();
    }

    public Vector multiply(float multi) {
        x *= multi;
        y *= multi;
        return this;
    }

    public float crossProduct(Vector v) {
        return x * v.getY() - y * v.getX();
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float dotProduct(Vector v) {
        return v.getX() * x + v.getY() * y;
    }

    public float angle(Vector v) {
        float sin = (float) Math.asin(crossProduct(v)/(length() * v.length()));
        if(dotProduct(v) < 0) {
            sin *= -1;
            sin += Math.PI;
        }
        return sin;
    }

    public Vector rotate(float theta) {
        var sinTheta = Math.sin(theta);
        var cosTheta = Math.cos(theta);
        float[] rot = Player.rotate(x, y, theta);
        x = rot[0];
        y = rot[1];
        return this;
    }

    public void rotateByDegrees(float theta) {
        var rotation = theta *  Math.PI / 180;
        rotate((float) rotation);
    }

    public float getY() {
        return y;
    }

    public float length() {
        return (float) Math.sqrt(lengthSquared());
    }

    public float lengthSquared() {
        return (float) (Math.pow(x, 2) + Math.pow(y, 2));
    }

    public Vector normalize() {
        float length = length();
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
        return Float.compare(vector.x, x) == 0 && Float.compare(vector.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
