package textures;

public enum TextureType {

    player_skin("entities.player"),
    wall_skin("elements.wall"),
    ground_skin("elements.ground"),
    entity_skin("entities"),
    item_skin("items"),
    hitbox_skin("hitbox"),
    pointer_skin("util.pointer"),
    unknown("");

    private String directory;

    TextureType(String directory) {
        this.directory = directory;
    }

    public static TextureType get(String s) {
        TextureType result;
        try {
            result = valueOf(s);
        } catch (IllegalArgumentException ix) {
            result = unknown;
        }
        return result;
    }

    public String tex(String name) {
        return this + "." + name;
    }

    public String getDirectory() {
        return "textures." + directory + ".";
    }
}
