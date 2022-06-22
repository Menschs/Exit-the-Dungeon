package entities;

import main.ExitTheDungeon;
import util.Vector;

import java.awt.*;

public class Ball implements Entity{

    public static final int SIZE = 15;
    private static final double DAMAGE = 5;

    private double x = 0;
    private double y = 0;

    private Vector velocity;
    private boolean removed = false;

    private final RoundHitbox hitbox;
    private final Entity shooter;

    public Ball(double x, double y, Entity shooter, Vector velocity) {
        this.x = x;
        this.y = y;
        this.velocity = velocity;
        this.shooter = shooter;
        hitbox = new RoundHitbox(x, y, SIZE, this, new HitboxAction() {
            @Override
            public void hit(Collider c) {
                if(c.getEntity() != null && !(c.getEntity() instanceof Ball) && shooter != c.getEntity()) {
                    c.getEntity().damage(DAMAGE);
                    System.out.println("damaging");
                    kill();
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
        hitbox.move(this.x, this.y);
    }

    @Override
    public void rotate(double rotation) {

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
        return null;
    }

    @Override
    public Vector getVelocity() {
        return velocity;
    }

    @Override
    public Vector getDirection() {
        return null;
    }

    @Override
    public void paint(Graphics2D g) {
        g.setColor(Color.BLUE);
        g.fillOval((int) x, (int) y, SIZE, SIZE);
        hitbox.paint(g);
    }

    @Override
    public void damage(double damage) {

    }

    @Override
    public void heal(double heal) {

    }

    @Override
    public void kill() {
        System.out.println("killing...");
        removed = true;
        ExitTheDungeon.getBoard().removeEntity(this);
        hitbox.remove();
        remove();
    }
}
