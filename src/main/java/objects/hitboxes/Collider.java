package objects.hitboxes;

import objects.interfaces.Updating;
import objects.entities.interfaces.Entity;
import objects.elements.interfaces.Element;
import util.Point;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public interface Collider extends Updating {

    List<Collider> collider = new ArrayList<>();
    List<Collider> remove = new ArrayList<>();

    List<Point> getPoints();

    void paint(Graphics2D g);
    void paintThis(Rectangle r);
    Rectangle getRect();

    default Collider wouldCollide(Point point) {

        Rectangle newRect = (Rectangle) getRect().clone();
        newRect.setLocation((int)point.getX(), (int)point.getY());
        for (Collider c : collider) {
            if (c == this) continue;
            if(newRect.intersects(c.getRect())) return c;
        }
        return null;
    }

    default void collide() {
        for (Collider c : collider) {
            if (c == this) continue;
            if(this.getRect().intersects(c.getRect())) {
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
}
