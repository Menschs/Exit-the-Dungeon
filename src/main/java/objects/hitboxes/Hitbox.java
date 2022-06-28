package objects.hitboxes;

import util.Point;
import objects.entities.interfaces.Entity;
import objects.elements.interfaces.Element;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Hitbox implements Collider {

    private final List<Point> points = new ArrayList<>();
    private final int width;
    private final int height;
    private Entity parentEntity;
    private Element parentObject;

    private final Rectangle rect;

    private HitboxAction onHit;

    public Hitbox(int x, int y, int width, int height, Entity parent, HitboxAction onHit) {
        this.width = width;
        this.height = height;
        this.onHit = onHit;
        this.parentEntity = parent;
        this.rect = new Rectangle(x - width/2, y - height/2, width, height);
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

    public Hitbox(int x, int y, int width, int height, Element parent, HitboxAction onHit) {
        this.width = width;
        this.height = height;
        this.onHit = onHit;
        this.parentObject = parent;
        this.rect = new Rectangle(x, y, width, height);
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

    @Override
    public List<Point> getPoints() {
        return points;
    }

    public void move(int x, int y) {
        //int index = 0;
        //for (int i = 0; i < width; i++) {
        //    for (int j = 0; j < height; j++) {
        //        points.get(index).move(x + i, y + j);
        //        index++;
        //        if(i != 0 && i != (int) width -1) {
        //            j += height -2;
        //        }
        //    }
        //}
        //rotate(pivotX, pivotY, rotation);
        rect.setLocation(x, y);
        collide();
    }

    Rectangle r;

    @Override
    public void paint(Graphics2D g) {
        //points.forEach(point -> point.paint(g));
        g.setColor(Color.red);
        g.draw(rect);
        g.setColor(Color.CYAN);
        if(r != null) g.fill(r);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public void paintThis(Rectangle r) {
        this.r = r;
    }

    @Override
    public Rectangle getRect() {
        return rect;
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
}
