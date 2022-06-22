package entities;

import java.awt.*;

public class Bomb implements Object{

    private final int x, y;
    private static final int SIZE = 16;

    private final RoundHitbox hitbox;

    public Bomb(int x, int y) {
        this.x = x - SIZE/2;
        this.y = y - SIZE/2;
        hitbox = new RoundHitbox(x, y, SIZE, null, new HitboxAction() {
            @Override
            public void hit(Collider c) {
                if(c.getEntity() instanceof Ball) {
                    c.getEntity().kill();
                }
            }
        });
    }

    @Override
    public void paint(Graphics2D g) {
        g.setColor(Color.red);
        g.fillOval(x, y, SIZE, SIZE);
    }
}
