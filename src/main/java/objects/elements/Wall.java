package objects.elements;

import objects.hitboxes.Hitbox;

import java.awt.*;

public class Wall implements Element {

    private final int x, y;
    private final int width, height;

    private final Hitbox hitbox;

    public Wall(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        hitbox = new Hitbox(x, y, width, height, this, null);
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void paint(Graphics2D g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x, y, width, height);
        //hitbox.paint(g);
    }
}
