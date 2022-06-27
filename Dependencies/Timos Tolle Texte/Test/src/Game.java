import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferSubData;
import static org.lwjgl.opengl.GL43.GL_SHADER_STORAGE_BUFFER;

public abstract class Game {
    //A function supposed to be overwritten by your game class, which will be called every frame
    public abstract void runGameUpdate();

    public Drawer drawer = Drawer.get();

    private double timeStarted = glfwGetTime();
    public double deltaTime;
    public double timeSinceStart = 0.0;

    public boolean isKeyPressed(int keycode){
        return drawer.isKeyPressed(keycode);
    }

    public double getMouseX(){
        return drawer.getMouseX();
    }

    public double getMouseY(){
        return drawer.getMouseY();
    }

    public boolean isMousePressed(int code){
        return drawer.isMousePressed(code);
    }

    public abstract void init();

    public void setCameraPos(float x, float y)
    {
        drawer.camera.setPos(x, y);
    }

    public void endGame(){
        glfwWindowShouldClose(Drawer.getWindow());
    }

    public int addObject(ObjectData o){
        return drawer.addObject(o);
    }
    public void updateObjectPosition(int ID, float newX, float newY){
        drawer.updateObjectPosition(ID, newX, newY);
    }
    //Simply call the function once to run the game
    public void runGame()
    {
        //Init game
        init();

        //Loop, generate frames
        while(!glfwWindowShouldClose(Drawer.getWindow()))
        {
            //Poll events from system
            glfwPollEvents();

            //Clear Buffer from last frame
            glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            //Update the time
            deltaTime = glfwGetTime() - timeSinceStart;
            timeSinceStart += deltaTime;

            //glfwSetWindowTitle(Drawer.getWindow(), 1.0 / deltaTime + " ");

            //Run the actual game function
            runGameUpdate();

            //Start drawing
            drawer.draw();

            //Queue frame for presentation, start next one
            glfwSwapBuffers(Drawer.getWindow());
        }

        //Free native memory
        Drawer.endDrawer();
    }
}
