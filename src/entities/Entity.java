package entities;

import util.Vector;

import java.awt.*;

public interface Entity {

    void move(Vector v);
    void rotate(float rotation);

    void paint(Graphics2D g);
}
