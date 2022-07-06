package util.frame.gui.guis;

import main.ExitTheDungeon;
import util.frame.gui.GUI;
import util.frame.gui.Text;
import util.frame.gui.Button;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static main.ExitTheDungeon.frame;

public class StartGUI implements GUI {

    private final List<Button> buttons = new ArrayList<>();
    private final List<Text> texts = new ArrayList<>();

    public StartGUI() {
        addText(new Text("§cExit-The-Dungeon", frame.getWidth()/2, frame.getHeight()/4, frame.getWidth()/20));
        addButton(new Button(new Text("§fhardcore", 0, 0, 20), frame.getWidth()/2 + 250, frame.getHeight()/2, frame.getWidth()/8, frame.getHeight()/12, Color.red, new Runnable() {
            @Override
            public void run() {
                ExitTheDungeon.resumeGame();
            }
        }));
        addButton(new Button(new Text("§fcasual", 0, 0, 20), frame.getWidth()/2 - 250, frame.getHeight()/2, frame.getWidth()/8, frame.getHeight()/12, Color.red, new Runnable() {
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
