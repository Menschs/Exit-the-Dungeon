package entities;

import util.Interval;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Hitbox implements Collider {

    private final List<Point> points = new ArrayList<>();
    private double width;
    private double height;
    private Entity parentEntity;
    private Object parentObject;

    private HitboxAction onHit;

    public Hitbox(double x, double y, double width, double height, Entity parent, HitboxAction onHit) {
        this.width = width;
        this.height = height;
        this.onHit = onHit;
        this.parentEntity = parent;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                points.add(new Point((int) (x + i), (int) (y + j)));
                if(i != 0 && i != (int) width -1) {
                    j += height -2;
                }
            }
        }
        createCollider();
    }

    public Hitbox(double x, double y, double width, double height, Object parent, HitboxAction onHit) {
        this.width = width;
        this.height = height;
        this.onHit = onHit;
        this.parentObject = parent;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                points.add(new Point((int) (x + i), (int) (y + j)));
                if(i != 0 && i != (int) width -1) {
                    j += height -2;
                }
            }
        }
        createCollider();
    }

    @Override
    public List<Point> getPoints() {
        return points;
    }

    public void rotate(int pivotX, int pivotY, double theta) {
        points.forEach(point -> point.rotate(pivotX, pivotY, theta));
    }

    public void move(int x, int y) {
        int index = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                points.get(index).move(x + i, y + j);
                index++;
                if(i != 0 && i != (int) width -1) {
                    j += height -2;
                }
            }
        }
    }

    @Override
    public void paint(Graphics2D g) {
        points.forEach(point -> point.paint(g));
    }

    @Override
    public Entity getEntity() {
        return parentEntity;
    }

    @Override
    public Object getObject() {
        return parentObject;
    }

    @Override
    public HitboxAction getHitboxAction() {
        return onHit;
    }

    public void setOnHit(HitboxAction onHit) {
        this.onHit = onHit;
    }
}
