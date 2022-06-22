package entities;

import util.Vector;

import java.awt.*;
import java.util.List;

public class RoundHitbox implements Collider{

    private double x, y;
    private final double size;
    private final Entity parent;
    private HitboxAction onHit;

    public RoundHitbox(double x, double y, double size, Entity parent, HitboxAction onHit) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.onHit = onHit;
        this.parent = parent;
        createCollider();
    }

    @Override
    public List<Point> getPoints() {
        return null;
    }

    @Override
    public void paint(Graphics2D g) {
        g.setColor(Color.red);
        g.drawOval((int) (x), (int) (y), (int) size, (int) size);
    }

    @Override
    public boolean isColliding(Collider c) {
        if(c instanceof RoundHitbox) {
            RoundHitbox hb = (RoundHitbox) c;
            Vector v = new Vector(getX(), getY(), hb.getX(), hb.getY());
            return (v.lengthSquared() <= Math.pow(size, 2) + Math.pow(size, 2));
        } else {
            return new Vector(x, y, this.x, this.y).lengthSquared() <= Math.pow(size/2, 2);
        }
    }

    @Override
    public HitboxAction getHitboxAction() {
        return onHit;
    }

    public double[] getCenter() {
        return new double[] {x, y};
    }

    public void setOnHit(HitboxAction onHit) {
        this.onHit = onHit;
    }

    public double getRadius() {
        return size/2;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void move(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Entity getEntity() {
        return parent;
    }
}
