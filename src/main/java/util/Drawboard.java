package util;

import objects.entities.Entity;
import objects.elements.Element;

import javax.swing.*;
import java.awt.*;
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
        objects.forEach(object -> object.paint((Graphics2D) g));
        entities.forEach(entity -> {
            entity.paint((Graphics2D) g);
        });
    }
}
