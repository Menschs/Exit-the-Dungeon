package objects.entities.interfaces.effects;

import util.Colors;

import java.awt.*;

public enum StatusEffects {

    healing("Healing", Colors.red);

    final String name;
    final Colors color;

    StatusEffects(String name, Colors color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Colors getColor() {
        return color;
    }
}
