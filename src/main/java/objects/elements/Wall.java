package objects.elements;

import objects.elements.interfaces.Element;
import textures.TextureType;
import textures.WallSkin;

import java.awt.*;

public class Wall implements Element {

    private final double x, y;
    private final double width, height;

    private final WallSkin s;

    //private final Hitbox hitbox;

    public Wall(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        s = new WallSkin(TextureType.wall_skin.tex("default"), x, y, width, height, 1);
        //hitbox = new Hitbox(x, y, width, height, this, null);
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void paint(Graphics2D g) {
        g.setColor(Color.LIGHT_GRAY);
        //g.fillRect(x, y, width, height);
        //hitbox.padouble(g);
    }
}
