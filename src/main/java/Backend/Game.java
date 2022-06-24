package Backend;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public abstract class Game {
    //A function supposed to be overwritten by your game class, which will be called every frame
    public abstract void runGameUpdate();

    public Drawer drawer = Drawer.get();

    private double timeStarted = glfwGetTime();
    public double deltaTime;
    public double timeSinceStart = 0.0;

    public abstract void init();

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

            //Run the actual game function
            runGameUpdate();

            //Queue frame for presentation, start next one
            glfwSwapBuffers(Drawer.getWindow());
        }

        //Free native memory
        Drawer.endDrawer();
    }
}