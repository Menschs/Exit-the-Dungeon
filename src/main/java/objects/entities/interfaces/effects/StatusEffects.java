package objects.entities.interfaces.effects;

import util.Colors;

import java.awt.*;

public enum StatusEffects {

    healing("Healing", Color.red);

    final String name;
    final Color color;

    StatusEffects(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }
}
