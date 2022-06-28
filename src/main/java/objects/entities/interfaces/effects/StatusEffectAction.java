package objects.entities.interfaces.effects;

import objects.entities.interfaces.Entity;

public interface StatusEffectAction {

    void effect(Entity e, int curTicks);
}
