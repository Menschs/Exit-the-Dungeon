package objects.elements;

import objects.interfaces.Damageable;
import objects.elements.interfaces.Element;
import objects.hitboxes.Collider;
import objects.hitboxes.Hitbox;
import objects.hitboxes.HitboxAction;

import java.awt.*;
import java.util.Random;

public class Crate implements Element, Damageable {
    Random r = new Random();
    int x, y, w, h;
    private final Hitbox hitbox;

    public Crate(int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        hitbox = new Hitbox(x, y, w, h, this, new HitboxAction() {
            @Override
            public void hit(Collider c) {
                damage(0);
            }
        });
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public void paint(Graphics2D g) {

    }

    @Override
    public void damage(double damage) {
        kill();
    }

    @Override
    public void heal(double heal) {

    }

    @Override
    public void kill() {
        if(pInInt(5)){
            // Hier dann irgendwas was gedroppt werden soll
        }
        else if(pInInt(10)){

        }
    }

    boolean pInInt(int percent){
        return r.nextInt() % (100/percent) == 0;
    }
}
