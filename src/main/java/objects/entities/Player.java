package objects.entities;

import inventory.inventory.Inventory;
import inventory.inventory.InventoryView;
import inventory.inventory.Inventoryholder;
import inventory.items.ItemStack;
import inventory.items.Rarity;
import inventory.items.items.Sword;
import main.ExitTheDungeon;
import objects.entities.interfaces.Entity;
import objects.entities.interfaces.effects.StatusEffect;
import objects.entities.interfaces.effects.StatusEffects;
import objects.hitboxes.Collider;
import objects.hitboxes.Hitbox;
import objects.hitboxes.HitboxAction;
import textures.Skin;
import textures.TextureType;
import util.Point;
import org.jetbrains.annotations.NotNull;
import util.Colors;
import util.Vector;

import java.awt.*;
import java.util.HashMap;

public class Player implements Entity, Inventoryholder {

    private final static int[] modelX = new int[] {30, 0, 60};
    private final static int[] modelY = new int[] {0, 60, 60};

    private final Skin skin = new Skin(TextureType.player_skin.tex("hat"));

    private final HashMap<StatusEffects, StatusEffect> effects = new HashMap<>();

    private Color c = Color.LIGHT_GRAY;

    private double x = 0, y = 0;
    private final double width, height;
    private double rotation = 0;
    private double lastRot = -600;

    private int[][] model;

    private final int max_health = 100;
    private int health = max_health;

    private final Hitbox hitbox;
    private final Inventory inventory = new Inventory(this, "Ahmeds Inventar");
    private final InventoryView invV = new InventoryView(inventory);

    private InventoryView openedInventory = invV;

    private boolean openInv;

    private Vector velocity = new Vector(0, 0);

