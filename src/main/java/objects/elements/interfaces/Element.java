package objects.elements.interfaces;

import main.ExitTheDungeon;
import objects.hitboxes.HitboxHolder;
import textures.Skin;

import java.awt.*;

public interface Element extends HitboxHolder {

    Skin getSkin();

    float getX();
    float getY();

    void paint(Graphics2D g);

    default void remove() {
        ExitTheDungeon.getBoard().removeElement(this);
    }

    default boolean isBarrier() {
        return true;
    }
}
