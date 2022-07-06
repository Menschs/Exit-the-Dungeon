package objects.entities.interfaces.effects;

import objects.entities.interfaces.Entity;
import util.RomanNumber;
import util.frame.gui.Text;
import util.frame.gui.TextCenter;

import java.awt.*;

public interface StatusEffect {

    StatusEffects getEffectIdentifier();
    StatusEffectAction getAction();
    int getLevel();
    void setLevel(int level);
    int getTime();
    void setTime(int time);

    default void effect(Entity e, int curTicks) {
        if(curTicks == 0) setTime(getTime()-1);
        if(getTime() < 0) {
            e.removeEffect(getEffectIdentifier());
            return;
        }
        getAction().effect(e, curTicks);
    }

    default void paint(Graphics2D g, int x, int y) {
        String minus = (getLevel() < 0) ? "  - " : "  ";
        Text t = new Text(getEffectIdentifier().getColor().getIdentifier() + getEffectIdentifier().getName() + minus + RomanNumber.toRoman(getLevel()) + "  " + getTime() + "s", x, y);
        t.setCenter(TextCenter.left);
        t.paint(g);
    }
}
