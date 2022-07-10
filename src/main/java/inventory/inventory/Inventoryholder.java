package inventory.inventory;

import inventory.items.ItemStack;
import objects.entities.DroppedItem;

public interface Inventoryholder {

    default void addItem(ItemStack i) {
        int excess = getInventory().addItem(i);
        if(excess > 0) {
            i.setAmount(excess);
            dropItem(i);
        }
    }

    default boolean addDroppedItem(DroppedItem di) {
        int excess = getInventory().addItem(di.getItem());
        if(excess <= 0) return true;
        di.getItem().setAmount(excess);
        return false;
    }

    float getX();
    float getY();

    default void dropItem(ItemStack i) {
        i.drop((int) getX(), (int) getY());
    }
    Inventory getInventory();
}
