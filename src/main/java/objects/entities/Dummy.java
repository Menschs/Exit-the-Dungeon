package objects.entities;

import Backend.Drawer;
import inventory.items.ItemStack;
import inventory.items.Itemtype;
import inventory.items.Material;
import inventory.items.Rarity;
import inventory.items.items.LongSword;
import inventory.items.items.Sword;
import main.ExitTheDungeon;
import objects.entities.interfaces.AIMovement;
import objects.hitboxes.CollisionResult;
import org.lwjgl.glfw.GLFW;
import textures.EntityDirection;
import textures.EntitySkin;
import textures.Skin;
import textures.TextureType;
import util.Debugger;
import util.Point;
import objects.entities.interfaces.Entity;
import objects.hitboxes.Collider;
import objects.hitboxes.Hitbox;
import util.Colors;
import util.Vector;

import java.awt.*;
import java.util.List;

public class Dummy implements Entity, AIMovement {

    private float x, y;

    private static final int SIZE = 30;

    private float health = 20;
    private Color c = Colors.gold.getColor();
    private final Hitbox hitbox;
    private float range;

    private final EntitySkin skin = new EntitySkin(TextureType.player_skin.tex("slime"));

    private int damagedSeconds = 0;
    private final ItemStack[] loot = {new Sword(Rarity.uncommon), new LongSword(Rarity.legendary)};

    private Vector velocity = Vector.getNullVector();

    public Dummy(float x, float y) {
        this.x = x;
        this.y = y;
        this.hitbox = new Hitbox((int) x, (int) y, skin.getScaleX(), skin.getScaleY(), this, null);
        skin.move(x, y);
        skin.finish();
        createEntity();
        //new Thread(new Runnable() {
        //    @Override
        //    public void run() {
        //        while(health > 0 && !GLFW.glfwWindowShouldClose(Drawer.getWindow())) {
        //            damagedSeconds--;
        //            try {
        //                Thread.sleep(1000);
        //            } catch (InterruptedException ignored) {
        //            }
        //        }
        //    }
        //}).start();
    }

    @Override
    public void damage(float damage) {
        if(health <= 0) return;
        health -= damage;
        //System.out.println(this + "  "  + health);
        damagedSeconds = 1;
        if(health <= 0) kill();
    }

    @Override
    public void heal(float heal) {
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

    boolean moved = false;

    @Override
    public void move(float x, float y) {
        if(x == 0.0 && y == 0.0) {
            skin.setIdle(true);
        } else {
            skin.setWalking(true);
        }
        if(x == 0 && y == 0) return;
        if(x < 0) skin.setDirection(EntityDirection.left);
        if(x > 0) skin.setDirection(EntityDirection.right);
        CollisionResult result = hitbox.wouldCollide(new Point(this.x + x, this.y + y));
        List<Collider> connect = result.collider();
        if(!result.collisionX()) this.x += x;
        if(!result.collisionY()) this.y += y;
        moved = (!result.collisionX() && !result.collisionY());
        skin.move(this.x, this.y);
        hitbox.move(this.x,this.y);
        if(!connect.isEmpty()) {
            connect.forEach(hitbox::collide);
        }
    }

    @Override
    public void rotate(float rotation) {
    }

    @Override
    public void rotate(Vector v) {

    }

    @Override
    public void setRangedVelocity(Vector v, float range) {
        this.range = range;
        setVelocity(v);
    }

    @Override
    public void removeRange(float distanceTraveled) {
        range -= distanceTraveled;
    }

    @Override
    public float getRange() {
        return range;
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
    public Skin getSkin() {
        return skin;
    }

    @Override
    public float getX() {
        return x;
    }

    @Override
    public float getY() {
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

    @Override
    public void move() {
        setVelocity(find(x, y));
    }
}
