package inventory;

import java.util.ArrayList;
import java.util.List;

public class Inventory {

    private final List<Item> items = new ArrayList<>();
    private final Inventoryholder holder;

    public Inventory(Inventoryholder holder) {
        this.holder = holder;
    }
}
