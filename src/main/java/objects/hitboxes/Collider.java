package objects.hitboxes;

import objects.interfaces.Updating;
import objects.entities.interfaces.Entity;
import objects.elements.interfaces.Element;
import util.Interval;
import util.Point;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public interface Collider extends Updating {

    List<Collider> collider = new ArrayList<>();
    List<Collider> remove = new ArrayList<>();

    void paint(Graphics2D g);
    void paintThis(Rectangle r);
    Interval[] getRect();
    double getWidth();
    double getHeight();

    default Collider wouldCollide(Point point) {

        Point x1y1 = point;
        Point x2y1 = new Point(point.getX() + getWidth(), point.getY());
        Point x1y2 = new Point(point.getX(), point.getY() + getHeight());
        Point x2y2 = new Point(point.getX() + getWidth(), point.getY() + getHeight());

        for (Collider c : collider) {
            if (c == this) continue;
            if(c.intersects(x1y1) || c.intersects(x2y1) || c.intersects(x1y2) ||c.intersects(x2y2)) {
                //System.out.println("intersecting");
                return c;
            }
        }
        return null;
    }

    default void collide() {
        for (Collider c : collider) {
            if (c == this) continue;
            if(this.intersects(c)) {
                collide(c);
            }
        }
    }

    default void collide(Collider opponent) {
        if(opponent.getHitboxAction() != null) opponent.getHitboxAction().hit(this);
        if(getHitboxAction() != null) this.getHitboxAction().hit(opponent);
    }

    @Override
    default void tick(double deltaTime) {
        //collider.forEach(collider1 -> {
        //    collider.forEach(collider2 -> {
        //        if (collider1 != collider2) {
        //            if(collider1.isColliding(collider2)) {
        //                HitboxAction ha = collider1.getHitboxAction();
        //                if(ha != null) ha.hit(collider2);
        //            }
        //        }
        //    });
//
        //});
        collider.removeAll(remove);
        remove.clear();
    }

    default void createCollider() {
        collider.add(this);
        create();
    }

    default void remove() {
        remove.add(this);
    }

    Entity getEntity();
    Element getObject();

    HitboxAction getHitboxAction();

    boolean intersects(Point p);
    boolean intersects(Collider c);
}
