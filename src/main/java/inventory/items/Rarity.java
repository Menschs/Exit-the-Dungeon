package inventory.items;

import java.awt.*;

public enum Rarity {

    common("#a8a8a8"), uncommon("#93b36f"), rare("#607bdb"), epic("#cf60db"), legendary("#d9932b"), weird("#c24a6e");

    private final Color c;

    Rarity(String colorCode) {
        this.c = Color.decode(colorCode);
    }

    public Color getColor() {
        return c;
    }

    public Rarity next() {
        int index = indexOf(this)+1;
        if(index >= values().length) index = 0;
        return values()[index];
    }

    public int getIndex() {
        for (int i = 0; i < values().length; i++) {
            if(values()[i] == this) return i;
        }
        return -1;
    }

    public static int indexOf(Rarity r) {
        return r.getIndex();
    }
}
