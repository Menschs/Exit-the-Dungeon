package util.frame.gui;

import util.Colors;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Text {

    private String text;
    private int x, y;
    private Font f = new Font(Font.DIALOG, Font.PLAIN, 15);
    private TextCenter center = TextCenter.mid;

    private final List<TextComponent> components = new ArrayList<>();

    public Text(String text, int x, int y) {
        this.text = text;
        this.x = x;
        this.y = y;
        init();
    }

    public Text(String text, int x, int y, int fSize) {
        this.text = text;
        this.x = x;
        this.y = y;
        f = new Font(Font.DIALOG, Font.PLAIN, fSize);
        init();
    }

    public Text(String text, int x, int y, TextCenter center) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.center = center;
        init();
    }

    public Text(String text, int x, int y, int fSize, TextCenter center) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.center = center;
        f = new Font(Font.DIALOG, Font.PLAIN, fSize);
        init();
    }

    private void init() {
        char[] chars = text.toCharArray();
        this.text = "";
        boolean change = false;
        TextComponent text = getNewComponent(new TextComponent(f, Colors.white.getColor()));
        Color c = Colors.white.getColor();
        for (char aChar : chars) {
            if (change) {
                Colors c1 = Colors.translate(aChar + "");
                if (c1 == Colors.none) {
                    text.add(aChar + "");
                    this.text += aChar;
                } else {
                    if (!c.equals(c1.getColor())) text = getNewComponent(new TextComponent(f, c1.getColor()));
                    c = c1.getColor();
                }
                change = false;
            } else {
                change = (aChar == '§');
                if (change) continue;
                this.text += aChar;
                text.add(aChar + "");
            }
        }
    }

    public TextComponent getNewComponent(TextComponent comp) {
        components.add(comp);
        return comp;
    }

    public void paint(Graphics2D g) {
        g.setFont(f);
        Rectangle2D r = g.getFontMetrics().getStringBounds(text, g);
        int mX = (center == TextCenter.left) ? x : (int) ((center == TextCenter.mid) ? x - r.getWidth() / 2 : x - r.getWidth());
        int mY = (center == TextCenter.left) ? y : (int) ((center == TextCenter.mid) ? y - r.getHeight() / 2 : y - r.getHeight());

        int lastWidth = 0;
        for (TextComponent component : components) {
            component.paint(g, mX + lastWidth , mY);
            lastWidth = component.getWidth(g);
        }
    }

    public void setCenter(TextCenter center) {
        this.center = center;
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
