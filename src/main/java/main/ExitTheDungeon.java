package main;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;
import objects.entities.Player;
import objects.Updating;
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

    private static boolean runCallback = true;

    public static void main(String[] args) {
        //DiscordRPC.discordUpdatePresence(new DiscordRichPresence.Builder("awerrqc").setDetails("abasdfasd").setBigImage("test", "lol").build());
        discord();
        lorenzWindow();
        tick();
    }

    public static void discord() {
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(new ReadyCallback() {
            @Override
            public void apply(DiscordUser discordUser) {
                System.out.println("Welcome " + discordUser.username + "#" + discordUser.discriminator + "!");
                update("Booting up...", "");
            }
        }).build();
        DiscordRPC.discordInitialize("989883971598966803", handlers, false);
        DiscordRPC.discordRegister("989883971598966803", "");
        new Thread("Discord RPC Callback") {

            @Override
            public void run() {
                while(runCallback) {
                    DiscordRPC.discordRunCallbacks();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public static void update(String first, String second) {
        DiscordRichPresence.Builder b = new DiscordRichPresence.Builder(second);
        b.setBigImage("test", "");
        b.setDetails(first);
        DiscordRPC.discordUpdatePresence(b.build());
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

        frame = new JFrame("Exit the Dungeon");
        frame.setVisible(true);
        frame.setSize(1000,1000);
        frame.setLocation(2300, 30);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);

        board.addEntity(p);
        frame.add(board);
        KeyyyListener listener = new KeyyyListener(board);
        frame.addKeyListener(listener);
        frame.addMouseListener(listener);
        frame.addMouseMotionListener(listener);
        frame.repaint();
    }

    public static Player getPlayer() {
        return p;
    }

    public static Drawboard getBoard() {
        return board;
    }

    public static JFrame getFrame() {
        return frame;
    }

    public static void setRunCallback(boolean runCallback) {
        ExitTheDungeon.runCallback = runCallback;
    }
}
