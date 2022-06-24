package objects;

import java.util.ArrayList;
import java.util.List;

public interface Updating {

    List<Updating> update = new ArrayList<>();
    List<Updating> remove = new ArrayList<>();

    static void update() {
        update.forEach(Updating::tick);
        update.removeAll(remove);
        remove.clear();
    }

    default void create() {
        update.add(this);
    }

    default void remove() {
        remove.add(this);
    }

    void tick();
}
