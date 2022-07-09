package objects.hitboxes;

import objects.elements.Wall;
import util.Debugger;
import util.Interval;
import util.Point;
import objects.entities.interfaces.Entity;
import objects.elements.interfaces.Element;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Hitbox implements Collider {

    private final double width;
    private final double height;
    private Entity parentEntity;
    private Element parentObject;

    private final Interval xCorners, yCorners;

    private HitboxAction onHit;

    public Hitbox(double x, double y, double width, double height, Entity parent, HitboxAction onHit) {
        this.width = width;
        this.height = height;
        this.onHit = onHit;
        this.parentEntity = parent;
        this.xCorners = new Interval(x, x + width);
        this.yCorners = new Interval(y, y + height);

        //for (int i = 0; i < width; i++) {
        //    for (int j = 0; j < height; j++) {
        //        points.add(new Point(x + i, y + j));
        //        if(i != 0 && i != (int) width -1) {
        //            j += height -2;
        //        }
        //    }
        //}
        createCollider();
    }

    public Hitbox(double x, double y, double width, double height, Element parent, HitboxAction onHit) {
        if(parent instanceof Wall w) {
            x -= w.getSkin().getScaleX();
            y -= w.getSkin().getScaleY();
            width += 2 * w.getSkin().getScaleX();
            height += 2 * w.getSkin().getScaleY();
        }
        this.width = width;
        this.height = height;
        this.onHit = onHit;
        this.parentObject = parent;
        this.xCorners = new Interval(x, x + width);
        this.yCorners = new Interval(y, y + height);
        //for (int i = 0; i < width; i++) {
        //    for (int j = 0; j < height; j++) {
        //        points.add(new Point(x + i, y + j));
        //        if(i != 0 && i != (int) width -1) {
        //            j += height -2;
        //        }
        //    }
        //}
        createCollider();
    }

    public void move(double x, double y) {
        //int index = 0;
        //for (int i = 0; i < width; i++) {
        //    for (int j = 0; j < height; j++) {
        //        points.getTextureObject(index).move(x + i, y + j);
        //        index++;
        //        if(i != 0 && i != (int) width -1) {
        //            j += height -2;
        //        }
        //    }
        //}
        //rotate(pivotX, pivotY, rotation);
        xCorners.setS(x);
        xCorners.setE(x + width);

        yCorners.setS(y);
        yCorners.setE(y + height);
        collide();
    }

    Rectangle r;

    @Override
    public void paint(Graphics2D g) {
        //points.forEach(point -> point.paint(g));
        g.setColor(Color.red);
        g.setColor(Color.CYAN);
        if(r != null) g.fill(r);
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public void paintThis(Rectangle r) {
        this.r = r;
    }

    @Override
    public Interval[] getRect() {
        return new Interval[]{xCorners, yCorners};
    }

    @Override
    public Entity getEntity() {
        return parentEntity;
    }

    @Override
    public Element getObject() {
        return parentObject;
    }

    @Override
    public HitboxAction getHitboxAction() {
        return onHit;
    }

    public void setOnHit(HitboxAction onHit) {
        this.onHit = onHit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hitbox hitbox = (Hitbox) o;
        return Objects.equals(parentEntity, hitbox.parentEntity) && Objects.equals(parentObject, hitbox.parentObject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parentEntity, parentObject);
    }

    public boolean intersects(Point p) {
        Debugger.debug(xCorners, yCorners, p, (xCorners.contains(p.getX()) && yCorners.contains(p.getY())));
        return (xCorners.contains(p.getX()) && yCorners.contains(p.getY()));
    }

    @Override
    public boolean intersects(Collider c) {
        var oXCorners = c.getRect()[0];
        var oYCorners = c.getRect()[1];

        //Debugger.debug(xCorners, oXCorners, (xCorners.contains(oXCorners) || oXCorners.contains(xCorners)), yCorners, oYCorners, (yCorners.contains(oYCorners) || oYCorners.contains(yCorners)), ((xCorners.contains(oXCorners) || oXCorners.contains(xCorners)) && (yCorners.contains(oYCorners) || oYCorners.contains(yCorners))));

        return ((xCorners.contains(oXCorners) || oXCorners.contains(xCorners)) && (yCorners.contains(oYCorners) || oYCorners.contains(yCorners)));
    }
}
