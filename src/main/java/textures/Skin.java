package textures;

import Backend.ObjectData;
import main.ExitTheDungeon;

public class Skin implements Subscriber {

    private final Texture t;
    private final ObjectData data = new ObjectData();
    private final int OID;
    private String state;

    public Skin(String texture) {
        t = Texture.getTextureObject(texture);
        state = t.getStates().get(0);
        data.data[2] = 1;
        data.data[3] = 1;
        OID = ExitTheDungeon.getInstance().addObject(data, t.get("left"));
        if(t.isAnimated()) t.subscribe(this);
    }

    @Override
    public void update(int newIndex) {
        System.out.println(newIndex);
        ExitTheDungeon.getInstance().setObjectTextureIndex(OID, newIndex);
    }

    @Override
    public String getState() {
        return state;
    }

    public void move(double x, double y) {
        data.data[0] = (float) x;
        data.data[1] = (float) y;
    }

    public void setState(String state) {
        if(!t.getStates().contains(state)) return;
        this.state = state;
        //update object
    }
}
