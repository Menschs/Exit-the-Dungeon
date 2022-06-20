import entities.Player;

import javax.swing.*;

public class ExitTheDungeon {

    private static final Player p = new Player(200, 200, 0);
    private static final Drawboard board = new Drawboard();

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame = new JFrame("Pollycloud");
        frame.setVisible(true);
        frame.setSize(1000,1000);
        frame.setLocation(2300, 30);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);

        board.addEntity(p);
        frame.add(board);
        frame.addKeyListener(new KeyyyListener(board));
        frame.repaint();
    }

    public static Player getPlayer() {
        return p;
    }

    public static Drawboard getBoard() {
        return board;
    }
}
