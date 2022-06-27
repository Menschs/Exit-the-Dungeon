package main;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import objects.entities.Player;
import objects.Updating;
import util.Drawboard;
import util.KeyyyListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ConcurrentModificationException;

public class ExitTheDungeon {

    public static JFrame frame;

    private static final Player p = new Player(200, 200, 0);
    private static final Drawboard board = new Drawboard();
    private static final boolean tick = true;
    private static DiscordRichPresence rp = new DiscordRichPresence.Builder("starting...").setStartTimestamps(System.currentTimeMillis()/1000).build();

    public static void main(String[] args) {
        discord();
        lorenzWindow();
        tick();
    }

    public static void discord() {
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(discordUser -> System.out.println("Welcome " + discordUser.username + "#" + discordUser.discriminator + "!")).build();
        DiscordRPC.discordInitialize("989883971598966803", handlers, false);
        DiscordRPC.discordRegister("989883971598966803", "");
        update("lol" , "");
        new Thread("Discord RPC Callback") {
            @Override
            public void run() {
                while(true) {
                    DiscordRPC.discordRunCallbacks();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ignored) {}
                }
            }
        }.start();

        Runtime.getRuntime().addShutdownHook(new Thread(DiscordRPC::discordShutdown));
    }

    public static void update(String first, String second) {
        DiscordRichPresence.Builder b = new DiscordRichPresence.Builder(second);
        b.setBigImage("test", "");
        b.setDetails(first);
        b.setStartTimestamps(rp.startTimestamp);
        rp = b.build();
        DiscordRPC.discordUpdatePresence(rp);
    }

    public static void tick() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (!tick) return;
                    Updating.update();
                    Updating.clear();
                    try {
                        Thread.sleep(1000 / 60);
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                board.repaint();
            }
        }).start();
        KeyyyListener listener = new KeyyyListener();
        frame.addKeyListener(listener);
        frame.addMouseListener(listener);
        frame.addMouseMotionListener(listener);

        icon();
    }

    public static void icon() {
        try {
            BufferedImage image = ImageIO.read(new File("assets/icon/icon.png"));
            frame.setIconImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
