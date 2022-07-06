package inventory.inventory;

import inventory.items.ItemStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Inventory {

    static final int y = 30;
    static final int invSize = 5;

    private final List<ItemStack> items = new ArrayList<>();
    private final Inventoryholder holder;
    private final String name;

    public Inventory(Inventoryholder holder, String name) {
        this.holder = holder;
        this.name = name;
    }

    public Inventoryholder getHolder() {
        return holder;
    }

    public void paint(Graphics2D g, int addX) {
        g.setColor(Color.LIGHT_GRAY);
        g.drawString(name, addX + 5, y - 5);
        for (int i = 0; i < items.size(); i++) {
            items.get(i).paint(g, addX, y + (i) * 20);
        }
    }

    public ItemStack getItem(int index) {
        return items.get(index);
    }

    public int addItem(ItemStack i) {
        int add = i.getAmount();
        for (ItemStack item : items) {
            if(item.equals(i)) {
                add = item.add(add);
                if(add <= 0) return 0;
            }
        }
        if(items.size() < invSize) {
            i.setAmount(add);
            items.add(i);
            return 0;
        } else {
            return add;
        }
    }

    public ItemStack removeItem(int index) {
        if(items.size() <= index) return null;
        var i = items.get(index);
        items.remove(index);
        return i;
    }

    public void removeItem(ItemStack i) {
        removeAlg(i, 0);
    }

    public void removeAlg(ItemStack i, int index) {
        ItemStack item = items.get(index);
        if(item.equals(i)) {
            int remove = i.getAmount();
            remove = item.remove(remove);
            if(item.getAmount() <= 0){
                items.remove(item);
                index--;
            }
            if(remove <= 0) {
                return;
            }
            i.setAmount(remove);
        }
        index++;
        if(index < items.size()) removeAlg(i, index);
    }

    public List<ItemStack> getItems() {
        return items;
    }
}
