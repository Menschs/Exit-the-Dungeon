package objects.entities;

import inventory.ItemStack;
import inventory.Itemtype;
import inventory.Material;
import inventory.Rarity;
import objects.entities.interfaces.StaticEntity;
import objects.hitboxes.Hitbox;
import util.Colors;

import java.awt.*;

public class Crate implements StaticEntity {

    private static final int SIZE = 15;

    private final int x, y;
    private final Hitbox hitbox;

    private final ItemStack[] loot = {new ItemStack(Material.sword, Itemtype.weapon, Rarity.uncommon), new ItemStack(Material.longsword, Itemtype.weapon, Rarity.legendary)};

    private boolean dead;

    public Crate(int x, int y) {
        this.x = x - SIZE/2;
        this.y = y - SIZE/2;
        hitbox = new Hitbox(x, y, SIZE, SIZE, this, null);
        createEntity();
    }

    @Override
    public void damage(double damage) {
        kill();
    }

    @Override
    public void heal(double heal) {

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
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public Hitbox getHitbox() {
        return hitbox;
    }
}
