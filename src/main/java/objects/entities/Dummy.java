package objects.entities;

import Backend.Drawer;
import inventory.items.ItemStack;
import inventory.items.Itemtype;
import inventory.items.Material;
import inventory.items.Rarity;
import inventory.items.items.LongSword;
import inventory.items.items.Sword;
import objects.hitboxes.CollisionResult;
import org.lwjgl.glfw.GLFW;
import util.Point;
import objects.entities.interfaces.Entity;
import objects.hitboxes.Collider;
import objects.hitboxes.Hitbox;
import util.Colors;
import util.Vector;

import java.awt.*;

public class Dummy implements Entity {

    private double x, y;

    private static final int SIZE = 30;

    private double health = 20;
    private Color c = Colors.gold.getColor();
    private final Hitbox hitbox;

    private int damagedSeconds = 0;
    private final ItemStack[] loot = {new Sword(Rarity.uncommon), new LongSword(Rarity.legendary)};

    private Vector velocity = new Vector(0, 0);

    public Dummy(double x, double y) {
        this.x = x;
        this.y = y;
        this.hitbox = new Hitbox((int) x, (int) y, SIZE, SIZE, this, null);
        createEntity();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(health > 0 && !GLFW.glfwWindowShouldClose(Drawer.getWindow())) {
                    damagedSeconds--;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        }).start();
    }

    @Override
    public void damage(double damage) {
        if(health <= 0) return;
        health -= damage;
        //System.out.println(this + "  "  + health);
        damagedSeconds = 1;
        if(health <= 0) kill();
    }

    @Override
    public void heal(double heal) {
        health += heal;
    }

    @Override
    public void kill() {
        c = Color.red;
        for (ItemStack itemStack : loot) {
            dropLoot(itemStack);
        }
        removeEntity();
    }

    @Override
    public void move(double x, double y) {
        CollisionResult connect = hitbox.wouldCollide(new Point(this.x + x - SIZE/2, this.y + y - SIZE/2));
        if(!connect.collisionX()) this.x += x;
        if(!connect.collisionY()) this.y += y;
        hitbox.move((int) this.x,(int) this.y);
    }

    @Override
    public void rotate(double rotation) {
    }

    @Override
    public void rotate(Vector v) {

    }

    @Override
    public void addVelocity(Vector v) {
        velocity.add(v);
    }

    @Override
    public void setVelocity(Vector v) {
        velocity = v;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public Hitbox getHitbox() {
        return hitbox;
    }

    @Override
    public Vector getVelocity() {
        return velocity;
    }

    @Override
    public Vector getDirection() {
        return new Vector(0, 0);
    }

    @Override
    public void paint(Graphics2D g) {
        g.setColor((damagedSeconds > 0) ? new Color(255, c.getGreen()/2, c.getBlue()/2) : c);
        g.fillOval((int) x, (int) y, SIZE, SIZE);
        //hitbox.paint(g);
    }
}
