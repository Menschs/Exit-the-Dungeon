package objects;

import Backend.ObjectData;
import main.ExitTheDungeon;
import textures.Texture;
import textures.TextureType;

import java.util.ArrayList;
import java.util.List;

import static main.ExitTheDungeon.getInstance;
import static main.ExitTheDungeon.getPlayer;

public class Ground {

    private static final Texture tex = Texture.getTextureObject(TextureType.ground_skin.tex("snow"));
    private static final List<Integer> OIDs = new ArrayList<>();

    public static void paintGround() {
        if(OIDs.isEmpty()) {
            for (int x = - 5*5; x < 5*5; x++) {
                for (int y = -2*5; y < 2*5; y++) {
                    ObjectData data = new ObjectData();
                    data.data[0] = x/5.0f;
                    data.data[1] = y/5.0f;
                    data.data[2] = 0.2f;
                    data.data[3] = 0.2f;
                    data.data[4] = 0;
                    OIDs.add(getInstance().addObject(data, tex.get("default")));
                }
            }
        } else {
            int index = 0;
            for (int x = - 5*5; x < 5*5; x++) {
                for (int y = -2*5; y < 2*5; y++) {
                    getInstance().updateObjectPosition(OIDs.get(index), (float) (getPlayer().getX() + x/5.0), (float) (getPlayer().getY() + y/5.0));
                    index++;
                }
            }
        }
    }
}
