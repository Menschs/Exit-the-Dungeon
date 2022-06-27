package util.gui.guis;

import main.ExitTheDungeon;
import util.gui.Button;
import util.gui.GUI;
import util.gui.Text;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static main.ExitTheDungeon.frame;

public class PauseGUI implements GUI {

    private final List<util.gui.Button> buttons = new ArrayList<>();
    private final List<Text> texts = new ArrayList<>();

    public PauseGUI() {
        addText(new Text("II pause", frame.getWidth()/2, frame.getHeight()/4, Color.red, new Font(Font.DIALOG, Font.PLAIN, frame.getWidth()/25)));
        addButton(new util.gui.Button(new Text("resume", 0, 0, Color.white, 20), ExitTheDungeon.getFrame().getWidth()/2, frame.getHeight()/2, frame.getWidth()/8, frame.getHeight()/12, Color.red, new Runnable() {
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
