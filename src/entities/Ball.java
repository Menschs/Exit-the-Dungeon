package entities;

import main.ExitTheDungeon;
import util.Vector;

import java.awt.*;

public class Ball implements Entity{

    private float x = 0;
    private float y = 0;

    private Vector velocity;
    private boolean removed = false;

    public Ball(float x, float y, Vector velocity) {
        this.x = x;
        this.y = y;
        this.velocity = velocity;
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
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
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
        g.fillOval((int) x, (int) y, 15, 15);
    }

    @Override
    public void damage(float damage) {

    }

    @Override
    public void heal(float heal) {

    }

    @Override
    public void kill() {
        System.out.println("killing...");
        removed = true;
        ExitTheDungeon.getBoard().removeEntity(this);
    }

    @Override
    public boolean isRemoved() {
        return removed;
    }
}
