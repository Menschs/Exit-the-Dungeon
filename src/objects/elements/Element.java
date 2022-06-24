package objects.elements;

import main.ExitTheDungeon;

import java.awt.*;

public interface Element {

    int getX();
    int getY();

    void paint(Graphics2D g);

    default void remove() {
        ExitTheDungeon.getBoard().removeElement(this);
    }

    default boolean isBarrier() {
        return true;
    }
}
