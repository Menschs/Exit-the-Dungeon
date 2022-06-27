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

    public void addneighbor(){
        neighbors[0] = new DungeonRoom(10000000,roomType.Normal);
    }
}
enum roomType{
    Start,
    End,
    Special,
    Normal
}
