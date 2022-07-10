package objects.entities.interfaces;

import inventory.items.ItemStack;
import main.ExitTheDungeon;
import objects.entities.interfaces.effects.StatusEffect;
import objects.entities.interfaces.effects.StatusEffects;
import objects.interfaces.Damageable;
import objects.hitboxes.Hitbox;
import objects.interfaces.Updating;
import textures.Skin;
import util.Vector;

import java.awt.*;
import java.util.HashMap;
import java.util.Random;

public interface Entity extends Updating, Damageable {

    Random r = new Random();

    void paint(Graphics2D g);

    default void move(Vector v) {
        move(v.getX(), v.getY());
    }
    default HashMap<StatusEffects, StatusEffect> getEffects() {
        return new HashMap<>();
    }
    default void addEffect(StatusEffect effect) {
        if(!getEffects().containsKey(effect.getEffectIdentifier())) getEffects().put(effect.getEffectIdentifier(), effect);
    }
    default void removeEffect(StatusEffects id) {
        getEffects().remove(id);
    }
    void move(float x, float y);
    void rotate(float rotation);
    void rotate(Vector v);
    void addVelocity(Vector v);
    void setVelocity(Vector v);
    Skin getSkin();
    float getX();
    float getY();
    Hitbox getHitbox();

    @Override
    default void tick(float deltaTime) {
        Vector v = getVelocity();
        float YperI = v.getY() * 15 * deltaTime;
        float XperI = v.getX() * 15 * deltaTime;
        move(XperI, YperI);
        setVelocity(getVelocity().multiply(0.9f));
        //if(!getVelocity().equals(Vector.getNullVector()))
        if(getVelocity().lengthSquared() < 0.0005) setVelocity(Vector.getNullVector());
        getEffects().values().forEach(statusEffects -> statusEffects.effect(this, 2));
    }

    default void removeEntity() {
        if(getHitbox() != null) {
            getHitbox().remove();
        }
        if(getSkin() != null) getSkin().remove();
        ExitTheDungeon.getBoard().removeEntity(this);
        remove();
    }

    default void createEntity() {
        create();
        ExitTheDungeon.getBoard().addEntity(this);
    }

    default void dropLoot(ItemStack item) {
        item.drop((int) (getX() + r.nextFloat(getHitbox().getWidth())), (int) (getY() + r.nextFloat(getHitbox().getHeight())));
    }

    default void setStaticVelocity(Vector v) {
        if(getVelocity().lengthSquared() < v.lengthSquared()) setVelocity(v);
    }

    Vector getVelocity();
    Vector getDirection();
}
