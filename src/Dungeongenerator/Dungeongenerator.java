package Dungeongenerator;

import java.util.HashMap;

public abstract class Dungeongenerator {

    public DungeonRoom[] rooms;
    public HashMap<DungeonRoom, DungeonRoom> RoomMap = new HashMap<>();
    public void generate(int size, int level){
        rooms = new DungeonRoom[size];
        for (int i = 0; i<size; i++){
            rooms[i] = new DungeonRoom(i);
        }
        for (int i = 0; i<size*2; i++) {
            rooms[i].neighbors[i] = rooms[i+1];
            rooms[i+1].neighbors[i] = rooms[i];

        }
    }
}
