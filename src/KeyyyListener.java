import entities.Bomb;
import entities.Player;
import util.Vector;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class KeyyyListener implements KeyListener {

    private final Drawboard board;
    private final List<String> pressed = new ArrayList<>();

    public KeyyyListener(Drawboard board) {
        this.board = board;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Player p = ExitTheDungeon.getPlayer();
        if(!pressed.contains(e.getKeyChar() + "")) pressed.add(e.getKeyChar() + "");
        pressed.forEach(character -> {
            switch (character) {
                case "q" -> p.rotate(5);
                case "e" -> p.rotate(-5);
                case "w" -> p.move(new Vector(0, -5));
                case "s" -> p.move(new Vector(0, 5));
                case "a" -> p.move(new Vector(-5, 0));
                case "d" -> p.move(new Vector(5, 0));
                case "b" -> ExitTheDungeon.getBoard().addObject(new Bomb((int) p.getX(), (int) p.getY()));
            }
        });
        board.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressed.remove(e.getKeyChar() + "");
    }
}
