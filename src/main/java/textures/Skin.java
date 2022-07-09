package textures;

import Backend.ObjectData;
import main.ExitTheDungeon;

import java.util.ArrayList;
import java.util.List;

public class Skin implements Subscriber {

    private final Texture t;
    private final ObjectData data = new ObjectData();
    private int OID;
    private String state;
    private float a;


    private final List<String> pausedStates = new ArrayList<>();

    public Skin(String texture) {
        t = Texture.getTextureObject(texture);
        System.out.println(texture + " " + t.get(""));
        state = t.getStates().get(0);
        a = t.getScalingX() / t.getScalingY();
        data.data[0] = 400;
        data.data[1] = 400;
        data.data[2] = t.getScalingX();
        data.data[3] = t.getScalingY();
        data.data[4] = 1;
    }

    public void finish() {
        OID = ExitTheDungeon.getInstance().addObject(data, t.get(state));
        if(t.isAnimated()) t.subscribe(this);
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
        ExitTheDungeon.getInstance().updateObjectPosition(OID, (float) x, (float) y);
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

    public void hide() {
    }
}
