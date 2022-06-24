package util;

import objects.entities.Ball;
import objects.entities.Dummy;
import objects.elements.Bomb;
import objects.entities.Player;
import objects.Updating;
import main.ExitTheDungeon;
import objects.elements.Wall;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class KeyyyListener implements KeyListener, MouseListener, MouseMotionListener,  Updating {

    private final Drawboard board;
    private final List<String> pressed = new ArrayList<>();

    public KeyyyListener(Drawboard board) {
        this.board = board;
        create();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        String character = (e.getKeyChar() + "").toLowerCase();
        if(!pressed.contains(character)) pressed.add(character);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Player p = ExitTheDungeon.getPlayer();
        switch (e.getKeyChar() + "") {
            case "t" -> {
                Wall w = new Wall((int) (p.getX() + 100), (int) (p.getY() + 100), 100, 20);
                ExitTheDungeon.getBoard().addObject(w);
                return;
            }
            case "g" -> {
                ExitTheDungeon.getBoard().addEntity(new Dummy(p.getX(), p.getY()));
                return;
            }
            case "b" -> {
                Vector v1 = p.getDirection().add(100, 100);
                ExitTheDungeon.getBoard().addObject(new Bomb((int) (p.getX() + v1.getX()), (int) (p.getY() + v1.getY())));
                return;
            }
        }
        String character = (e.getKeyChar() + "").toLowerCase();
        pressed.remove(character);
    }

    @Override
    public void tick() {
        Player p = ExitTheDungeon.getPlayer();
        final double[] multiply = {0};
        final double[] rotation = {0};
        List<String> remove = new ArrayList<>();
        pressed.forEach(character -> {
            switch (character) {
                case "d" -> p.rotate(2.5);
                case "a" -> p.rotate(-2.5);
                case "w" -> multiply[0] = 5;
                case "s" -> multiply[0] = -5;
            }
        });
        pressed.removeAll(remove);
        Vector v = p.getDirection();
        v.rotate(rotation[0]);
        v.normalize().multiply(multiply[0]);
        p.setVelocity(v);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Player p = ExitTheDungeon.getPlayer();
        System.out.println(e.getButton());
        switch (e.getButton()) {
            case 1 -> {
                Ball b = new Ball(p.getX() - Ball.SIZE/2, p.getY() - Ball.SIZE/2, p, p.getDirection().multiply(3));
                ExitTheDungeon.getBoard().addEntity(b);
            }
            case 3 -> {
                Vector v = new Vector(p.getX(), p.getY(), e.getX(), e.getY());
                p.rotate(v.normalize());
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Player p = ExitTheDungeon.getPlayer();
        if (SwingUtilities.isRightMouseButton(e)) {
            Vector v = new Vector(p.getX(), p.getY(), e.getX(), e.getY());
            p.rotate(v.normalize());
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
