package objects.entities;

import inventory.items.ItemStack;
import objects.entities.interfaces.Permeable;
import objects.entities.interfaces.StaticEntity;
import objects.hitboxes.Collider;
import objects.hitboxes.Hitbox;
import objects.hitboxes.HitboxAction;
import textures.Skin;

import java.awt.*;

public class DroppedItem implements StaticEntity, Permeable {

    private static final int SIZE = 20;

    private final int x, y;
    private final ItemStack item;
    private final Hitbox hitbox;

    private boolean pickedUp;

    private final DroppedItem tis = this;

    public DroppedItem(int x, int y, ItemStack item) {
        this.x = x - SIZE/2;
        this.y = y - SIZE/2;
        this.item = item;
        hitbox = new Hitbox(x, y, SIZE, SIZE, this, new HitboxAction() {
            @Override
            public void hit(Collider c) {
                if(pickedUp) return;
                if(c.getEntity() instanceof Player p) {
                    pickedUp = p.addDroppedItem(tis);
                    if(pickedUp) kill();
                }
            }
        });
        createEntity();
    }

    public ItemStack getItem() {
        return item;
    }

    @Override
    public void paint(Graphics2D g) {
        g.setColor(item.getRarity().getColor());
        g.fillPolygon(new int[]{x, x + SIZE / 2, x + SIZE, x + SIZE / 2}, new int[]{y + SIZE/2, y, y + SIZE/2, y + SIZE}, 4);
        //hitbox.paint(g);
    }

    @Override
    public Skin getSkin() {
        return null;
    }

    @Override
    public void damage(float damage) {

    }

    @Override
    public void heal(float heal) {

    }

    @Override
    public void kill() {
        removeEntity();
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
