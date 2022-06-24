package Backend;

import static org.lwjgl.glfw.GLFW.*;

public class KeyInput {
    private KeyInput()
    {

    }

    private static KeyInput keyInput = new KeyInput();
    private boolean[] pressed = new boolean[350];
    public static KeyInput get()
    {
        return keyInput;
    }

    public boolean keyPressed(int keycode) {
        return pressed[keycode];
    }

    public static void keyCallback(long window, int key, int scancode, int action, int mods){
        if(action == GLFW_PRESS) keyInput.pressed[key] = true;
        else if(action == GLFW_RELEASE) keyInput.pressed[key] = false;
    }
}
