package objects.entities;

import inventory.inventory.Inventory;
import inventory.inventory.InventoryView;
import inventory.inventory.Inventoryholder;
import inventory.items.ItemStack;
import inventory.items.Itemtype;
import inventory.items.Material;
import inventory.items.Rarity;
import inventory.items.items.LongSword;
import inventory.items.items.Sword;
import main.ExitTheDungeon;
import objects.entities.interfaces.Permeable;
import objects.entities.interfaces.StaticEntity;
import objects.hitboxes.Collider;
import objects.hitboxes.Hitbox;
import objects.hitboxes.HitboxAction;
import textures.Skin;
import util.Colors;

import java.awt.*;

public class LootChest implements StaticEntity, Inventoryholder, Permeable {

    private static final int SIZE = 20;

    private final int x, y;
    private final Inventory inv = new Inventory(this, "Lootchest");
    private final Hitbox hitbox;

    private final InventoryView view = new InventoryView(ExitTheDungeon.getPlayer().getInventory(), inv);

    public LootChest(int x, int y) {
        this.x = x - SIZE / 2;
        this.y = y - SIZE / 2;
        hitbox = new Hitbox(x, y, SIZE, SIZE, this, new HitboxAction() {
            @Override
            public void hit(Collider c) {
                if(c.getHolder() instanceof Player p) {
                    p.setOpenedInventory(view);
                }
            }
        });
        inv.addItem(new LongSword(Rarity.weird).setAmount(10));
        inv.addItem(new LongSword(Rarity.uncommon).setAmount(10));
        inv.addItem(new Sword(Rarity.legendary).setAmount(10));
        createEntity();
    }

    @Override
    public void damage(float damage) {

    }

    @Override
    public void heal(float heal) {

    }

    @Override
    public void kill() {

    }

    @Override
    public void paint(Graphics2D g) {
        g.setColor(Colors.brown.getColor());
        g.fillRect(x, y, SIZE, SIZE);
        hitbox.paint(g);
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
    public Inventory getInventory() {
        return inv;
    }

    @Override
    public Hitbox getHitbox() {
        return hitbox;
    }
}
