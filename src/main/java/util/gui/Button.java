package util.gui;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Button {


    private final String text;
    private final int x, y, width, height;
    private Color c;

    private final Runnable onClick;

    public Button(String text, int x, int y, int width, int height, Color c, Runnable onClick) {
        this.text = text;
        this.x = x - width/2;
        this.y = y - height/2;
        this.c = c;
        this.width = width;
        this.height = height;
        this.onClick = onClick;
    }

    public void click(int x, int y) {
        if(x >= this.x && x <= this.x + width) {
            if(y >= this.y && y <= this.y + height) {
                new Thread(onClick).start();
            }
        }
    }

    public void paint(Graphics2D g) {
        g.setColor(c);
        g.fillRoundRect(x, y, width, height, 5, 5);
        g.setColor(Color.WHITE);
        Rectangle2D r = g.getFontMetrics().getStringBounds(text, g);
        int mX = (int) (x + width/2 - r.getWidth()/2);
        int mY = (int) (y + height/2 - r.getHeight()/2);
        g.drawString(text, mX, mY);
    }
}
