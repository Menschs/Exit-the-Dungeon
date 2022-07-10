package objects.hitboxes;

import objects.interfaces.Updating;
import objects.entities.interfaces.Entity;
import objects.elements.interfaces.Element;
import util.Debugger;
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
    float getWidth();
    float getHeight();

    default CollisionResult wouldCollide(Point point) {
        Point x0y1 = new Point(getX(), point.getY());
        Point x0y2 = new Point(getX(), point.getY() + getHeight());
        Point x1y0 = new Point(point.getX(), getY());
        Point x2y0 = new Point(point.getX() + getWidth(), getY());
        Point x00y1 = new Point(getX() + getWidth(), point.getY());
        Point x00y2 = new Point(getX() + getWidth(), point.getY() + getHeight());
        Point x1y00 = new Point(point.getX(), getY() + getHeight());
        Point x2y00 = new Point(point.getX() + getWidth(), getY() + getHeight());

        boolean collisionX = false;
        boolean collisionY = false;
        List<Collider> coll = new ArrayList<>();

        for (Collider c : collider) {
            if (c == this) continue;
            if(c.intersects(x0y1) || c.intersects(x0y2) || c.intersects(x00y1) || c.intersects(x00y2)) {
                collisionY = true;
                coll.add(c);
            }
            if(c.intersects(x1y0) || c.intersects(x2y0) || c.intersects(x1y00) || c.intersects(x2y00)) {
                collisionX = true;
                coll.add(c);
            }
        }
        return new CollisionResult(coll, collisionX, collisionY);
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
    default void tick(float deltaTime) {
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

    float getX();
    float getY();
}
