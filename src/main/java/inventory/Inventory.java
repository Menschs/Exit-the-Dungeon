package inventory;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private final ItemStack[] items = new ItemStack[32];
    private final Inventoryholder holder;

    public Inventory(Inventoryholder holder) {
        this.holder = holder;
    }

    public void addItem(Item i) {

    }
}
