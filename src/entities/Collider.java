package entities;

import util.Interval;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public interface Collider extends Updating {

    List<Collider> collider = new ArrayList<>();
    List<Collider> remove = new ArrayList<>();

    List<Point> getPoints();

    void paint(Graphics2D g);

    default boolean isColliding(Collider c) {
        if(c instanceof RoundHitbox) {
            RoundHitbox hb = (RoundHitbox) c;
            return hb.isColliding(this);
        } else {
            List<Point> p1 = getPoints();
            List<Point> p2 = c.getPoints();
            if(p1 == null || p2 == null) return false;

            for (Point point : getPoints()) {
                for (Point point1 : p2) {
                    if(point1.is(point.getX(), point1.getY())) return true;
                }
            }
        }
        return false;
    }

    @Override
    default void tick() {
        collider.forEach(collider1 -> {
            collider.forEach(collider2 -> {
                if (collider1 != collider2) {
                    if(collider1.isColliding(collider2)) {
                        HitboxAction ha = collider1.getHitboxAction();
                        if(ha != null) ha.hit(collider2);
                    }
                }
            });

        });
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

    HitboxAction getHitboxAction();
}
