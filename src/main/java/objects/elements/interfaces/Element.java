package objects.elements.interfaces;

import main.ExitTheDungeon;
import textures.Skin;

import java.awt.*;

public interface Element {

    Skin getSkin();

    double getX();
    double getY();

    void paint(Graphics2D g);

    default void remove() {
        ExitTheDungeon.getBoard().removeElement(this);
    }

    default boolean isBarrier() {
        return true;
    }
}
