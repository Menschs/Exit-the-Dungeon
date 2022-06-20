package entities;

import java.awt.*;

public class Bomb implements Object{

    private final int x, y;
    private static final int SIZE = 16;

    public Bomb(int x, int y) {
        this.x = x - SIZE/2;
        this.y = y - SIZE/2;
    }

    @Override
    public void paint(Graphics2D g) {
        g.setColor(Color.red);
        g.fillOval(x, y, SIZE, SIZE);
    }
}
