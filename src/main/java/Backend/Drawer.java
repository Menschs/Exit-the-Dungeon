package Backend;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.*;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL31.*;
import static org.lwjgl.opengl.GL43.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

//Dangerous state machine
//Is used for all render operations, frame and input, time
//It's all static
public class Drawer {
    private static final Drawer drawer = new Drawer();

    final int maxObjectCount = 32768;

    private final long window;
    private MouseInput mouseInput;
    private KeyInput keyInput;

    public boolean isKeyPressed(int keycode)
    {
        return keyInput.keyPressed(keycode);
    }

    public List<Long> getPressed() {
        return keyInput.getPressed();
    }

    public double getMouseX() {
        return mouseInput.posX;
    }

    public void setObjectTextureIndex(int objectIndex, int n_textureIndex){
        objectTextureIndices[objectIndex] = n_textureIndex;
    }

    public Camera getCamera(){
        return camera;
    }

    public double getMouseY(){
        return mouseInput.posY;
    }

    public boolean isMousePressed(int code){
        return mouseInput.buttonsPressed[code];
    }

    private int vertexID, fragmentID, shaderProgram, pipe, vaid, vertexBuffer, indexBuffer;
    IntBuffer indexBuff;
    FloatBuffer objectBuffer;
    private float vertices[];
    private int indices[];
    int objectTextureIndices[];
    private int objectBufferID, amountObjects = 0;

    private Queue<Integer> freeIndices = new ArrayDeque<>();
    public Camera camera;

    public void draw()
    {
        //Bind pipeline and vertices
        int varloc = glGetUniformLocation(pipe, "camera");
        int samploc = glGetUniformLocation(pipe, "TEX_SAMPLER");
        int obloc = glGetUniformLocation(pipe, "objectId");

        glUseProgram(pipe);
        camera.uploadToShader(varloc);
        glBindVertexArray(vaid);

        //Bind our storage buffer
        glBindBufferBase(GL_SHADER_STORAGE_BUFFER, 1, objectBufferID);

        //Enable the vertex input layout aka vertex attribute pointer
        glEnableVertexAttribArray(0);

        glUniform1i(samploc, 0);
        glActiveTexture(GL_TEXTURE0);

        //Draw single elements?
        for (int i = 0; i < amountObjects; i++){
            glBindTexture(GL_TEXTURE_2D, textures[objectTextureIndices[i]]);
            glUniform1i(obloc, i);
            glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
        }
        /*glBindTexture(GL_TEXTURE_2D, textures[0]);
        glDrawElementsInstanced(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0, amountObjects);*/

        //Unbind stuff
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
        glUseProgram(0);
    }

    public void disableObject(int objectIndex){
        glBindBuffer(GL_SHADER_STORAGE_BUFFER, objectBufferID);
        float possy[] = {0.0f, 0.0f};
        glBufferSubData(GL_SHADER_STORAGE_BUFFER, objectIndex * 8 * 4 + 2 * 4, possy);
        glBindBuffer(GL_SHADER_STORAGE_BUFFER, 0);
    }

    public void disableFreeObject(int objectIndex){
        disableObject(objectIndex);
        freeIndices.add(objectIndex);
    }

    public int width, height;

    private Drawer()
    {
        //Set up error message output
        GLFWErrorCallback.createPrint(System.err).set();

        objectTextureIndices = new int[maxObjectCount];
        mouseInput = MouseInput.get();
        keyInput = KeyInput.get();

        //Init GLFW for window
        if(!glfwInit()) throw new IllegalStateException("Init fail: GLFW");

        //Configure window with GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        //Create the window
        width = glfwGetVideoMode(glfwGetPrimaryMonitor()).width();
        height = glfwGetVideoMode(glfwGetPrimaryMonitor()).height();
        //window = glfwCreateWindow(1800, 800, "Test", NULL, NULL);
        window =   glfwCreateWindow(width,
            height, "WindowSombrero",
            glfwGetPrimaryMonitor(), NULL);
        if(window == NULL) throw new RuntimeException("Creation failed: Window");

        //Set callbacks for window messages
        glfwSetCursorPosCallback(window, MouseInput::mousePosCallBack);
        glfwSetMouseButtonCallback(window, MouseInput::mouseButtonCallback);
        glfwSetScrollCallback(window, MouseInput::mouseScrollCallback);
        glfwSetKeyCallback(window, KeyInput::keyCallback);

        //Make OpenGL inits
        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);

        camera = new Camera(0.0f, 0.0f, width, height,3.0f);

