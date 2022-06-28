package util.frame.gui.guis;

import main.ExitTheDungeon;
import objects.entities.interfaces.effects.StatusEffect;
import util.frame.gui.GUI;
import util.frame.gui.Text;
import util.frame.gui.Button;

import java.awt.*;
import java.util.List;

public class StatusHUD implements GUI {

    private final int x = 10;
    private final int y = 20;

    @Override
    public List<Button> getButtons() {
        return null;
    }

    @Override
    public List<Text> getTexts() {
        return null;
    }

    @Override
    public void paint(Graphics2D g) {
        Object[] effects = ExitTheDungeon.getPlayer().getEffects().values().toArray();
        for (int i = 0; i < effects.length; i++) {
            ((StatusEffect)effects[i]).paint(g, x, y + i*30);
        }
    }
}
