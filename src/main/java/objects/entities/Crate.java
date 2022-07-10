package objects.entities;

import inventory.items.ItemStack;
import inventory.items.Itemtype;
import inventory.items.Material;
import inventory.items.Rarity;
import inventory.items.items.LongSword;
import inventory.items.items.Sword;
import objects.entities.interfaces.StaticEntity;
import objects.hitboxes.Hitbox;
import textures.Skin;
import util.Colors;

import java.awt.*;

public class Crate implements StaticEntity {

    private static final int SIZE = 15;

    private final int x, y;
    private final Hitbox hitbox;

    private final ItemStack[] loot = {new Sword(Rarity.uncommon), new LongSword(Rarity.legendary)};

    private boolean dead;

    public Crate(int x, int y) {
        this.x = x - SIZE/2;
        this.y = y - SIZE/2;
        hitbox = new Hitbox(x, y, SIZE, SIZE, this, null);
        createEntity();
    }

    @Override
    public void damage(float damage) {
        kill();
    }

    @Override
    public void heal(float heal) {

    }

    @Override
    public void kill() {
        if(dead) return;
        dead = true;
        for (ItemStack itemStack : loot) {
            dropLoot(itemStack);
        }
        removeEntity();
    }

    @Override
    public void paint(Graphics2D g) {
        g.setColor(Colors.light_red.getColor());
        g.fillRect(x, y, SIZE, SIZE);
    }

    @Override
    public Skin getSkin() {
        return null;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public Hitbox getHitbox() {
        return hitbox;
    }
}