        GL.createCapabilities();

        String vertShaderSrc = "#version 430 core\n" +
                "\n" +
                "layout (location = 0) in vec2 pos;\n" +
                "\n" +
                "out vec2 texCords;\n" +
                "\n" +
                "uniform vec4 camera;\n" +
                "uniform int objectId;\n" +
                "flat out float depth;\n" +
                "\n" +
                "struct Obs{\n" +
                "    vec2 pos;\n" +
                "    vec2 scaling;\n" +
                "    float depth;\n" +
                "    float rotation;\n" +
                "    vec2 padding;\n" +
                "};\n" +
                "\n" +
                "layout(binding = 1) buffer objectBuffer{\n" +
                "    Obs objects[];\n" +
                "} ObjectBuffer;\n" +
                "\n" +
                "void main() {\n" +
                "    texCords = pos;\n" +
                "    //gl_Position = vec4(((pos.x * ObjectBuffer.objects[objectId].scaling.x - camera.x + ObjectBuffer.objects[objectId].pos.x) * camera.z) / camera.w, ((pos.y * ObjectBuffer.objects[objectId].scaling.y- camera.y + ObjectBuffer.objects[objectId].pos.y)) / camera.w, ObjectBuffer.objects[objectId].depth, 1.0f);\n" +
                "    vec2 mpos = pos;\n" +
                "\n" +
                "    //Rotate\n" +
                "    mpos.x = (mpos.x * cos(ObjectBuffer.objects[objectId].rotation) - mpos.y * sin(ObjectBuffer.objects[objectId].rotation));\n" +
                "    mpos.y = (mpos.y * cos(ObjectBuffer.objects[objectId].rotation) + mpos.x * sin(ObjectBuffer.objects[objectId].rotation));\n" +
                "\n" +
                "    //Scale\n" +
                "    mpos = mpos * ObjectBuffer.objects[objectId].scaling;\n" +
                "\n" +
                "    //Transform with worldspace coordinates and camera coordinates\n" +
                "    mpos = mpos + ObjectBuffer.objects[objectId].pos - camera.xy;\n" +
                "\n" +
                "    //Scale with width/height ratio\n" +
                "    mpos.x = mpos.x * camera.z;\n" +
                "\n" +
                "    //Transform by camera distance\n" +
                "    mpos = mpos / camera.w;\n" +
                "\n" +
                "    gl_Position = vec4(mpos, depth, 1.0f);\n" +
                "    texCords = vec2(max(0, texCords.x), max(0, texCords.y));\n" +
                "    depth = ObjectBuffer.objects[objectId].depth;\n" +
                "}";

        String fragShderSrc = "#version 430 core\n" +
                "\n" +
                "in vec2 texCords;\n" +
                "\n" +
                "flat in float depth;\n" +
                "uniform sampler2D TEX_SAMPLER;\n" +
                "\n" +
                "out vec4 color;\n" +
                "\n" +
                "void main(){\n" +
                "    vec4 c = texture(TEX_SAMPLER, texCords);\n" +
                "    color = c;\n" +
                "}";

