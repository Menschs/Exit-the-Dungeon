package util.frame.gui;

import java.awt.*;

public class Button {


    private final Text text;
    private final int x, y, width, height;
    private Color c;

    private final Runnable onClick;

    public Button(Text text, int x, int y, int width, int height, Color c, Runnable onClick) {
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
        int mX = x + width/2;
        int mY = y + height/2;
        text.setX(mX);
        text.setY(mY);
        text.paint(g);
    }
}
