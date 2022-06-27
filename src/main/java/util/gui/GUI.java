package util.gui;

import java.awt.*;
import java.util.List;

public interface GUI {

    List<Button> getButtons();
    List<Text> getTexts();

    default void addButton(Button b) {
        getButtons().add(b);
    }

    default void addText(Text t) {
        getTexts().add(t);
    }

    default void paint(Graphics2D g) {
        getButtons().forEach(button -> button.paint(g));
        getTexts().forEach(text -> text.paint(g));
    }

    default void click(int x, int y) {
        getButtons().forEach(button -> button.click(x, y));
    }
}
