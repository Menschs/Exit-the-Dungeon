package Dungeongenerator;

public class DungeonRoom {
    public DungeonElement[] elements;
    public DungeonRoom[] neighbors;
    int number;

    public DungeonRoom(int _number){
        number = _number;
    }
}
