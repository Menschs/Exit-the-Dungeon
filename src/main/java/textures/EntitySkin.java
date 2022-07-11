package textures;

public class EntitySkin extends Skin {

    private boolean idle, walking;
    private EntityDirection direction = EntityDirection.left;

    public EntitySkin(String texture) {
        super(texture);
        setIdle(true);
    }

    public void updateState() {
        if(isIdle()) setState(getIdle() + direction);
        if(isWalking()) setState(getWalking() + direction);
    }

    public void attack() {
        playOnce("attack_" + direction);
    }

    public EntityDirection getDirection() {
        return direction;
    }

    public void setDirection(EntityDirection direction) {
        this.direction = direction;
        updateState();
    }

    public String getIdle() {
        return (isIdle()) ? "idle_" : "";
    }

    public boolean isIdle() {
        return idle;
    }

    public void setIdle(boolean idle) {
        this.idle = idle;
        walking = !idle;
        updateState();
    }

    public String getWalking() {
        return (isWalking()) ? "walk_" : "";
    }

    public boolean isWalking() {
        return walking;
    }

    public void setWalking(boolean walking) {
        this.walking = walking;
        idle = !walking;
        updateState();
    }
}
