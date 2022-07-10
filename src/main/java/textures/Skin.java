package textures;

import Backend.ObjectData;
import main.ExitTheDungeon;

import java.util.ArrayList;
import java.util.List;

public class Skin implements Subscriber {

    protected final Texture t;
    private final ObjectData data = new ObjectData();
    private int OID;
    protected String state;
    private float a;
    private double theta = 0;


    private final List<String> pausedStates = new ArrayList<>();

    public Skin(String texture) {
        t = Texture.getTextureObject(texture);
        state = t.getStates().get(0);
        a = t.getScalingX() / t.getScalingY();
    }

    public void finish() {
        data.data[0] = 400;
        data.data[1] = 400;
        if(data.data[2] == 0) data.data[2] = t.getScalingX();
        if(data.data[3] == 0) data.data[3] = t.getScalingY();
        data.data[4] = 1;
        //data.data[5] = (float) (60 * Math.PI/180);
        OID = ExitTheDungeon.getInstance().addObject(data, t.get(state));
        if(t.isAnimated()) t.subscribe(this);
    }

    public void rotate(double theta) {
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

    public double getScaleX() {
        return data.data[2];
    }

    public double getScaleY() {
        return data.data[3];
    }

    @Override
    public void updateSubscriber(int newIndex) {
        if(pausedStates.contains(getState())) return;
        update(newIndex);
    }

    public void update(int newIndex) {
        ExitTheDungeon.getInstance().setObjectTextureIndex(OID, newIndex);
    }

    @Override
    public String getState() {
        return state;
    }

    public void move(double x, double y) {
        ExitTheDungeon.getInstance().updateObjectPosition(OID, (float) x + t.getOffsetX(), (float) y + t.getOffsetY());
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
    }

    public Texture getTexture() {
        return t;
    }
}
