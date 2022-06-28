package objects.entities.statuseffects;

import objects.entities.interfaces.Entity;
import objects.entities.interfaces.effects.StatusEffect;
import objects.entities.interfaces.effects.StatusEffectAction;
import objects.entities.interfaces.effects.StatusEffects;

public class HealingEffect implements StatusEffect {

    private int level = 1;
    private int time = 60;

    @Override
    public StatusEffects getEffectIdentifier() {
        return StatusEffects.healing;
    }

    @Override
    public StatusEffectAction getAction() {
        return new StatusEffectAction() {
            @Override
            public void effect(Entity e, int curTicks) {
                if(curTicks == 0) e.heal(level);
            }
        };
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int getTime() {
        return time;
    }

    @Override
    public void setTime(int time) {
        this.time = time;
    }
}
