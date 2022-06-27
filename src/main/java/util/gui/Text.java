package util.gui;

import javax.swing.text.Style;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Text {

    private String text;
    private int x, y;
    private Color c;
    private Font f = new Font(Font.DIALOG, Font.PLAIN, 15);

    public Text(String text, int x, int y, Color c) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.c = c;
    }

    public Text(String text, int x, int y, Color c, Font f) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.c = c;
        this.f = f;
    }

    public Text(String text, int x, int y, Color c, int fSize) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.c = c;
        f = new Font(Font.DIALOG, Font.PLAIN, fSize);
    }

    public void paint(Graphics2D g) {
        g.setColor(c);
        g.setFont(f);
        Rectangle2D r = g.getFontMetrics().getStringBounds(text, g);
        int mX = (int) (x - r.getWidth()/2);
        int mY = (int) (y - r.getHeight()/2);
        g.drawString(text, mX, mY);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
