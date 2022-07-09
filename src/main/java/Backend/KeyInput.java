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
    private static List<Integer> pressed = new ArrayList<>();
    public static KeyInput get()
    {
        return keyInput;
    }

    public boolean keyPressed(int keycode) {
        return pressed.contains(keycode);
    }

    public List<Integer> getPressed() {
        return pressed;
    }

    public static void keyCallback(long window, int key, int scancode, int action, int mods){
        if(action == GLFW_PRESS) {
            if(!pressed.contains(key)) pressed.add(key);
        }
        else if(action == GLFW_RELEASE) pressed.remove(key);
    }
}
