package util;

import main.ExitTheDungeon;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StartGUI implements GUI {

    private final List<Button> buttons = new ArrayList<>();
    private final List<Text> texts = new ArrayList<>();

    public StartGUI() {
        addText(new Text("Exit-The-Dungeon", ExitTheDungeon.getFrame().getWidth()/2, 60, Color.red));
        addButton(new Button("hardcore", ExitTheDungeon.getFrame().getWidth()/2 + 250, 200, 150, 50, Color.red, new Runnable() {
            @Override
            public void run() {
                ExitTheDungeon.resumeGame();
            }
        }));
        addButton(new Button("casual", ExitTheDungeon.getFrame().getWidth()/2 - 250, 200, 150, 50, Color.red, new Runnable() {
            @Override
            public void run() {
                ExitTheDungeon.resumeGame();
            }
        }));
    }

    @Override
    public List<Button> getButtons() {
        return buttons;
    }

    @Override
    public List<Text> getTexts() {
        return texts;
    }
}
