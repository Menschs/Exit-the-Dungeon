package Dungeongenerator;

public class DungeonRoom {
    public DungeonRoom[] neighbors;
    int id;
    roomType type;

    public DungeonRoom(int id, roomType type){
        this.id = id;
        this.type = type;
        neighbors = new DungeonRoom[4];
    }
}
enum roomType{
    Start,
    End,
    Special,
    Normal
}
