
public class Test extends Game{
    public static void main(String[] args)
    {
        Test t = new Test();
        t.runGame();
    }
    ObjectData o ;
    int oID;
    @Override
    public void runGameUpdate() {
        o.data[0] += deltaTime;
        o.data[1] *= deltaTime;
        updateObjectPosition(oID, o.data[0], o.data[1]);

        if(isKeyPressed()){

        }
    }
    public float pX = 0.0f, pY = 0.0f;

    @Override
    public void init() {
        o = new ObjectData();
        o.data[0] = 1.0f;
        o.data[0] = 1.0f;
        o.data[2] = 1.0f;
        o.data[3] = 1.0f;
        oID = addObject(o);

        ObjectData o2 = new ObjectData();
        o2.data[0] = -3.0f;
        o2.data[1] = -3.0f;
        o2.data[2] = 0.5f;
        o2.data[3] = 0.5f;
        addObject(o2);

        ObjectData o3 = new ObjectData();
        o3.data[0] = -1.0f;
        o3.data[1] = 1.0f;
        o3.data[2] = 1.5f;
        o3.data[3] = 1.5f;
        addObject(o3);
    }

}