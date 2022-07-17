package objects.entities;

import objects.entities.interfaces.Entity;
import objects.entities.interfaces.Permeable;
import objects.hitboxes.Collider;
import objects.hitboxes.Hitbox;
import objects.hitboxes.HitboxAction;
import textures.EntitySkin;
import textures.Skin;
import textures.Texture;
import textures.TextureType;
import util.Debugger;
import util.Vector;

import java.awt.*;

public class Ball implements Entity {

    public static final int SIZE = 15;
    private static final float DAMAGE = 5;

    private final EntitySkin skin = new EntitySkin(TextureType.entity_skin.tex("ball"));

    private float x;
    private float y;
    private float range;

    private Vector velocity;

    private final boolean[] damaged = {false};

    private final Hitbox hitbox;
    private final Entity shooter;

    public Ball(float x, float y, Entity shooter, Vector velocity, float range) {
        this.x = x;
        this.y = y;
        setRangedVelocity(velocity, range);
        this.shooter = shooter;
        hitbox = new Hitbox(x, y, skin.getScaleX(), skin.getScaleY(), this, new HitboxAction() {
            @Override
            public void hit(Collider c) {
                if(damaged[0]) return;
                if(c.getHolder() instanceof Entity e) {
                    if(!(e instanceof Ball && shooter != e)) {
                        if(e instanceof Permeable) return;
                        e.damage(DAMAGE);
                        damaged[0] = true;
                        kill();
                    }
                } else {
                    kill();
                }
            }
        });
        skin.finish();
        createEntity();
    }

    @Override
    public void move(float x, float y) {
        if(x == 0 && y == 0) kill();
        this.x += x;
        this.y += y;
        skin.move(this.x, this.y);
        hitbox.move((int)this.x,(int) this.y);
    }

    @Override
    public void rotate(float rotation) {

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
        return null;
    }

    @Override
    public void paint(Graphics2D g) {
        g.setColor(Color.BLUE);
        g.fillOval((int) x, (int) y, SIZE, SIZE);
        //hitbox.paint(g);
    }

    @Override
    public void damage(float damage) {

    }

    @Override
    public void heal(float heal) {

    }

    @Override
    public void kill() {
        removeEntity();
    }
}
