package util.frame.gui;

import main.ExitTheDungeon;
import objects.entities.interfaces.Entity;
import objects.elements.interfaces.Element;
import objects.entities.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.ArrayList;

public class Drawboard extends JPanel {

    private final List<Entity> entities = new ArrayList<>();
    private final List<Element> objects = new ArrayList<>();

    public void addObject(Element o) {
        objects.add(o);
    }

    public void removeElement(Element o) {
        objects.remove(o);
    }

    public void addEntity(Entity e) {
        entities.add(e);
    }

    public void removeEntity(Entity e) {
        entities.remove(e);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D gx = (Graphics2D) g;
        gx.setColor(Color.darkGray);
        gx.fillRect(0, 0, ExitTheDungeon.getFrame().getWidth(), ExitTheDungeon.getFrame().getHeight());
        if(ExitTheDungeon.isStarted()) {
            try {
                objects.forEach(object -> object.paint(gx));
                entities.forEach(entity -> {
                    if(!(entity instanceof Player)) entity.paint(gx);
                });
                ExitTheDungeon.getPlayer().paint(gx);
                ExitTheDungeon.getHUDs().forEach((huDs, gui) -> gui.paint(gx));
                if(!ExitTheDungeon.isGaming()) ExitTheDungeon.getGui().paint(gx);
            } catch (ConcurrentModificationException ignored){}
        } else {
            ExitTheDungeon.getGui().paint(gx);
        }
        //repaint();
    }
}
