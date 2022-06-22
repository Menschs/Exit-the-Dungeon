package entities;

import util.Vector;

import java.awt.*;
import java.util.List;

public class RoundHitbox implements Collider{

    private double x, y;
    private final double size;
    private Entity parentEntity;
    private Object parentObject;
    private HitboxAction onHit;

    public RoundHitbox(double x, double y, double size, Entity parent, HitboxAction onHit) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.onHit = onHit;
        this.parentEntity = parent;
        createCollider();
    }

    public RoundHitbox(double x, double y, double size, Object parent, HitboxAction onHit) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.onHit = onHit;
        this.parentObject = parent;
        createCollider();
    }

    @Override
    public List<Point> getPoints() {
        return null;
    }

    @Override
    public void paint(Graphics2D g) {
        g.setColor(Color.red);
        g.drawOval((int) x, (int) y, (int) size, (int) size);
    }

    @Override
    public boolean isColliding(Collider c) {
        if(c == this || (c.getObject() instanceof Object && this.getObject() instanceof Object)) return false;
        double[] c1 = getCenter();
        if(c instanceof RoundHitbox hb) {
            double[] c2 = hb.getCenter();
            Vector v = new Vector(c1[0], c1[1], c2[0], c2[1]);
            System.out.println(v.lengthSquared() + "  " + (Math.pow(getRadius(), 2) + Math.pow(hb.getRadius(), 2)));
            return (v.lengthSquared() <= Math.pow(getRadius(), 2) + Math.pow(hb.getRadius(), 2));
        } else {
            return new Vector(x, y, this.x, this.y).lengthSquared() <= Math.pow(getRadius(), 2);
        }
    }

    @Override
    public HitboxAction getHitboxAction() {
        return onHit;
    }

    public double[] getCenter() {
        return new double[] {x + size/2, y + size/2};
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
        return parentEntity;
    }

    @Override
    public Object getObject() {
        return parentObject;
    }
}
