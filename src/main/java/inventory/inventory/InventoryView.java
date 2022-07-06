package inventory.inventory;

import inventory.items.ItemStack;
import main.ExitTheDungeon;
import objects.entities.Player;
import util.Colors;
import util.frame.gui.Text;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class InventoryView {

    private static final int x = 30;

    public final List<Inventory> invs;
    private ItemStack paintLore = null;
    private int[] lore = null;

    public InventoryView(Inventory... invs) {
        this.invs = Arrays.asList(invs);
    }

    public void paint(Graphics2D g) {
        for (int i = 0; i < invs.size(); i++) {
            invs.get(i).paint(g, x + 150*i);
        }
        if(paintLore != null) {
            List<Text> loreText = paintLore.getLore();
            int x = lore[0] + 20;
            int y = lore[1];
            for (int i = 0; i < loreText.size(); i++) {
                loreText.get(i).setX(x + 5);
                loreText.get(i).setY(y + 15 + i * 15);
            }
            g.setColor(Colors.black.getColor());
            g.fillRoundRect(x, y, 200, loreText.size() * 15 + 5, 5, 5);
            loreText.forEach(text -> text.paint(g));
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

    public void hover(int x, int y) {
        Player p = ExitTheDungeon.getPlayer();
        Inventory inv = getInventory(x);
        ItemStack item = getItem(x, y);
        if(item == null) {
            paintLore = null;
            return;
        }
        paintLore = item;
        lore = new int[] {x, y};
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
            int sY = Inventory.y + i1 * 20;
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