        //Create vertex shader
        vertexID = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexID, vertShaderSrc);
        glCompileShader(vertexID);
        if(glGetShaderi(vertexID, GL_COMPILE_STATUS) == GL_FALSE)
        {
            int len = glGetShaderi(vertexID, GL_INFO_LOG_LENGTH);
            throw new IllegalStateException("Broken vertex shader\n" + glGetShaderInfoLog(vertexID, len));
        }
        //Create Fragment shader
        fragmentID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentID, fragShderSrc);
        glCompileShader(fragmentID);
        if(glGetShaderi(fragmentID, GL_COMPILE_STATUS) == GL_FALSE)
        {
            int len = glGetShaderi(fragmentID, GL_INFO_LOG_LENGTH);
            throw new IllegalStateException("Broken fragmentshader\n" + glGetShaderInfoLog(fragmentID, len));
        }

        //Create Pipeline
        pipe = glCreateProgram();
        glAttachShader(pipe, vertexID);
        glAttachShader(pipe, fragmentID);
        glLinkProgram(pipe);
        if(glGetProgrami(pipe, GL_LINK_STATUS) == GL_FALSE) {
            int l = glGetProgrami(pipe, GL_INFO_LOG_LENGTH);

            throw new IllegalStateException("Pipeline creation failed\n" + glGetProgramInfoLog(pipe));
        }

        vertices = new float[]{
            //Position              //
               1.0f, -1.0f,
               1.0f, 1.0f,
               -1.0f, 1.0f,
               -1.0f, -1.0f
        };

        indices = new int[]{
            0, 1, 2,
            2, 3, 0
        };

        //Vertex buffer setup
        vaid = glGenVertexArrays();
        glBindVertexArray(vaid);

        FloatBuffer vertexBuff= BufferUtils.createFloatBuffer(vertices.length);
        vertexBuff.put(vertices).flip();

        //Create vbo
        vertexBuffer = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vertexBuffer);
        glBufferData(GL_ARRAY_BUFFER, vertexBuff, GL_STATIC_DRAW);

        //Create and upload indices
        indexBuff = BufferUtils.createIntBuffer(indices.length);
        indexBuff.put(indices).flip();
        indexBuffer = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuff, GL_STATIC_DRAW);

        //Add vertex attribute pointers
        int posSize = 2;
        glVertexAttribPointer(0, posSize, GL_FLOAT, false, posSize * 4, 0);
        glEnableVertexAttribArray(0);

        //Make a storage buffer
        objectBufferID = glGenBuffers();
        glBindBuffer(GL_SHADER_STORAGE_BUFFER, objectBufferID);

        objectBuffer = BufferUtils.createFloatBuffer(maxObjectCount * 8);
        glBufferData(GL_SHADER_STORAGE_BUFFER, objectBuffer, GL_DYNAMIC_READ);

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public int addObject(ObjectData o, int textureIndex){
        if(freeIndices.isEmpty())
        {
            glBindBuffer(GL_SHADER_STORAGE_BUFFER, objectBufferID);
            glBufferSubData(GL_SHADER_STORAGE_BUFFER, amountObjects * 8 * 4, o.data);
            glBindBuffer(GL_SHADER_STORAGE_BUFFER, 0);
            objectTextureIndices[amountObjects] = textureIndex;
            amountObjects++;
            return amountObjects - 1;
        }
        else{
            int index = freeIndices.remove();
            glBindBuffer(GL_SHADER_STORAGE_BUFFER, objectBufferID);
            glBufferSubData(GL_SHADER_STORAGE_BUFFER, index * 8 * 4, o.data);
            glBindBuffer(GL_SHADER_STORAGE_BUFFER, 0);
            objectTextureIndices[index] = textureIndex;
            return index;
        }
    }

    private int amountTextures = 0;
    private int textures[] = new int[128];
    public int addTexture(String filename){
        //System.out.println(filename);
        //Load buffered image to file
        BufferedImage b = null;
        try {b = ImageIO.read(new File(filename));} catch (IOException e){
            System.out.println("Problem loading image file:"+ filename + e.getMessage());
        }
        //Check if the format is correct:
        if(!b.getColorModel().hasAlpha()){
            throw new IllegalStateException("Illegal texture format, I want Alpha: " + filename);
        }

        //Load texture to float array
        float pixels[] = new float[b.getWidth() * b.getHeight() * 4]; //Width * height for the amount of pixels, four floats per pixel
        for(int y = 0; y < b.getHeight(); y++){
            for(int x = 0; x < b.getWidth(); x++){
                int col = b.getRGB(x, y);
                Color c = new Color(col);
                pixels[((b.getHeight() - y - 1) * b.getWidth() + x) * 4 + 0] =  c.getRed() / 255.0f;
                pixels[((b.getHeight() - y - 1) * b.getWidth() + x) * 4 + 1] =  c.getGreen() / 255.0f;
                pixels[((b.getHeight() - y - 1) * b.getWidth() + x) * 4 + 2] =  c.getBlue() / 255.0f;
                float alpha []= new float[1];
                pixels[((b.getHeight() - y - 1) * b.getWidth() + x) * 4 + 3] =  b.getAlphaRaster().getPixel(x, y, alpha)[0] / 255.0f;
            }
        }

        //Load texture to gpu
        textures[amountTextures] = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, textures[amountTextures]);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, b.getWidth(), b.getHeight(), 0, GL_RGBA, GL_FLOAT, pixels);

        amountTextures++;
        return amountTextures - 1;
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

    public void updateObjectPosition(int ID, float newX, float newY){
        glBindBuffer(GL_SHADER_STORAGE_BUFFER, objectBufferID);
        float possy[] = {newX, newY};
        glBufferSubData(GL_SHADER_STORAGE_BUFFER, ID * 8 * 4, possy);
        glBindBuffer(GL_SHADER_STORAGE_BUFFER, 0);
    }
}
