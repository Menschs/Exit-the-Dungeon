package inventory;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Inventory {


    private static final int x = 30;
    private static final int y = 30;

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

    public void paint(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.drawString(name, x + 5, y- 5);
        for (int i = 0; i < items.size(); i++) {
            items.get(i).paint(g, x, y + (i) * 20);
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
                System.out.println(add);
                if(add <= 0) return 0;
            }
        }
        if(items.size() < 32) {
            i.setAmount(add);
            items.add(i);
            return 0;
        } else {
            return add;
        }
    }

    public void removeItem(ItemStack i) {
        int remove = i.getAmount();
        List<ItemStack> removeT = new ArrayList<>();
        for (ItemStack item : items) {
            if(item.equals(i)) {
                remove = item.remove(remove);
                System.out.println(remove);
                if(item.getAmount() <= 0){
                    removeT.add(item);
                }
                if(remove <= 0) {
                    return;
                }
            }
        }
        items.removeAll(removeT);
    }
}
