package map;

import util.Vector;

public class HeatedTile {

    private final Tile parent;
    private float heat;
    private Vector v;

    public HeatedTile(Tile parent, float heat) {
        this.parent = parent;
        this.heat = heat;
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
