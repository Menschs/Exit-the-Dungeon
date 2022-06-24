package util;

import java.awt.*;

public enum Colors {

    dark_red(780202);

    final Color color;

    Colors(int color) {
        this.color = Color.decode("#" + color);
    }

    public Color getColor() {
        return color;
    }
}
