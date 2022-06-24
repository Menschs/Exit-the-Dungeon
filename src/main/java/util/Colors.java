package util;

import java.awt.*;

public enum Colors {

    dark_red("#780202"),
    red("#c20e0e"),
    gold("#c9a40e");

    final Color color;

    Colors(String color) {
        this.color = Color.decode(color);
    }

    public Color getColor() {
        return color;
    }
}
