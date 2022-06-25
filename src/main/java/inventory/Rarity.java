package inventory;

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
        switch (this) {
            case common -> {
                return uncommon;
            }
            case uncommon -> {
                return rare;
            }
            case rare -> {
                return epic;
            }
            case epic -> {
                return legendary;
            }
            case legendary -> {
                return weird;
            }
            case weird -> {
                return common;
            }
        }
        return common;
    }
}
