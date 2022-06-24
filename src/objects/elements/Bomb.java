package objects.elements;

import objects.Damaging;
import objects.hitboxes.Collider;
import objects.hitboxes.Hitbox;
import objects.hitboxes.HitboxAction;
import objects.entities.Ball;

import java.awt.*;

public class Bomb implements Element, Damaging {

    private final int x, y;
    private static final int SIZE = 16;

    private static final double DAMAGE = 20;

    private Hitbox hitbox;

    public Bomb(int x, int y) {
        this.x = x - SIZE/2;
        this.y = y - SIZE/2;
        hitbox();
    }

    public void hitbox() {
        hitbox = new Hitbox(x, y, SIZE, SIZE, this, new HitboxAction() {
            @Override
            public void hit(Collider c) {
                if(c.getEntity() != null && !(c.getEntity() instanceof Ball)) {
                    c.getEntity().damage(DAMAGE);
                    hitbox.remove();
                    remove();
                }
            }
        });
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void paint(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fillOval(x, y, SIZE, SIZE);
        hitbox.paint(g);
    }

    @Override
    public void setDamage(double damage) {

    }

    @Override
    public void getDamage(double damage) {

    }
}
