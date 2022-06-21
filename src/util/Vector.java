package util;

public class Vector {

    private float x, y;

    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void add(float x, float y) {
        this.x += x;
        this.y += y;
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

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Vector{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
