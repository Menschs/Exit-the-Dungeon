package map;

import java.util.ArrayList;
import java.util.List;

import static map.Tilemap.*;

public class Tile {

    private final float x, y;
    private final List<Object> blocking = new ArrayList<>();

    public Tile(float x, float y) {
        this.x = x;
        this.y = y;
        String identifier = getIdentifier();
        if(!tiles.containsKey(identifier)) tiles.put(identifier, this);
    }

    public Tile[] getNeighbourTiles() {
        return new Tile[] {
                getTile(x - TILE_SIZE, y),
                getTile(x - TILE_SIZE, y - TILE_SIZE),
                getTile(x, y - TILE_SIZE),
                getTile(x + TILE_SIZE, y - TILE_SIZE),
                getTile(x + TILE_SIZE, y),
                getTile(x + TILE_SIZE, y + TILE_SIZE),
                getTile(x, y + TILE_SIZE),
                getTile(x - TILE_SIZE, y + TILE_SIZE),
            };
    }

    public void block(Object o) {
        if(!blocking.contains(o)) blocking.add(o);
    }

    public boolean isBlocked() {
        return !blocking.isEmpty();
    }

    public void unBlock(Object o) {
        blocking.remove(o);
    }

    public String getIdentifier() {
        return Tilemap.getIdentifier(x, y);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "x=" + x +
                ", y=" + y +
                ", blocking=" + blocking +
                '}';
    }
}
