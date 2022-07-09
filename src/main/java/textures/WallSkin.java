package textures;

import Backend.ObjectData;
import main.ExitTheDungeon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WallSkin extends Skin{

    private static final Random r = new Random();
    private static final float RANDOM_OFFSET = 0.4f;
    private final List<Integer> ids = new ArrayList<>();

    private final double x, y, width, height, scaling;

    public WallSkin(String texture, double x, double y, double width, double height, double scaling) {
        super(texture);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.scaling = scaling;

        for (double d = 0; d < width / t.getScalingX() * scaling; d += t.getScalingX() * scaling/2) {
            for (double d2 = height / t.getScalingY() * scaling; d2 > 0; d2 -= t.getScalingY() * scaling/2) {
                ObjectData o = new ObjectData();
                o.data[0] = (float) (x + d) + r.nextFloat(RANDOM_OFFSET) - RANDOM_OFFSET/2;
                o.data[1] = (float) (y + d2) + r.nextFloat(RANDOM_OFFSET) - RANDOM_OFFSET/2;
                o.data[2] = (float) scaling * t.getScalingX();
                o.data[3] = (float) scaling * t.getScalingY();
                ids.add(ExitTheDungeon.getInstance().addObject(o, t.get()));
            }
        }
    }
}
