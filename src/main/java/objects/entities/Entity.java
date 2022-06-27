package objects.entities;

import inventory.ItemStack;
import main.ExitTheDungeon;
import objects.Damageable;
import objects.hitboxes.Hitbox;
import objects.Updating;
import util.Vector;

import java.awt.*;
import java.util.Random;

public interface Entity extends Updating, Damageable {

    Random r = new Random();

    void paint(Graphics2D g);

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
    }

    default void removeEntity() {
        if(getHitbox() != null) getHitbox().remove();
        ExitTheDungeon.getBoard().removeEntity(this);
        remove();
    }

    default void createEntity() {
        create();
        ExitTheDungeon.getBoard().addEntity(this);
    }

    default void dropLoot(ItemStack item) {
        item.drop((int) (getX() + r.nextInt(getHitbox().getWidth())), (int) (getY() + r.nextInt(getHitbox().getHeight())));
    }

    Vector getVelocity();
    Vector getDirection();
}
