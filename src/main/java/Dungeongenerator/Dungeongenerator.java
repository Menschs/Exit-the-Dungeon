package Dungeongenerator;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Dungeongenerator {

    Random r = new Random();
    public DungeonRoom[] rooms;
    public HashMap<DungeonRoom, DungeonRoom> roomMap = new HashMap<>();
    int specialCount = 0;

    public void generate(int level){
        rooms = new DungeonRoom[12];
        rooms[0] = new DungeonRoom(0, roomType.Start);
        while(rooms[11] == null){
            generateRoom(Arrays.stream(rooms).toList().size()+1);
        }
        int tollerint = 0;
        for (DungeonRoom room:
             rooms) {
            if(room.neighbors.length == 1){
                room.type = roomType.End;
                System.out.println(tollerint);
                tollerint++;
            }
        }
    }

    public void generateRoom(int i){
        if(i == 1){return;}
            roomType type;
            if(r.nextBoolean() && specialCount <= 2){
                type = roomType.Special;
                specialCount++;
            } else {
                type = roomType.Normal;
            }
            rooms[i-2] = new DungeonRoom(i,type);
            System.out.println(i);
    }

    public boolean random(int percent){
        return r.nextInt(0, 100) % (100 / percent) == 0;
    }
}
