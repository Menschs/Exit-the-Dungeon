package Backend;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.Vector;

import static org.lwjgl.opengl.GL20.*;

public class Camera {
    public Vec4f camera;
    public void uploadToShader(int location)
    {
        glUniform4f(location, camera.x, camera.y, camera.z, camera.w);
    }
    Camera(float posx, float posy, float width, float height, float camDistance){
        camera = new Vec4f();
        camera.x = posx;
        camera.y = posy;
        camera.z = height / width;
        camera.w = camDistance;
    }

    public float getX(){
        return camera.x;
    }

    public float getY(){
        return camera.y;
    }

    public void setPos(float x, float y)
    {
        camera.x = x;
        camera.y = y;
    }
}
