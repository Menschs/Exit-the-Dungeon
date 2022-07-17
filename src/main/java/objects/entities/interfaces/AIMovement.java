package objects.entities.interfaces;

import main.ExitTheDungeon;
import map.HeatedTile;
import map.Tile;
import map.Tilemap;
import objects.interfaces.Updating;
import util.Debugger;
import util.FloatComparator;
import util.Point;
import util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import static map.Tilemap.*;

public interface AIMovement {

    HashMap<String, Vector>[] path = new HashMap[] {new HashMap<>()};
    Tile[] prevTile = new Tile[1];

    Vector[] directionVectors = new Vector[] {
            new Vector(-TILE_SIZE, 0).normalize(),
            new Vector(-TILE_SIZE, -TILE_SIZE).normalize().multiply(DIAGONALLY),
            new Vector(0, -TILE_SIZE).normalize(),
            new Vector(TILE_SIZE, -TILE_SIZE).normalize().multiply(DIAGONALLY),
            new Vector(TILE_SIZE, 0).normalize(),
            new Vector(TILE_SIZE, TILE_SIZE).normalize().multiply(DIAGONALLY),
            new Vector(0, TILE_SIZE).normalize(),
            new Vector(-TILE_SIZE,TILE_SIZE).normalize().multiply(DIAGONALLY),
    };

    default Vector find(float x, float y) {
        return path[0].getOrDefault(getTile(x, y).getIdentifier(), Vector.getNullVector());
    }

    static void generatePath() {
        Tile t = getTile(ExitTheDungeon.getPlayer().getX(), ExitTheDungeon.getPlayer().getY());
        if(t == prevTile[0]) return;
        HashMap<String, HeatedTile> heatMap = generateHeatMap(t.getX(), t.getY());
        if(heatMap.isEmpty()) return;
        heatMap.values().forEach(heatedTile -> {
            Tile parent = heatedTile.getParent();
            Vector v = Vector.getNullVector();
            float heat = heatedTile.getHeat();

            Tile[] neighbours = parent.getNeighbourTiles();

            for (int i = 0; i < neighbours.length; i++) {
                HeatedTile hNeighbour = heatMap.get(neighbours[i].getIdentifier());
                if(hNeighbour == null) {
                    v = Vector.getNullVector();
                    break;
                }
                Vector v2 = directionVectors[i].clone().multiply(heat);
                Debugger.debug(v2);
                v2.multiply(1/v2.length());
                v.add(v2);
            }
            Debugger.debug(v);
            path[0].put(parent.getIdentifier(), v.normalize());
        });
        prevTile[0] = t;
    }

    void move();

    /*
    default Stack<Point> find(Point start, Point goal) {
        if(start == goal) return null;
        Stack<Point> path = new Stack<>();
        path.add(start);
        Point current = start;
        while(current != goal) {
            current = getBestPoint(start, current, goal);
            path.add(current);
        }
        return path;
    }

    default Point getBestPoint(Point start, Point current, Point goal) {
        HashMap<Float, Point> points = new HashMap<>();

        find(start, current.move(0.5f, 0), goal, points);
        find(start, current.move(-0.5f, 0), goal, points);
        find(start, current.move(0, 0.5f), goal, points);
        find(start, current.move(0, -0.5f), goal, points);
        find(start, current.move(0.5f, 0.5f), goal, points);
        find(start, current.move(0.5f, -0.5f), goal, points);
        find(start, current.move(-0.5f, 0.5f), goal, points);
        find(start, current.move(-0.5f, -0.5f), goal, points);

        List<Float> list = new ArrayList<>(points.keySet());
        list.sort(new FloatComparator());
        return points.get(list.get(0));
    }

    default float find(Point start, Point current, Point goal, HashMap<Float, Point> map) {

        float h_cost;
        float g_cost;

        {
            float lengthX = goal.getX() - current.getX();
            float lengthY = goal.getY() - current.getY();

            float diagonal_length;
            if(lengthX > lengthY) {
                diagonal_length = lengthY * DIAGONALLY;
                lengthX -= lengthY;
                lengthY = 0;
            } else {
                diagonal_length = lengthX * DIAGONALLY;
                lengthY -= lengthX;
                lengthX = 0;
            }

            h_cost = lengthX + lengthY + diagonal_length;
        }
        {
            float lengthX = current.getX() - start.getX();
            float lengthY = current.getY() - start.getY();

            float diagonal_length = (lengthX > lengthY) ? (lengthX -= lengthY) * DIAGONALLY : (lengthY -= lengthX) * DIAGONALLY;

            g_cost = lengthX + lengthY + diagonal_length;
        }

        float f_cost = g_cost + h_cost;
        f_cost = Math.abs(f_cost);
        map.put(f_cost, current);
        return f_cost;
    }
    */
}
