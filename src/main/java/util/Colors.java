package util;

import java.awt.*;
import java.util.HashMap;

import static util.ColorTranslator.getTranslator;

public enum Colors {

    black("#000000", "0"),
    dark_blue("#301A90", "1"),
    dark_green("#388D3C", "2"),
    dark_aqua("#008B93", "3"),
    dark_red("#780202", "4"),
    dark_purple("#AC1457", "5"),
    gold("#FD9600", "6"),
    gray("#9E9E9E", "7"),
    dark_gray("#414141", "8"),
    blue("#1464BE", "9"),
    green("#8BC34A", "a"),
    aqua("#8BC34A", "b"),
    red("#c20e0e", "c"),
    light_purple("#F49FFF", "d"),
    yellow("#FFE527", "e"),
    white("#FFFFFF", "f"),

    light_red("#b56b67"),
    brown("#a3531d"),

    none("#7B0071");

    final Color color;
    final String id;

    Colors(String color) {
        this.color = Color.decode(color);
        id = "";
    }

    Colors(String color, String id) {
        this.color = Color.decode(color);
        if(!getTranslator().containsKey(id)) {
            getTranslator().put(id, this);
            this.id = id;
        } else {
            this.id = "";
        }
    }

    public Color getColor() {
        return color;
    }

    public String getIdentifier() {
        return "§" + id;
    }

    public static Colors translate(String id) {
        return getTranslator().getOrDefault(id, none);
    }
}
class ColorTranslator {
    private final static HashMap<String, Colors> translator = new HashMap<>();

    public static HashMap<String, Colors> getTranslator() {
        return translator;
    }
}
