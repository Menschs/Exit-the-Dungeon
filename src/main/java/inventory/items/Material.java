package inventory.items;

import textures.Texture;
import textures.TextureType;

public enum Material {

    sword("Schwert", 1, TextureType.item_skin.tex("sword")), longsword("Langschwert", 10, TextureType.item_skin.tex("sword"));

    private final String name;
    private final String texture;
    private int maxStack = 20;

    Material(String name, int maxStack, String texture) {
        this.name = name;
        this.maxStack = maxStack;
        this.texture = texture;
    }

    Material(String name, String texture) {
        this.name = name;
        this.texture = texture;
    }

    public String getTexture() {
        return texture;
    }

    public int getMaxStack() {
        return maxStack;
    }

    public String getName() {
        return name;
    }
}
