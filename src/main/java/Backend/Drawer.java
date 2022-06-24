package Backend;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

//Dangerous state machine
//Is used for all render operations, frame and input, time
//It's all static
public class Drawer {
    private static final Drawer drawer = new Drawer();

    private final long window;
    private MouseInput mouseInput;
    private KeyInput keyInput;

    private int vertexID, fragmentID, shaderProgram, pipe;
    private Drawer()
    {
        //Set up error message output
        GLFWErrorCallback.createPrint(System.err).set();

        mouseInput = MouseInput.get();
        keyInput = KeyInput.get();

        //Init GLFW for window
        if(!glfwInit()) throw new IllegalStateException("Init fail: GLFW");

        //Configure window with GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        //Create the window
        window = glfwCreateWindow(600, 400, "Test", NULL, NULL);
        if(window == NULL) throw new RuntimeException("Creation failed: Window");

        //Set callbacks for window messages
        glfwSetCursorPosCallback(window, MouseInput::mousePosCallBack);
        glfwSetMouseButtonCallback(window, MouseInput::mouseButtonCallback);
        glfwSetScrollCallback(window, MouseInput::mouseScrollCallback);
        glfwSetKeyCallback(window, KeyInput::keyCallback);

        //Make OpenGL inits
        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);

        GL.createCapabilities();

        String vertShaderSrc = "#type vertex\n" +
                "#version 330 core\n" +
                "layout (location=0)in vec3 pos;\n" +
                "\n" +
                "void main()\n" +
                "{\n" +
                "    gl_Position = vec4(pos, 1.0f);\n" +
                "}";

        String fragShderSrc = "#type fragment\n" +
                "#version 330 core\n" +
                "\n" +
                "out vec4 color;\n" +
                "\n" +
                "void main()\n" +
                "{\n" +
                "    color = vec4(1.0f, 1.0f, 1.0f);\n" +
                "}";

        //Create vertex shader
        vertexID = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexID, vertShaderSrc);
        glCompileShader(vertexID);
        if(glGetShaderi(vertexID, GL_COMPILE_STATUS) == GL_FALSE) throw new IllegalStateException("Broken vertex shader");
        //Create Fragment shader
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentID, fragShderSrc);
        glCompileShader(fragmentID);
        if(glGetShaderi(fragmentID, GL_COMPILE_STATUS) == GL_FALSE) throw new IllegalStateException("Broken fragment shader");

        //Create Pipeline
        pipe = glCreateProgram();
        glAttachShader(pipe, vertexID);
        glAttachShader(pipe, fragmentID);
        glLinkProgram(pipe);
        if(glGetProgrami(pipe, GL_LINK_STATUS) == GL_FALSE) throw new IllegalStateException("Pipeline creation failed");


    }

    public static long getWindow()
    {
        return drawer.window;
    }

    public static Drawer get()
    {
        return drawer;
    }

    public static void endDrawer()
    {
        glfwFreeCallbacks(drawer.window);
        glfwDestroyWindow(drawer.window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }
}