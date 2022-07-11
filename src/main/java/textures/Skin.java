package textures;

import Backend.ObjectData;
import main.ExitTheDungeon;
import util.Debugger;

import java.util.ArrayList;
import java.util.List;

public abstract class Skin implements Subscriber {

    private static final List<Skin> skins = new ArrayList<>();

    String name;
    protected Texture t;
    private final ObjectData data = new ObjectData();
    private int OID;
    protected String state;
    private float a;
    private float theta = 0;
    private boolean playingAnimation = false;

    private Thread animation;

    private final List<String> pausedStates = new ArrayList<>();

    public Skin(String texture) {
        name = texture;
        t = Texture.getTextureObject(name);
        state = t.getStates().get(0);
        a = t.getScalingX() / t.getScalingY();
        data.data[0] = 400;
        data.data[1] = 400;
        skins.add(this);
    }

    public void reload() {
        t = Texture.getTextureObject(name);
        state = (t.getStates().contains(state)) ? state : t.getStates().get(0);
        a = t.getScalingX() / t.getScalingY();
        ExitTheDungeon.getInstance().setObjectTextureIndex(OID, t.get(state));
        if(t.isAnimated()) t.subscribe(this);
    }

    public static void reloadAll() {
        skins.forEach(Skin::reload);
    }

    public void finish() {
        if(data.data[2] == 0) data.data[2] = t.getScalingX();
        if(data.data[3] == 0) data.data[3] = t.getScalingY();
        data.data[4] = 1;
        //data.data[5] = (float) (60 * Math.PI/180);
        OID = ExitTheDungeon.getInstance().addObject(data, t.get(state));
        if(t.isAnimated()) t.subscribe(this);
    }

    public void rotate(float theta) {
        this.theta = theta;
        ExitTheDungeon.getInstance().setObjectRotation(OID, (float) theta);
    }

    public void scaleX(float x) {
        data.data[2] = x;
        data.data[3] = x / a;
    }

    public void scaleY(float y) {
        data.data[2] = y / a;
        data.data[3] = y;
    }

    public void scale(float x, float y) {
        data.data[2] = x;
        data.data[3] = y;
    }

    public float getScaleX() {
        return data.data[2];
    }

    public float getScaleY() {
        return data.data[3];
    }

    @Override
    public void updateSubscriber(int newIndex) {
        if(pausedStates.contains(getState()) || playingAnimation) return;
        update(newIndex);
    }

    public void update(int newIndex) {
        ExitTheDungeon.getInstance().setObjectTextureIndex(OID, newIndex);
    }

    @Override
    public String getState() {
        return state;
    }

    public void move(float x, float y) {
        if(OID != 0) ExitTheDungeon.getInstance().updateObjectPosition(OID, (float) (x + t.getOffsetX() - getScaleX()/2), (float) (y + t.getOffsetY() - getScaleY()/2));
        else {
            data.data[0] = (float) (x - ((t.getType() != TextureType.pointer_skin) ? getScaleX()/2 : 0 ));
            data.data[1] = (float) (y - ((t.getType() != TextureType.pointer_skin) ? getScaleY()/2 : 0 ));
        }
    }

    public void setState(String state) {
        if(!t.getStates().contains(state) || this.state.equals(state)) return;
        if(pausedStates.contains(state)) update(t.get(state));
        this.state = state;
        //updateSubscriber object
    }

    public void pauseAnimation(String... states) {
        for (String s : states) {
            if(t.getStates().contains(state) && !pausedStates.contains(s)) pausedStates.add(s);
        }
    }

    public void unpauseAnimation(String... states) {
        for (String s : states) {
            if(pausedStates.contains(state)) pausedStates.remove(s);
        }
    }

    public void remove() {
        ExitTheDungeon.getInstance().disableFreeObject(OID);
        skins.remove(this);
    }

    public void playOnce(String state) {
        if(!t.getStates().contains(state)) return;
        playingAnimation = true;
        if(animation != null) animation.stop();
        final List<Integer> anim = t.getAnimation(state);
        animation = new Thread(new Runnable() {
            @Override
            public void run() {
                for (Integer integer : anim) {
                    update(t.getTexture(integer));
                    try {
                        Thread.sleep(t.getDelay());
                    } catch (InterruptedException ignored) {}
                }
                playingAnimation = false;
            }
        });
        animation.start();
    }

    public Texture getTexture() {
        return t;
    }
}
