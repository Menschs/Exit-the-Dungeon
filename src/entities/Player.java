package entities;

import util.Vector;

import java.awt.*;

public class Player implements Entity, Damageable {

    private final static int[] modelX = new int[] {30, 0, 60};
    private final static int[] modelY = new int[] {0, 70, 70};

    private Color c = Color.BLACK;

    private float x = 0;
    private float y = 0;
    private float rotation = 0;

    private int max_health;
    private int health;

    private Vector velocity = new Vector(0, 0);

    public Player(float x, float y, float rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        create();
    }

    @Override
    public void move(Vector v) {
        x += v.getX();
        y += v.getY();
    }

    @Override
    public void move(float x, float y) {
        this.x += x;
        this.y += y;
    }

    @Override
    public void rotate(float rotation) {
        this.rotation += rotation * Math.PI/180;
    }

    @Override
    public void addVelocity(Vector v) {
        this.velocity.add(v);
    }

    @Override
    public void setVelocity(Vector v) {
        velocity = v;
    }

    @Override
    public Vector getVelocity() {
        return velocity;
    }

    @Override
    public Vector getDirection() {
        float[] rot = rotate(0, -1, rotation);
        return new Vector(rot[0], rot[1]);
    }

    @Override
    public void paint(Graphics2D g) {
        g.setColor(c);
        int[][] model = rotate(modelX, modelY, rotation);
        g.drawPolygon(model[0], model[1], 3);
    }

    public float[] rotate(float x, float y, float theta) {
        var sinTheta = Math.sin(theta);
        var cosTheta = Math.cos(theta);
        return rotate(x, y, sinTheta, cosTheta);
    }

    public float[] rotate(float x, float y, double sinTheta, double cosTheta) {
        float nx = (float) (x * cosTheta - y * sinTheta);
        float ny = (float) (y * cosTheta + x * sinTheta);
        return new float[] {nx, ny};
    }

    public int[][] rotate(int[] toRX, int[] toRY, float theta) {
        int[][] result = new int[2][toRX.length];
        var sinTheta = Math.sin(theta);
        var cosTheta = Math.cos(theta);
        for (int i = 0; i < toRX.length; i++) {
            var x = toRX[i] - 30;
            var y = toRY[i] - 35;
            float[] rot = rotate(x, y, sinTheta, cosTheta);
            result[0][i] = (int) (rot[0] + this.x);
            result[1][i] = (int) (rot[1] + this.y);
        }
        return result;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    public float getRotation() {
        return rotation;
    }

    @Override
    public void damage(float damage) {
        health -= damage;
        if(health <= 0) kill();
    }

    @Override
    public void heal(float heal) {
        health += heal;
        if(health > 0) c = Color.black;
    }

    @Override
    public void kill() {
        c = Color.red;
    }

    @Override
    public boolean isRemoved() {
        return false;
    }
}
