import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseInput {
    private MouseInput()
    {

    }
    private static MouseInput mouseInput = new MouseInput();

    public double posX, posY, prevX, prevY, scrollX, scrollY;
    public boolean buttonsPressed[] = new boolean[3];
    public boolean dragging;

    public static void mousePosCallBack(long window, double xpos, double ypos)
    {
        mouseInput.prevX = mouseInput.posX;
        mouseInput.prevY = mouseInput.posY;

        mouseInput.posX = xpos;
        mouseInput.posY = ypos;

    }

    public static void mouseButtonCallback(long window, int button, int action, int mods)
    {
        if(action == GLFW_PRESS){
            if(button < mouseInput.buttonsPressed.length) mouseInput.buttonsPressed[button] = true;
        }
        else if(action == GLFW_RELEASE){
            mouseInput.buttonsPressed[button] = false;
            mouseInput.dragging = false;
        }
    }

    public static void mouseScrollCallback(long window, double xOffset, double yOffset)
    {
        mouseInput.scrollX = xOffset;
        mouseInput.scrollY = yOffset;
    }

    public static MouseInput get()
    {
        return mouseInput;
    }
}
