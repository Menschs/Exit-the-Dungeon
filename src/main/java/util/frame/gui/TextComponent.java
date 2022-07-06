package util.frame.gui;

import java.awt.*;

public class TextComponent {

    private final Font font;
    private final Color c;
    private String text = "";

    public TextComponent(Font font, Color c) {
        this.font = font;
        this.c = c;
    }

    public TextComponent(String text, Font font, Color c) {
        this.text = text;
        this.font = font;
        this.c = c;
    }

    public void add(String c) {
        text += c;
    }

    public int getWidth(Graphics2D g) {
        g.setFont(font);
        return g.getFontMetrics().stringWidth(text);
    }

    public void paint(Graphics2D g, int x, int y) {
        g.setFont(font);
        g.setColor(c);
        g.drawString(text, x, y);
    }
}
