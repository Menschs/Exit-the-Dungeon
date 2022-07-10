package objects.elements;

import objects.interfaces.Damaging;
import objects.elements.interfaces.Element;
import objects.hitboxes.Collider;
import objects.hitboxes.Hitbox;
import objects.hitboxes.HitboxAction;
import objects.entities.Ball;
import textures.Skin;

import java.awt.*;

public class Bomb implements Element, Damaging {

    private final float x, y;
    private static final int SIZE = 16;

    private static final float DAMAGE = 20;

    private Hitbox hitbox;

    private Skin skin = new Skin("");

    private boolean damaged = false;

    public Bomb(float x, float y) {
        this.x = x - SIZE/2;
        this.y = y - SIZE/2;
        //hitbox();
    }

    //public void hitbox() {
    //    hitbox = new Hitbox(x, y, SIZE, SIZE, this, new HitboxAction() {
    //        @Override
    //        public void hit(Collider c) {
    //            if(damaged) return;
    //            if(c.getEntity() != null && !(c.getEntity() instanceof Ball)) {
    //                c.getEntity().damage(DAMAGE);
    //                damaged = true;
    //                hitbox.remove();
    //                remove();
    //            }
    //        }
    //    });
    //}

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
    public void paint(Graphics2D g) {
        g.setColor(Color.BLACK);
        //g.fillOval(x, y, SIZE, SIZE);
        //hitbox.paint(g);
    }

    @Override
    public void setDamage(float damage) {

    }

    @Override
    public void getDamage(float damage) {

    }
}