    public Player(double x, double y, int rotation) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        rotate(180);
        create();
        inventory.addItem(new Sword(Rarity.rare));
        skin.finish();
        width = skin.getScaleX();
        height = skin.getScaleY();
        hitbox = new Hitbox(0, 0, width, height, this, new HitboxAction() {
            @Override
            public void hit(Collider c) {
            }
        });
        skin.move(x, y);
    }

    boolean moved = false;

    @Override
    public void move(double x, double y) {
        hitbox.move(this.x,this.y);
        if(x == 0.0 && y == 0.0) {
            skin.pauseAnimation("left", "right");
        } else {
            skin.unpauseAnimation("left", "right");
        }
        if(x == 0 && y == 0) return;
        Collider connect = hitbox.wouldCollide(new Point(this.x + x, this.y + y));
        if(connect == null || connect.getObject() == null || !connect.getObject().isBarrier()) {
            this.x += x;
            this.y += y;
            moved = true;
            skin.move(this.x, this.y);
            ExitTheDungeon.getInstance().setCameraPos((float) (this.x - skin.getScaleX()/2), (float) (this.y - skin.getScaleY()/2));
        } else {
            if(moved) {
                hitbox.collide(connect);
                moved = false;
            }
        }
    }

    @Override
    public void rotate(double rotation) {
        this.rotation += rotation * Math.PI/180;
        double angle = getDirection().angle(new Vector(0, 1)) / Math.PI * 180;
        if(!(angle + "").equals("NaN")) {
            if(angle > 0 && angle < 180) {
                skin.setState("right");
            } else if(angle != 0 && angle != 180){
                skin.setState("left");
            }
        }
    }

    public double getRotationDegrees() {
        return  rotation / Math.PI * 180;
    }

    double rotChange = 0;

    @Override
    public void rotate(Vector v) {
        if(v.equals(getDirection())) return;

        double angle = v.angle(new Vector(0, 1));

        //System.out.println(angle);

        rotation = angle;
        rotate(0);
        rotChange = angle;
    }

    @Override
    public void addVelocity(Vector v) {
        this.velocity.add(v);
    }

    @Override
    public void setVelocity(Vector v) {
        velocity = v;
    }

    @Override
    public Vector getVelocity() {
        return velocity;
    }

    @Override
    public Vector getDirection() {
        double[] rot = rotate(0, -1, rotation);
        return new Vector(rot[0], rot[1]);
    }

    @Override
    public void paint(Graphics2D g) {
        //if(model == null || lastRot != rotation) rotate(0);
        g.setColor(c);
        int[][] tempModel = new int[2][3];
        for (int i = 0; i < model[0].length; i++) {
            tempModel[0][i] = (int) (model[0][i] + x);
            tempModel[1][i] = (int) (model[1][i] + y);
        }
        g.drawPolygon(tempModel[0], tempModel[1], 3);
        g.setColor(Colors.dark_red.getColor());
        int x = ExitTheDungeon.getFrame().getWidth()/2 - 125;
        int y = ExitTheDungeon.getFrame().getHeight() - 70;
        g.fillRoundRect(x, y, 250, 10, 5, 5);
        g.setColor(Color.red);
        double percentage = (health+0.0)/(max_health+0.0);
        g.fillRoundRect(x, y, (int) (250 * percentage), 10, 5, 5);
        //hitbox.paint(g);
        if(openInv) openedInventory.paint(g);
    }

    public void openInventory() {
        openedInventory = invV;
        openInv = true;
    }

    public void openInventory(InventoryView inv) {
        if(inv.hashCode() == openedInventory.hashCode()) return;
        openedInventory = inv;
        openInv = true;
    }

    public void setOpenedInventory(InventoryView inv) {
        if(inv.hashCode() == openedInventory.hashCode()) return;
        openedInventory = inv;
    }

    public void closeInventory() {
        openInv = false;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public boolean hasOpenInventory() {
        return openInv;
    }

    public InventoryView getOpenedInventory() {
        return openedInventory;
    }

    public static double[] rotate(double x, double y, double theta) {
        var sinTheta = Math.sin(theta);
        var cosTheta = Math.cos(theta);
        return rotate(x, y, sinTheta, cosTheta);
    }

    public static double[] rotate(double x, double y, double sinTheta, double cosTheta) {
        double nx = (x * cosTheta - y * sinTheta);
        double ny = (y * cosTheta + x * sinTheta);
        return new double[] {nx, ny};
    }

    public int[][] rotate(int[] toRX, int[] toRY) {
        int[][] result = new int[2][toRX.length];
        var sinTheta = Math.sin(rotation);
        var cosTheta = Math.cos(rotation);
        for (int j = 0; j < toRX.length; j++) {
            var x = toRX[j] - 30;
            var y = toRY[j] - 30;
            double[] rot = rotate(x, y, sinTheta, cosTheta);
            result[0][j] = (int) (rot[0]);
            result[1][j] = (int) (rot[1]);
        }
        return result;
    }

    public int[][] rotate(int[] toRX, int[] toRY, double theta) {
        int[][] result = new int[2][toRX.length];
        var sinTheta = Math.sin(theta);
        var cosTheta = Math.cos(theta);
        for (int j = 0; j < toRX.length; j++) {
            var x = toRX[j] - 30;
            var y = toRY[j] - 30;
            double[] rot = rotate(x, y, sinTheta, cosTheta);
            result[0][j] = (int) (rot[0]);
            result[1][j] = (int) (rot[1]);
        }
        return result;
    }

    public void dropItem(@NotNull ItemStack i) {
        Vector v = getDirection().normalize().multiply(60);
        i.drop((int) (x + v.getX()), (int) (y + v.getY()));
    }

    public void dropItem(int index) {
        ItemStack i = inventory.removeItem(index);
        if(i == null) return;
        dropItem(i);
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

    public double getRotation() {
        return rotation;
    }

    @Override
    public void damage(double damage) {
        health -= damage;
        if(health <= 0) kill();
    }

    @Override
    public void heal(double heal) {
        if(max_health < health + heal) return;
        health += heal;
        if(health > 0) c = Color.black;
    }

    @Override
    public void kill() {
        c = Color.red;
    }

    @Override
    public HashMap<StatusEffects, StatusEffect> getEffects() {
        return effects;
    }

    public Skin getSkin() {
        return skin;
    }
}
