package inventory;

import java.awt.*;
import java.util.HashMap;
import java.util.UUID;

public class Item {

    private static final HashMap<String, Item> items = new HashMap<>();

    private final String id;
    private String name;

    public Item(String name) {
        this.name = name;
        id = generateID();
        items.put(id, this);
    }

    private String generateID() {
        String id = UUID.randomUUID().toString();
        if(items.containsKey(id)) {
            System.out.println("loooooooooool OMG HAAHAHAHAHAHA!!!");
            id = generateID();
        }
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void paint(Graphics2D g, int x, int y) {
        g.setColor(Color.gray);
        g.drawRect(x, y, 35, 20);
        g.setColor(Color.black);
        g.drawString(name, x + 5, y + 5);
    }

    public static Item getItem(String id) {
        return getItem(id);
    }
}
