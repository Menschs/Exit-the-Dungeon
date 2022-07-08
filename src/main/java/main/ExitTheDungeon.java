package main;

import Backend.Game;
import Backend.ObjectData;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import objects.entities.Player;
import objects.interfaces.Updating;
import textures.Texture;
import util.frame.gui.Drawboard;
import util.frame.gui.HUDs;
import util.frame.gui.GUI;
import util.frame.gui.KeyyyListener;
import util.frame.gui.guis.StartGUI;
import util.frame.gui.guis.StatusHUD;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.HashMap;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;

public class ExitTheDungeon extends Game {

    public static JFrame frame = new JFrame("Exit the Dungeon");;

    private static Player p;
    private static final Drawboard board = new Drawboard();
    private static DiscordRichPresence rp = new DiscordRichPresence.Builder("starting...").setStartTimestamps(System.currentTimeMillis()/1000).build();

    private static boolean gaming;
    private static boolean started;
    private static final HashMap<HUDs, GUI> huds = new HashMap<>();
    private static GUI gui = new StartGUI();

    private static ExitTheDungeon instance;

    public static void main(String[] args) {
        instance = new ExitTheDungeon();
        instance.runGame();
        //discord();
        //lorenzWindow();
        //tick();
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
        Thread repaint = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    board.repaint();
                    try {
                        Thread.sleep(1000 / 144);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    run();
                } catch (ConcurrentModificationException | StackOverflowError cx) {
                    run();
                }
            }
        });
        repaint.start();
        final int[] ticksPerCurrentSecond = {0};
        final int ticksPerSecond = 60;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(isGaming()) {
                        if(ticksPerCurrentSecond[0] >= ticksPerSecond) ticksPerCurrentSecond[0] = 0;
                        Updating.update(ticksPerCurrentSecond[0]);
                        Updating.clear();
                        ticksPerCurrentSecond[0]++;
                    }
                    try {
                        Thread.sleep(1000 / ticksPerSecond);
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

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);

        setGui(new StartGUI());
        //resumeGame();

        frame.add(board);
        board.addEntity(p);
        //new Thread(new Runnable() {
        //    @Override
        //    public void run() {
        //        board.repaint();
        //    }
        //}).start();
        KeyyyListener listener = new KeyyyListener();
        frame.addKeyListener(listener);
        frame.addMouseListener(listener);
        frame.addMouseMotionListener(listener);

        icon();
    }

    public static void resumeGame() {
        gaming = true;
        if(!started) {
            addHUD(HUDs.status, new StatusHUD());
            started = true;
        }
    }

    public static void icon() {
        try {
            BufferedImage image = ImageIO.read(new File("assets/icon/icon.png"));
            frame.setIconImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isGaming() {
        return gaming;
    }

    public static GUI getGui() {
        return gui;
    }

    public static void setGui(GUI gui) {
        ExitTheDungeon.gui = gui;
        gaming = false;
    }

    public static void addHUD(HUDs id, GUI gui) {
        if(huds.containsKey(id)) return;
        huds.put(id, gui);
    }

    public static void removeHUD(HUDs id) {
        huds.remove(id);
    }

    public static HashMap<HUDs, GUI> getHUDs() {
        return huds;
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

    public static boolean isStarted() {
        return started;
    }

    public static void setStarted(boolean started) {
        ExitTheDungeon.started = started;
    }


    private ObjectData o;
    private int oID;
    private float cx = 0, cy = 0;

    @Override
    public void runGameUpdate() {
        if(isKeyPressed(GLFW_KEY_A)){
            cx -= deltaTime * 4;
        }
        if(isKeyPressed(GLFW_KEY_D)){
            cx += deltaTime * 4;
        }
        if(isKeyPressed(GLFW_KEY_W)){
            cy += deltaTime * 4;
        }
        if(isKeyPressed(GLFW_KEY_S)){
            cy -= deltaTime * 4;
        }
        setCameraPos(cx, cy);
    }

    @Override
    public void init() {
        Texture.loadTextures();
        p  = new Player(200, 200, 0);
    }

    public static ExitTheDungeon getInstance() {
        return instance;
    }
}
