package entities;

import main.ExitTheDungeon;
import util.Vector;

import java.awt.*;

public interface Entity extends Updating, Damageable {

    void move(Vector v);
    void move(double x, double y);
    void rotate(double rotation);
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
        for (double i = 0; i < 10; i++) {
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
