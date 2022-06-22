package main;

import entities.Player;
import entities.Updating;
import util.Drawboard;
import util.KeyyyListener;

import javax.swing.*;
import java.util.ConcurrentModificationException;

public class ExitTheDungeon {

    static String last = ""; //testing
    public static JFrame frame;

    private static final Player p = new Player(200, 200, 0);
    private static final Drawboard board = new Drawboard();
    private static boolean tick = true;
    private static Thread t;

    public static void main(String[] args) {
        lorenzWindow();
        tick();
    }

    public static void tick() {
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(!tick) return;
                    Updating.update();
                    board.repaint();
                    try {
                        Thread.sleep(1000/60);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    run();
                } catch (ConcurrentModificationException | StackOverflowError cx) {
                    run();
                }
            }
        });
        t.start();
    }

    public static void lorenzWindow() {

        frame = new JFrame("Pollycloud");
        frame.setVisible(true);
        frame.setSize(1000,1000);
        frame.setLocation(2300, 30);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setResizable(false);

        board.addEntity(p);
        frame.add(board);
        KeyyyListener listener = new KeyyyListener(board);
        frame.addKeyListener(listener);
        frame.addMouseListener(listener);
        frame.repaint();
    }

    public static Player getPlayer() {
        return p;
    }

    public static Drawboard getBoard() {
        return board;
    }
}
