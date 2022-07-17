package map;

import textures.ItemSkin;
import textures.Skin;
import textures.TextureType;
import util.Vector;

import java.util.HashMap;

public class HeatedTile {

    private final Tile parent;
    private float heat = Float.MAX_VALUE;
    private Vector v;

    private static final HashMap<Tile, HeatedTile> heats = new HashMap<>();

    private ItemSkin s = new ItemSkin(TextureType.item_skin.tex("sword"));

    private HeatedTile(Tile parent) {
        this.parent = parent;
        s.move(parent.getX(), parent.getY());
        s.finish();
        heats.put(parent, this);
    }

    public static HeatedTile get(Tile parent) {
        HeatedTile heat = heats.getOrDefault(parent, new HeatedTile(parent));
        heat.reset();
        return heat;
    }

    public void reset() {
        heat = Float.MAX_VALUE;
        v = Vector.getNullVector();
    }

    public Tile getParent() {
        return parent;
    }

    public float getHeat() {
        return heat;
    }

    public void setHeat(float heat) {
        this.heat = heat;
    }

    public Vector getV() {
        return v;
    }

    public void setV(Vector v) {
        this.v = v;
        s.rotate(new Vector(1, 0).angle(v));
    }

    @Override
    public String toString() {
        return "HeatedTile{" +
                "parent=" + parent +
                ", heat=" + heat +
                ", v=" + v +
                '}';
    }
}
