package textures;

public class ItemSkin extends Skin {

    private boolean idle;

    public ItemSkin(String texture) {
        super(texture);
        setIdle(true);
    }

    public boolean isIdle() {
        return idle;
    }

    public void setIdle(boolean idle) {
        this.idle = idle;
        setState("idle");
    }

    public void attack() {
        playOnce("attack");
    }
}
