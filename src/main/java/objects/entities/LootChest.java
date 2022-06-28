package objects.entities;

import inventory.*;
import main.ExitTheDungeon;
import objects.entities.interfaces.Permeable;
import objects.entities.interfaces.StaticEntity;
import objects.hitboxes.Collider;
import objects.hitboxes.Hitbox;
import objects.hitboxes.HitboxAction;
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
                if(c.getEntity() instanceof Player p) {
                    p.setOpenedInventory(view);
                }
            }
        });
        inv.addItem(new ItemStack(Material.longsword, Itemtype.weapon, Rarity.weird).setAmount(10));
        inv.addItem(new ItemStack(Material.longsword, Itemtype.weapon, Rarity.uncommon).setAmount(10));
        inv.addItem(new ItemStack(Material.longsword, Itemtype.weapon, Rarity.epic).setAmount(10));
        createEntity();
    }

    @Override
    public void damage(double damage) {

    }

    @Override
    public void heal(double heal) {

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
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
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
