package objects.entities;

import util.Vector;

public interface StaticEntity extends Entity {

    @Override
    default void move(Vector v) {}

    @Override
    default void move(double x, double y) {}

    @Override
    default void rotate(double rotation) {}

    @Override
    default void rotate(Vector v) {}

    @Override
    default void addVelocity(Vector v) {}

    @Override
    default void setVelocity(Vector v) {}

    @Override
    default Vector getVelocity() {
        return Vector.getNullVector();
    }

    @Override
    default Vector getDirection() {
        return Vector.getNullVector();
    }
}
