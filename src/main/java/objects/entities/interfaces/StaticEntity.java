package objects.entities.interfaces;

import util.Vector;

public interface StaticEntity extends Entity {

    @Override
    default void move(Vector v) {}

    @Override
    default void move(float x, float y) {}

    @Override
    default void rotate(float rotation) {}

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

    @Override
    default void setStaticVelocity(Vector v) {}

    @Override
    default void setRangedVelocity(Vector v, float range) {}

    @Override
    default void removeRange(float distanceTraveled_squared) {}

    @Override
    default float getRange() {
        return 0f;
    }
}
