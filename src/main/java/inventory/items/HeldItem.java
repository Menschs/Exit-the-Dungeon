package inventory.items;

import textures.ItemSkin;
import textures.Skin;

public class HeldItem {

    private final ItemStack item;
    private final ItemSkin skin;
    private float rotation;

    public HeldItem(ItemStack item) {
        this.item = item;
        this.skin = new ItemSkin(item.getMaterial().getTexture());
        skin.finish();
    }

    public void move(float x, float y) {
        skin.move(x, y);
    }

    public void rotate(float theta) {
        rotation = theta;
        skin.rotate(theta);
    }

    public void attack() {
        skin.attack();
    }

    public ItemSkin getSkin() {
        return skin;
    }
}
