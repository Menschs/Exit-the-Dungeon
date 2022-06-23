package entities;

import util.Vector;

import java.awt.*;

public class Player implements Entity, Damageable {

    private final static int[] modelX = new int[] {30, 0, 60};
    private final static int[] modelY = new int[] {0, 70, 70};

    private Color c = Color.BLACK;

    private double x = 0;
    private double y = 0;
    private double rotation = 0;

    private int max_health;
    private int health;

    private final Hitbox hitbox;

    private Vector velocity = new Vector(0, 0);

    public Player(double x, double y, double rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        hitbox = new Hitbox(x, y, 60, 70, this, new HitboxAction() {
            @Override
            public void hit(Collider c) {
                if(c.getObject() != null) {
                    velocity = new Vector(0, 0);
                }
            }
        });
        create();
    }

    @Override
    public void move(Vector v) {
        move(v.getX(), v.getY());
    }

    @Override
    public void move(double x, double y) {
        this.x += x;
        this.y += y;
        hitbox.move((int) this.x - 30,(int) this.y - 35);
        hitbox.rotate((int) this.x, (int) this.y, rotation);
    }

    @Override
    public void rotate(double rotation) {
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
        double[] rot = rotate(0, -1, rotation);
        return new Vector(rot[0], rot[1]);
    }

    @Override
    public void paint(Graphics2D g) {
        g.setColor(c);
        int[][] model = rotate(modelX, modelY, rotation);
        g.drawPolygon(model[0], model[1], 3);
        hitbox.paint(g);
    }

    public double[] rotate(double x, double y, double theta) {
        var sinTheta = Math.sin(theta);
        var cosTheta = Math.cos(theta);
        return rotate(x, y, sinTheta, cosTheta);
    }

    public double[] rotate(double x, double y, double sinTheta, double cosTheta) {
        double nx = (double) (x * cosTheta - y * sinTheta);
        double ny = (double) (y * cosTheta + x * sinTheta);
        return new double[] {nx, ny};
    }

    public int[][] rotate(int[] toRX, int[] toRY, double theta) {
        int[][] result = new int[2][toRX.length];
        var sinTheta = Math.sin(theta);
        var cosTheta = Math.cos(theta);
        for (int i = 0; i < toRX.length; i++) {
            var x = toRX[i] - 30;
            var y = toRY[i] - 35;
            double[] rot = rotate(x, y, sinTheta, cosTheta);
            result[0][i] = (int) (rot[0] + this.x);
            result[1][i] = (int) (rot[1] + this.y);
        }
        return result;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public Hitbox getHitbox() {
        return hitbox;
    }

    public double getRotation() {
        return rotation;
    }

    @Override
    public void damage(double damage) {
        health -= damage;
        if(health <= 0) kill();
    }

    @Override
    public void heal(double heal) {
        health += heal;
        if(health > 0) c = Color.black;
    }

    @Override
    public void kill() {
        c = Color.red;
    }
}
