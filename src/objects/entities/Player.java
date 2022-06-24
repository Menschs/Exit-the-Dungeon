package objects.entities;

import main.ExitTheDungeon;
import objects.hitboxes.Collider;
import objects.Damageable;
import objects.hitboxes.Hitbox;
import objects.hitboxes.HitboxAction;
import objects.Point;
import util.Colors;
import util.Vector;

import java.awt.*;

public class Player implements Entity, Damageable {

    private final static int[] modelX = new int[] {30, 0, 60};
    private final static int[] modelY = new int[] {0, 60, 60};

    private Color c = Color.BLACK;

    private double x = 0;
    private double y = 0;
    private double rotation = 0;
    private double lastRot = -600;

    private int[][] model;

    private int max_health = 100;
    private int health = max_health;

    private final Hitbox hitbox;

    private Vector velocity = new Vector(0, 0);

    public Player(double x, double y, int rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        hitbox = new Hitbox(0, 0, 60, 60, this, new HitboxAction() {
            @Override
            public void hit(Collider c) {
                if(c.getObject() != null)
                    damage(1);
            }
        });
        rotate(0);
        create();
    }

    boolean moved = false;

    @Override
    public void move(double x, double y) {
        hitbox.move((int) this.x - 30,(int) this.y - 30);
        if(x == 0 && y == 0) return;
        Collider connect = hitbox.wouldCollide(new Point(this.x + x - 30, this.y + y - 30));
        if(connect == null || connect.getObject() == null) {
            this.x += x;
            this.y += y;
            moved = true;
        } else {
            if(moved) {
                hitbox.collide(connect);
                moved = false;
            }
        }
    }

    @Override
    public void rotate(double rotation) {
        this.rotation += rotation * Math.PI/180;
        model = rotate(modelX, modelY);
    }

    double rotChange = 0;

    @Override
    public void rotate(Vector v) {
        if(v.equals(getDirection())) return;

        Vector v2 = new Vector(0, -1);
        double lol = v2.angle(v);

        //double delta = rotation + rotation + lol * Math.PI;
        //model = rotate(modelX, modelY, delta);
        //System.out.println(v2.angle(v) + "  " + v2.angle(getDirection()) + "  " + (rotation - lol));
        //if(x + v.getX() > x)
        //    if(v2.angle(v) < v2.angle(getDirection())) {
        //        rotation = -rotation;
        //        rotate(-lol);
        //    } else {
        //        rotate(lol);
        //    }
        //else
        //    if(v2.angle(v) < v2.angle(getDirection())) {
        //        rotate(lol);
        //    } else {
        //        rotation = -rotation;
        //        rotate(-lol);
        //    }
        rotation = lol;
        rotate(0);
        rotChange = lol;
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
        //if(model == null || lastRot != rotation) rotate(0);
        g.setColor(c);
        int[][] tempModel = new int[2][3];
        for (int i = 0; i < model[0].length; i++) {
            tempModel[0][i] = (int) (model[0][i] + x);
            tempModel[1][i] = (int) (model[1][i] + y);
        }
        g.drawPolygon(tempModel[0], tempModel[1], 3);
        g.setColor(Colors.dark_red.getColor());
        int x = ExitTheDungeon.getFrame().getWidth()/2 - 125;
        int y = ExitTheDungeon.getFrame().getHeight() - 70;
        g.fillRoundRect(x, y, 250, 10, 5, 5);
        g.setColor(Color.red);
        double percentage = (health+0.0)/(max_health+0.0);
        g.fillRoundRect(x, y, (int) (250 * percentage), 10, 5, 5);
        hitbox.paint(g);
    }

    public double[] rotate(double x, double y, double theta) {
        var sinTheta = Math.sin(theta);
        var cosTheta = Math.cos(theta);
        return rotate(x, y, sinTheta, cosTheta);
    }

    public double[] rotate(double x, double y, double sinTheta, double cosTheta) {
        double nx = (x * cosTheta - y * sinTheta);
        double ny = (y * cosTheta + x * sinTheta);
        return new double[] {nx, ny};
    }

    public int[][] rotate(int[] toRX, int[] toRY) {
        int[][] result = new int[2][toRX.length];
        var sinTheta = Math.sin(rotation);
        var cosTheta = Math.cos(rotation);
        for (int j = 0; j < toRX.length; j++) {
            var x = toRX[j] - 30;
            var y = toRY[j] - 30;
            double[] rot = rotate(x, y, sinTheta, cosTheta);
            result[0][j] = (int) (rot[0]);
            result[1][j] = (int) (rot[1]);
        }
        return result;
    }

    public int[][] rotate(int[] toRX, int[] toRY, double theta) {
        int[][] result = new int[2][toRX.length];
        var sinTheta = Math.sin(theta);
        var cosTheta = Math.cos(theta);
        for (int j = 0; j < toRX.length; j++) {
            var x = toRX[j] - 30;
            var y = toRY[j] - 30;
            double[] rot = rotate(x, y, sinTheta, cosTheta);
            result[0][j] = (int) (rot[0]);
            result[1][j] = (int) (rot[1]);
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
