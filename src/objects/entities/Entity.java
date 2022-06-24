package objects.entities;

import main.ExitTheDungeon;
import objects.Damageable;
import objects.hitboxes.Hitbox;
import objects.Updating;
import util.Vector;

import java.awt.*;

public interface Entity extends Updating, Damageable {

    default void move(Vector v) {
        move(v.getX(), v.getY());
    }
    void move(double x, double y);
    void rotate(double rotation);
    void rotate(Vector v);
    void addVelocity(Vector v);
    void setVelocity(Vector v);
    double getX();
    double getY();
    Hitbox getHitbox();

    @Override
    default void tick() {
        Vector v = getVelocity();
        double YperI = v.getY() / 10;
        double XperI = v.getX() / 10;
        for (int i = 1; i < 11; i++) {
            move(XperI, YperI);
        }
        if(!(this instanceof Player) && (getX() <= -50 || getY() <= -50 || getX() >= ExitTheDungeon.frame.getWidth() + 50 || getY() >= ExitTheDungeon.frame.getHeight() + 50)) {
            kill();
        }
    }

    Vector getVelocity();
    Vector getDirection();

    void paint(Graphics2D g);
}
