package entities;

import main.ExitTheDungeon;
import util.Vector;

import java.awt.*;

public interface Entity extends Updating, Damageable {

    void move(Vector v);
    void move(float x, float y);
    void rotate(float rotation);
    void addVelocity(Vector v);
    void setVelocity(Vector v);
    float getX();
    float getY();

    @Override
    default void tick() {
        Vector v = getVelocity();
        float YperI = v.getY() / 10;
        float XperI = v.getX() / 10;
        for (float i = 0; i < 10; i++) {
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
