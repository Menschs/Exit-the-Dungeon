package textures;

public enum TextureType {

    player_skin, wall_skin, ground_skin, unknown;

    public static TextureType get(String s) {
        TextureType result;
        try {
            result = valueOf(s);
        } catch (IllegalArgumentException ix) {
            result = unknown;
        }
        return result;
    }
}
