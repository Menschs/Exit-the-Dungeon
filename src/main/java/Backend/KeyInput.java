package Backend;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class KeyInput {
    private KeyInput()
    {

    }

    private static KeyInput keyInput = new KeyInput();
    //private boolean[] pressed = new boolean[350];
    private static List<Long> pressed = new ArrayList<>();
    public static KeyInput get()
    {
        return keyInput;
    }

    public boolean keyPressed(int keycode) {
        return pressed.contains((long) keycode);
    }

    public List<Long> getPressed() {
        return pressed;
    }

    public static void keyCallback(long window, int key, int scancode, int action, int mods){
        if(action == GLFW_PRESS) {
            if(!pressed.contains((long) key)) pressed.add((long) key);
        }
        else if(action == GLFW_RELEASE) {
            pressed.remove((long) key);
        }
    }
}
