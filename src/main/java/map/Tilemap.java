package map;

import util.Debugger;
import util.Interval;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tilemap {

    protected static final float HEATMAP_SIZE_X = 1, HEATMAP_SIZE_Y = 1;
    public static final float TILE_SIZE = 0.5f, DIAGONALLY = (float) Math.sqrt(2 * Math.pow(TILE_SIZE, 2));

    protected static final HashMap<String, Tile> tiles = new HashMap<>();

    public static Tile getTile(float x, float y) {
        x = roundFloat(x);
        y = roundFloat(y);
        return tiles.getOrDefault(getIdentifier(x, y), new Tile(x, y));
    }

    public static String getIdentifier(float x, float y) {
        return "x" + x + "y" + y;
    }

    public static float roundFloatDown(float f) {
        return (int) f;
    }

    public static float roundFloatUp(float f) {
        float rounded = roundFloatDown(f);
        if(rounded == (int) f) return f;
        int minus = (f < 0) ? -1 : 1;
        return roundFloatDown(f) + minus;
    }

    public static float roundFloat(float f) {
        if(f == 0) return 0;
        int minus = (f < 0) ? -1 : 1;
        f = Math.abs(f);
        float min = (int) f;
        float max = min + TILE_SIZE;
        if(f > max) {
            min += 0.5;
            max += 0.5;
        }
        float result = max - f;
        if(result > 0.25) return minus * min;
        else return minus * max;
    }

    public static HashMap<String, HeatedTile> generateHeatMap(float midX, float midY) {
        return generateHeatMap(getTile(midX, midY));
    }

    public static HashMap<String, HeatedTile> generateHeatMap(Tile start) {
        final HashMap<String, HeatedTile> result = new HashMap<>();
        if(start.isBlocked()) return result;
        HeatedTile heatedStart = HeatedTile.get(start);
        heatedStart.setHeat(0);
        result.put(start.getIdentifier(), heatedStart);
        for (Tile neighbour : start.getNeighbourTiles()) {
            generateHeat(heatedStart, neighbour, result, new float[] {start.getX(), start.getY()});
        }
        return result;
    }

    private static void generateHeat(HeatedTile previous, Tile t, HashMap<String, HeatedTile> heatMap, float[] mid) {
        Tile parent = previous.getParent();
        if(t.isBlocked() || Math.abs(t.getX()) - Math.abs(mid[0]) > HEATMAP_SIZE_X || Math.abs(t.getY()) - Math.abs(mid[1])  > HEATMAP_SIZE_Y) return;
        float distance = (parent.getX() - t.getX() != 0 && parent.getY() - t.getY() != 0) ? DIAGONALLY : TILE_SIZE;
        float heat = previous.getHeat() + distance;
        heat = Math.abs(heat);
        HeatedTile current = heatMap.getOrDefault(t.getIdentifier(), HeatedTile.get(t));

        if(heat < current.getHeat()) current.setHeat(heat);

        if(!heatMap.containsKey(t.getIdentifier())) heatMap.put(t.getIdentifier(), current);

        for (Tile neighbour : t.getNeighbourTiles()) {
            if(!heatMap.containsKey(neighbour.getIdentifier())) generateHeat(current, neighbour, heatMap, mid);
        }
    }

    public static void block(float x, float y, Object o) {
        block(getTile(x, y), o);
    }

    public static void block(Tile p, Object o) {
        p.block(o);
    }

    public static void unBlock(float x, float y, Object o) {
        unBlock(getTile(x, y), o);
    }

    public static void blockSquare(Interval xI, Interval yI, float width, float height, Object o) {
        width = roundFloatUp(width);
        height = roundFloatUp(height);
        for (float x = roundFloatDown(xI.getS()); x < roundFloatUp(xI.getE()); x += TILE_SIZE) {
            for (float y = roundFloatDown(yI.getS()); y < roundFloatUp(yI.getE()); y += TILE_SIZE) {
                block(x, y, o);
                block(x + width, y, o);
                block(x, y + height, o);
                block(x + width, y + height, o);
            }
        }
    }

    public static void block (Interval xI, Interval yI, Object o ) {
        for (float x = roundFloatDown(xI.getS()); x < roundFloatUp(xI.getE()); x += TILE_SIZE) {
            for (float y = roundFloatDown(yI.getS()); y < roundFloatUp(yI.getE()); y += TILE_SIZE) {
                block(x, y, o);
            }
        }
    }

    public static void unBlockSquare(Interval xI, Interval yI, float width, float height, Object o) {
        width = roundFloatUp(width);
        height = roundFloatUp(height);
        for (float x = roundFloatDown(xI.getS()); x < roundFloatUp(xI.getE()); x += TILE_SIZE) {
            for (float y = roundFloatDown(yI.getS()); y < roundFloatUp(yI.getE()); y += TILE_SIZE) {
                unBlock(x, y, o);
                unBlock(x + width, y, o);
                unBlock(x, y + height, o);
                unBlock(x + width, y + height, o);
            }
        }
    }

    public static void unBlock (Interval xI, Interval yI, Object o ) {
        for (float x = roundFloatDown(xI.getS()); x < roundFloatUp(xI.getE()); x += TILE_SIZE) {
            for (float y = roundFloatDown(yI.getS()); y < roundFloatUp(yI.getE()); y += TILE_SIZE) {
                unBlock(x, y, o);
            }
        }
    }

    public static void unBlock(Tile p, Object o) {
        p.unBlock(o);
    }

    public static boolean isBlocked(float x, float y) {
        return isBlocked(getTile(x, y));
    }

    public static boolean isBlocked(Tile p) {
        return p.isBlocked();
    }
}
