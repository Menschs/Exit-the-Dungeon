package entities;

public interface Damageable {

    void damage(double damage);
    void heal(double heal);

    void kill();
}
