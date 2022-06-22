package util;

import entities.Ball;
import entities.Bomb;
import entities.Player;
import entities.Updating;
import main.ExitTheDungeon;
import util.Vector;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class KeyyyListener implements KeyListener, MouseListener,  Updating {

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
        if(!pressed.contains(e.getKeyChar() + "")) pressed.add(e.getKeyChar() + "");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressed.remove(e.getKeyChar() + "");
    }

    @Override
    public void tick() {
        Player p = ExitTheDungeon.getPlayer();
        Vector v = new Vector(0, 0);
        List<String> remove = new ArrayList<>();
        pressed.forEach(character -> {
            switch (character) {
                case "q" -> p.rotate(5);
                case "e" -> p.rotate(-5);
                case "w" -> v.subtract(0, 5);
                case "s" -> v.add(0, 5);
                case "a" -> v.subtract(5, 0);
                case "d" -> v.add(5, 0);
                case "b" -> {
                    ExitTheDungeon.getBoard().addObject(new Bomb((int) p.getX(), (int) p.getY()));
                    remove.add("b");
                }
            }
        });
        pressed.removeAll(remove);
        p.setVelocity(v);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Player p = ExitTheDungeon.getPlayer();
        Ball b = new Ball(p.getX(), p.getY(), p, p.getDirection().multiply(0.5));
        ExitTheDungeon.getBoard().addEntity(b);
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
}
