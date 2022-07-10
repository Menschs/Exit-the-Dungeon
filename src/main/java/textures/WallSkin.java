package textures;

import Backend.ObjectData;
import main.ExitTheDungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WallSkin extends Skin{

    private static final Random r = new Random();
    private static final float RANDOM_OFFSET = 0.8f;
    private final List<Integer> ids = new ArrayList<>();

    private final float x, y, width, height, scaling;

    public WallSkin(String texture, float x, float y, float width, float height, float scaling) {
        super(texture);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.scaling = scaling;

        for (float d = 0; d < width + t.getScalingX() * scaling; d += t.getScalingX() * scaling) {
            for (float d2 = height + t.getScalingY() * scaling; d2 > 0; d2 -= t.getScalingY() * scaling) {
                ObjectData o = new ObjectData();
                o.data[0] = getX(x, d);
                o.data[1] = getY(y, d2);
                o.data[2] = (float) scaling * t.getScalingX();
                o.data[3] = (float) scaling * t.getScalingY();
                ids.add(ExitTheDungeon.getInstance().addObject(o, t.get()));
            }
        }
    }

    private float getRandomOffset() {
        float randomOffset = r.nextFloat(RANDOM_OFFSET);
        if(randomOffset == 0 || randomOffset == RANDOM_OFFSET) randomOffset += r.nextFloat(RANDOM_OFFSET) - RANDOM_OFFSET/2;
        randomOffset -= RANDOM_OFFSET/2;
        return randomOffset;
    }

    private float getX(float x, float d) {
        return x + d + getRandomOffset() + t.getOffsetX();
    }

    private float getY(float y, float d) {
        return y + d + getRandomOffset() + t.getOffsetY();
    }

    @Override
    public void reload() {
        t = Texture.getTextureObject(super.name);
        state = (t.getStates().contains(state)) ? state : t.getStates().get(0);

        int index = 0;
        for (float d = 0; d < width + t.getScalingX() * scaling; d += t.getScalingX() * scaling) {
            for (float d2 = height + t.getScalingY() * scaling; d2 > 0; d2 -= t.getScalingY() * scaling) {
                ExitTheDungeon.getInstance().updateObjectPosition(ids.get(index), getX(x, d), getY(y, d2));
                ExitTheDungeon.getInstance().setObjectTextureIndex(ids.get(index), t.get(state));
                index++;
            }
        }
    }
}
