package util;

import main.ExitTheDungeon;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PauseGUI implements GUI {

    private final List<Button> buttons = new ArrayList<>();
    private final List<Text> texts = new ArrayList<>();

    public PauseGUI() {
        addText(new Text("pause", ExitTheDungeon.getFrame().getWidth()/2, 60, Color.red));
        addButton(new Button("resume", ExitTheDungeon.getFrame().getWidth()/2, 200, 150, 50, Color.red, new Runnable() {
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
