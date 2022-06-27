package util;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Text {

    private String text;
    private int x, y;
    private Color c;

    public Text(String text, int x, int y, Color c) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.c = c;
    }

    public void paint(Graphics2D g) {
        g.setColor(c);
        Rectangle2D r = g.getFontMetrics().getStringBounds(text, g);
        int mX = (int) (x - r.getWidth()/2);
        int mY = (int) (y - r.getHeight()/2);
        g.drawString(text, mX, mY);
    }
}
