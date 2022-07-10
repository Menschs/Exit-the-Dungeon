package main;

import Backend.Drawer;
import Backend.Game;
import Backend.ObjectData;
import inventory.items.HeldItem;
import inventory.items.Rarity;
import inventory.items.items.Sword;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import objects.Ground;
import objects.elements.Wall;
import objects.entities.Ball;
import objects.entities.Player;
import objects.interfaces.Updating;
import textures.Texture;
import util.Debugger;
import util.Vector;
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
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;

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
    }

    public static void discord() {
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(discordUser -> System.out.println("Welcome " + discordUser.username + "#" + discordUser.discriminator + "!")).build();
        DiscordRPC.discordInitialize("989883971598966803", handlers, false);
        DiscordRPC.discordRegister("989883971598966803", "");
        update("lol" , "");
        new Thread("Discord RPC Callback") {
            @Override
            public void run() {
                while(!glfwWindowShouldClose(Drawer.getWindow())) {
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
        b.setBigImage("icon", "");
        b.setDetails(first);
        b.setStartTimestamps(rp.startTimestamp);
        rp = b.build();
        DiscordRPC.discordUpdatePresence(rp);
    }

    public static void resumeGame() {
        gaming = true;
        if(!started) {
            addHUD(HUDs.status, new StatusHUD());
            started = true;
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

    private final List<String> pressed = new ArrayList<>();

    private boolean isHolding(long key) {
        return pressed.contains("k" + key);
    }

    private void hold(long key) {
        if(!pressed.contains("k" + key)) pressed.add("k" + key);
    }

    private void releaseKey(long key) {
        pressed.remove("k"  + key);
    }

    private boolean isHoldingMouse(String key) {
        return pressed.contains(key);
    }

    private void holdMouse(String key) {
        if(!pressed.contains(key)) pressed.add(key);
    }

    private void releaseMouse(String key) {
        pressed.remove(key);
    }

    @Override
    public void runGameUpdate() {
        float multiply = 0;
        float rotation = 0;

        for (Long key : getPressed()) {
            switch (Math.toIntExact(key)) {
                case GLFW_KEY_W -> multiply = 0.25f;
                case GLFW_KEY_S -> {
                    rotation = 180;
                    multiply = 0.25f;
                }
                case GLFW_KEY_D -> {
                    p.getSkin().setState("right");
                    rotation = -90;
                    multiply = 0.25f;
                }
                case GLFW_KEY_A -> {
                    p.getSkin().setState("left");
                    rotation = 90;
                    multiply = 0.25f;
                }
                case GLFW_KEY_T -> {
                    if(!isHolding(GLFW_KEY_T)) new Wall(p.getX() + 0.5f, p.getY() + 0.5f, 30, 30);
                }
                case GLFW_KEY_LEFT_SHIFT -> {
                    if(!isHolding(GLFW_KEY_LEFT_SHIFT)) {
                        if(p.getVelocity().lengthSquared() == 0 || isMousePressed(GLFW_MOUSE_BUTTON_RIGHT)) p.setVelocity(p.getDirection().multiply(1.25f));
                        else p.setVelocity(p.getVelocity().normalize().multiply(1.25f));
                    }
                }
                case GLFW_KEY_F3 -> {
                    if(!isHolding(GLFW_KEY_F3)) Texture.reload();
                }
                case GLFW_KEY_ESCAPE -> {
                    endGame();
                }
            }
            hold(key);
        }

        for (int i = 0; i < pressed.size(); i++) {
            try {
                long key = Long.parseLong(pressed.get(i).replace("k", ""));
                if (!isKeyPressed((int) key)) releaseKey(key);
            } catch (NumberFormatException nx) {
                continue;
            }
        }

        if(!isKeyPressed(GLFW_KEY_D) && !isKeyPressed(GLFW_KEY_A)) {
            p.rotate(0);
        }

        if(isKeyPressed(GLFW_KEY_W)) {
            if(isKeyPressed(GLFW_KEY_A)) rotation = 45;
            if(isKeyPressed(GLFW_KEY_D)) rotation = -45;
        }
        if(isKeyPressed(GLFW_KEY_S)) {
            if(isKeyPressed(GLFW_KEY_A)) rotation = 135;
            if(isKeyPressed(GLFW_KEY_D)) rotation = -135;
        }

        if(isMousePressed(GLFW_MOUSE_BUTTON_LEFT)) {
            p.rotate(0);
            if(p.hasOpenInventory())  {
                p.getOpenedInventory().click((int) getMouseX(), (int) getMouseY());
            } else {
                if(!isHoldingMouse("left") && p.getItemInHand() != null) {
                    Vector v = new Vector(p.getX(), p.getY(), (float) getMouseWorldX(), (float) getMouseWorldY());
                    Texture t = p.getItemInHand().getSkin().getTexture();
                    new Ball(p.getX() + t.getOffsetX(), p.getY() + t.getOffsetY(), p, p.getDirection().multiply(3));
                    p.setVelocity(p.getDirection().multiply(-0.1f));
                    ExitTheDungeon.update("throwing a Ball..." , "");
                    holdMouse("left");
                }
            }
        } else {
            releaseMouse("left");
        }
        if(isMousePressed(GLFW_MOUSE_BUTTON_RIGHT)) {
            Vector v = new Vector(p.getX(), p.getY(), (float) getMouseWorldX(), (float) getMouseWorldY());
            p.rotate(v.normalize());
        }
        Vector v = new Vector(0, 1);
        v.rotateByDegrees(rotation);
        v.normalize().multiply(multiply);
        if(v.lengthSquared() != 0) p.setStaticVelocity(v);

            Updating.update((float) deltaTime);
            Updating.clear();
    }

    @Override
    public void init() {
        Texture.loadTextures();
        Ground.paintGround();
        p  = new Player(0, 0, 0);
        p.setItemInHand(new HeldItem(new Sword(Rarity.legendary)));
        discord();
    }

    public static ExitTheDungeon getInstance() {
        return instance;
    }
}
