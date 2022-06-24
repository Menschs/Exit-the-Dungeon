package objects.entities;

import main.ExitTheDungeon;
import objects.Point;
import objects.hitboxes.Collider;
import objects.hitboxes.Hitbox;
import util.Colors;
import util.Vector;

import java.awt.*;

public class Dummy implements Entity {

    private double x, y;

    private static final int SIZE = 20;

    private double health = 1000;
    private Color c = Colors.gold.getColor();
    private final Hitbox hitbox;

    private int damagedFrames = 0;

    private Vector velocity = new Vector(0, 0);

    public Dummy(double x, double y) {
        this.x = x;
        this.y = y;
        this.hitbox = new Hitbox((int) x, (int) y, SIZE, SIZE, this, null);
    }

    @Override
    public void damage(double damage) {
        health -= damage;
        damagedFrames = 5;
        if(health <= 0) kill();
    }

    @Override
    public void heal(double heal) {
        health += heal;
    }

    @Override
    public void kill() {
        c = Color.red;
        ExitTheDungeon.getBoard().removeEntity(this);
        hitbox.remove();
        remove();
    }

    @Override
    public void move(double x, double y) {
        Collider connect = hitbox.wouldCollide(new Point(this.x + x - SIZE/2, this.y + y - SIZE/2));
        if(connect == null || connect.getObject() == null) {
            this.x += x;
            this.y += y;
        }
        hitbox.move((int) this.x,(int) this.y);
    }

    @Override
    public void rotate(double rotation) {
    }

    @Override
    public void rotate(Vector v) {

    }

    @Override
    public void addVelocity(Vector v) {
        velocity.add(v);
    }

    @Override
    public void setVelocity(Vector v) {
        velocity = v;
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

    @Override
    public Vector getVelocity() {
        return velocity;
    }

    @Override
    public Vector getDirection() {
        return new Vector(0, 0);
    }

    @Override
    public void paint(Graphics2D g) {
        g.setColor((damagedFrames > 0) ? new Color(255, c.getGreen()/2, c.getBlue()/2) : c);
        damagedFrames--;
        g.fillOval((int) x, (int) y, SIZE, SIZE);
        hitbox.paint(g);
    }
}
