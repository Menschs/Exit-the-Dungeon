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
    private final double width;
    private final double height;
    private Entity parentEntity;
    private Element parentObject;

    private final Rectangle rect;

    private HitboxAction onHit;

    public Hitbox(double x, double y, double width, double height, Entity parent, HitboxAction onHit) {
        this.width = width;
        this.height = height;
        this.onHit = onHit;
        this.parentEntity = parent;
        this.rect = new Rectangle((int) (x - width/2), (int) (y - height/2), (int) width, (int) height);

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
        rect.setLocation((int) x, (int) y);
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

    public double getWidth() {
        return width;
    }

    public double getHeight() {
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
