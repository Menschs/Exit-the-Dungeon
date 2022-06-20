package entities;

import util.Vector;

import java.awt.*;

public class Player implements Entity {

    private final static int[] modelX = new int[] {30, 0, 60};
    private final static int[] modelY = new int[] {0, 70, 70};

    private float x = 0;
    private float y = 0;
    private float rotation = 0;

    public Player(float x, float y, float rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
    }

    @Override
    public void move(Vector v) {
        x += v.getX();
        y += v.getY();
    }

    @Override
    public void rotate(float rotation) {
        this.rotation += rotation * Math.PI/180;
    }

    @Override
    public void paint(Graphics2D g) {
        g.setColor(Color.BLACK);
        int[][] model = rotate(modelX, modelY, rotation);
        g.drawPolygon(model[0], model[1], 3);
    }

    public int[][] rotate(int[] toRX, int[] toRY, float theta) {
        int[][] result = new int[2][toRX.length];
        var sinTheta = Math.sin(theta);
        var cosTheta = Math.cos(theta);
        for (int i = 0; i < toRX.length; i++) {
            var x = toRX[i] - 30;
            var y = toRY[i] - 35;
            result[0][i] = (int) (x * cosTheta - y * sinTheta + this.x);
            result[1][i] = (int) (y * cosTheta + x * sinTheta + this.y);
        }
        return result;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getRotation() {
        return rotation;
    }
}
