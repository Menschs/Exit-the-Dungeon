package objects.entities;

import objects.entities.interfaces.Entity;
import objects.entities.interfaces.Permeable;
import objects.hitboxes.Collider;
import objects.hitboxes.Hitbox;
import objects.hitboxes.HitboxAction;
import textures.Skin;
import textures.Texture;
import textures.TextureType;
import util.Vector;

import java.awt.*;

public class Ball implements Entity {

    public static final int SIZE = 15;
    private static final double DAMAGE = 5;

    private final Skin skin = new Skin(TextureType.entity_skin.tex("ball.default"));

    private double x = 0;
    private double y = 0;

    private Vector velocity;

    private final boolean[] damaged = {false};

    private final Hitbox hitbox;
    private final Entity shooter;

    public Ball(double x, double y, Entity shooter, Vector velocity) {
        this.x = x;
        this.y = y;
        this.velocity = velocity;
        this.shooter = shooter;
        hitbox = new Hitbox((int) x, (int) y, SIZE, SIZE, this, new HitboxAction() {
            @Override
            public void hit(Collider c) {
                if(damaged[0]) return;
                if(c.getObject() != null) kill();
                if(c.getEntity() != null && !(c.getEntity() instanceof Ball) && shooter != c.getEntity()) {
                    if(c.getEntity() instanceof Permeable) return;
                    c.getEntity().damage(DAMAGE);
                    damaged[0] = true;
                    kill();
                }
            }
        });
        skin.finish();
        createEntity();
    }

    @Override
    public void move(double x, double y) {
        if(x == 0 && y == 0) kill();
        this.x += x;
        this.y += y;
        skin.move(this.x, this.y);
        hitbox.move((int)this.x,(int) this.y);
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
        return null;
    }

    @Override
    public Vector getVelocity() {
        return velocity;
    }

    @Override
    public Vector getDirection() {
        return null;
    }

    @Override
    public void paint(Graphics2D g) {
        g.setColor(Color.BLUE);
        g.fillOval((int) x, (int) y, SIZE, SIZE);
        //hitbox.paint(g);
    }

    @Override
    public void damage(double damage) {

    }

    @Override
    public void heal(double heal) {

    }

    @Override
    public void kill() {
        removeEntity();
    }
}
