package objects.hitboxes;

import objects.elements.Wall;
import textures.Skin;
import textures.TextureType;
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

    private final float width;
    private final float height;
    private HitboxHolder parent;

    private final Interval xCorners, yCorners;

    private HitboxAction onHit;

    public Hitbox(float x, float y, float width, float height, HitboxHolder parent, HitboxAction onHit) {
        this.width = width;
        this.height = height;
        this.onHit = onHit;
        this.parent = parent;
        this.xCorners = new Interval(x, x + width);
        this.yCorners = new Interval(y, y + height);
        createCollider();
    }

    public void move(float x, float y) {
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
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight() {
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
        return Objects.equals(parent, hitbox.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parent);
    }

    public boolean intersects(Point p) {
        //Debugger.debug(xCorners, yCorners, p, (xCorners.contains(p.getX()) && yCorners.contains(p.getY())));
        return (xCorners.contains(p.getX()) && yCorners.contains(p.getY()));
    }

    @Override
    public boolean intersects(Collider c) {
        var oXCorners = c.getRect()[0];
        var oYCorners = c.getRect()[1];

        //Debugger.debug(xCorners, oXCorners, (xCorners.contains(oXCorners) || oXCorners.contains(xCorners)), yCorners, oYCorners, (yCorners.contains(oYCorners) || oYCorners.contains(yCorners)), ((xCorners.contains(oXCorners) || oXCorners.contains(xCorners)) && (yCorners.contains(oYCorners) || oYCorners.contains(yCorners))));

        return ((xCorners.contains(oXCorners) || oXCorners.contains(xCorners)) && (yCorners.contains(oYCorners) || oYCorners.contains(yCorners)));
    }

    @Override
    public float getX() {
        return xCorners.getS();
    }

    @Override
    public float getY() {
        return yCorners.getS();
    }

    @Override
    public void remove() {
        Collider.super.remove();
    }

    @Override
    public HitboxHolder getHolder() {
        return parent;
    }
}
