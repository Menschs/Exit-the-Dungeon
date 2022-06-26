package inventory;

import main.ExitTheDungeon;
import objects.entities.Player;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class InventoryView {

    private static final int x = 30;

    public final List<Inventory> invs;

    public InventoryView(Inventory... invs) {
        this.invs = Arrays.asList(invs);
    }

    public void paint(Graphics2D g) {
        for (int i = 0; i < invs.size(); i++) {
            invs.get(i).paint(g, x + 150*i);
        }
        //g.setColor(Color.red);
        //for (int i = 0; i < invs.size(); i++) {
        //    int sX = InventoryView.x + 150*i;
        //        Inventory inv = invs.get(i);
        //        for (int i1 = 0; i1 < inv.getItems().size(); i1++) {
        //            int sY = Inventory.y + i1 * 20;
        //            g.drawRect(sX, sY, 120, 20);
        //        }
        //}
    }

    public void add(Inventory inv) {
        invs.add(inv);
    }

    private int inventoryIndex = 0;
    private int itemIndex = 0;

    public void click(int x, int y) {
        Player p = ExitTheDungeon.getPlayer();
        Inventory inv = getInventory(x);
        ItemStack item = getItem(x, y);
        if(item == null) return;
        if(inventoryIndex == 0) {
            p.dropItem(item);
            p.getInventory().getItems().remove(itemIndex);
        } else {
            int prev = item.getAmount();
            int added = p.getInventory().addItem(item.clone());
            if(prev == prev - added) {
                inv.getItems().remove(itemIndex);
            } else {
                item.setAmount(added);
            }
        }
    }

    public boolean drop(int x, int y) {
        Inventory inv = getInventory(x);
        ItemStack item = getItem(x, y);
        if(item == null) return false;
        ExitTheDungeon.getPlayer().dropItem(item);
        inv.removeItem(itemIndex);
        return true;
    }

    public Inventory getInventory(int x) {
        for (int i = 0; i < invs.size(); i++) {
            int sX = InventoryView.x + 150*i;
            if(x >= sX && x <= sX + 120) {
                inventoryIndex = i;
                return invs.get(i);
            }
        }
        return null;
    }

    public ItemStack getItem(int x, int y) {
        Inventory inv = getInventory(x);
        if(inv == null) return null;

        for (int i1 = 0; i1 < inv.getItems().size(); i1++) {
            int sY = Inventory.y*2 + i1 * 20;
            if(y >= sY && y <= sY + 20) {
                itemIndex = i1;
                return inv.getItem(i1);
            }
        }
        return null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(invs);
    }
}
