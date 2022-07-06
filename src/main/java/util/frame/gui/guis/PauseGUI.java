package util.frame.gui.guis;

import main.ExitTheDungeon;
import util.Colors;
import util.frame.gui.GUI;
import util.frame.gui.Text;
import util.frame.gui.Button;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static main.ExitTheDungeon.frame;

public class PauseGUI implements GUI {

    private final List<Button> buttons = new ArrayList<>();
    private final List<Text> texts = new ArrayList<>();

    public PauseGUI() {
        addText(new Text(Colors.red.getIdentifier() + "II pause", frame.getWidth()/2, frame.getHeight()/4, frame.getWidth()/25));
        addButton(new Button(new Text("resume", 0, 0, 20), ExitTheDungeon.getFrame().getWidth()/2, frame.getHeight()/2, frame.getWidth()/8, frame.getHeight()/12, Color.red, new Runnable() {
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
