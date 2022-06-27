package util;

import inventory.ItemStack;
import inventory.Itemtype;
import inventory.Material;
import inventory.Rarity;
import objects.entities.*;
import objects.elements.Bomb;
import objects.Updating;
import main.ExitTheDungeon;
import objects.elements.Wall;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class KeyyyListener implements KeyListener, MouseListener, MouseMotionListener,  Updating {

    private final List<String> pressed = new ArrayList<>();

    public KeyyyListener() {
        create();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Point mouse = MouseInfo.getPointerInfo().getLocation();
        mouse.setLocation(mouse.getX() - ExitTheDungeon.getFrame().getX(), mouse.getY() - ExitTheDungeon.getFrame().getY());
        if(!ExitTheDungeon.isGaming()) return;
        String character = (e.getKeyChar() + "").toLowerCase();
        if(!pressed.contains(character)) pressed.add(character);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Player p = ExitTheDungeon.getPlayer();
        String key = e.getKeyChar() + "";
        if(key.equals("p") && ExitTheDungeon.isStarted()) {
            if (ExitTheDungeon.isGaming()) {
                ExitTheDungeon.setGui(new PauseGUI());
            } else {
                ExitTheDungeon.resumeGame();
            }
            return;
        }
        if(!ExitTheDungeon.isGaming()) {
            return;
        }
        Point mouse = MouseInfo.getPointerInfo().getLocation();
        mouse.setLocation(mouse.getX() - ExitTheDungeon.getFrame().getX(), mouse.getY() - ExitTheDungeon.getFrame().getY());
        key = key.toLowerCase();
        if(key.equals("e")) {
            if (p.hasOpenInventory()) p.closeInventory();
                else p.openInventory();
        }
        if(e.getKeyCode() == 27) {
            p.closeInventory();
            return;
        }
        if(p.hasOpenInventory()) {
            switch (key) {
                case "o" -> {
                    p.addItem(new ItemStack(Material.longsword, Itemtype.weapon, Rarity.weird).setAmount(20));
                    return;
                }
                case "p" -> {
                    p.getInventory().removeItem(new ItemStack(Material.longsword, Itemtype.weapon, Rarity.weird).setAmount(3));
                    return;
                }
                case "r" -> {
                    ItemStack i = p.getInventory().getItem(0);
                    i.setRarity(i.getRarity().next());
                    return;
                }
            }
        } else {
            switch (key) {
                case "t" -> {
                    Wall w = new Wall((int) (p.getX() + 100), (int) (p.getY() + 100), 100, 20);
                    ExitTheDungeon.getBoard().addObject(w);
                    return;
                }
                case "g" -> {
                    new Dummy(p.getX(), p.getY());
                    return;
                }
                case "b" -> {
                    Vector v1 = p.getDirection().add(100, 100);
                    ExitTheDungeon.getBoard().addObject(new Bomb((int) (p.getX() + v1.getX()), (int) (p.getY() + v1.getY())));
                    return;
                }
            }
        }
        switch (key) {
            case "q" -> {
                if(p.hasOpenInventory()) {
                    if(!p.getOpenedInventory().drop((int) mouse.getX(), (int) mouse.getY())) {
                        p.dropItem(0);
                    }
                } else {
                    p.dropItem(0);
                }
                return;
            }
            case "l" -> {
                new LootChest((int) p.getX(), (int) p.getY());
                return;
            }
            case "c" -> {
                new Crate((int) p.getX(), (int) p.getY());
                return;
            }
        }
        pressed.remove(key);
    }

    @Override
    public void tick() {
        if(!ExitTheDungeon.isGaming()) return;
        Player p = ExitTheDungeon.getPlayer();
        final double[] multiply = {0};
        final double[] rotation = {0};
        if(!p.hasOpenInventory()) {
            pressed.forEach(character -> {
                switch (character) {
                    case "d" -> p.rotate(2.5);
                    case "a" -> p.rotate(-2.5);
                    case "w" -> multiply[0] = 5;
                    case "s" -> multiply[0] = -5;
                }
            });
        }
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
        if(!ExitTheDungeon.isGaming()) {
            ExitTheDungeon.getGui().click(e.getX(), e.getY());
            return;
        }
        Player p = ExitTheDungeon.getPlayer();
        switch (e.getButton()) {
            case 1 -> {
                if(p.hasOpenInventory())  {
                    p.getOpenedInventory().click(e.getX(), e.getY());
                } else {
                    new Ball(p.getX() - Ball.SIZE/2, p.getY() - Ball.SIZE/2, p, p.getDirection().multiply(3));
                    ExitTheDungeon.update("throwing a Ball..." , "");
                }
            }
            case 3 -> {
                System.out.println(e.getX() + " " + e.getY());
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
