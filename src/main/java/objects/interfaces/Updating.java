package objects.interfaces;

import java.util.ArrayList;
import java.util.List;

public interface Updating {

    List<Updating> update = new ArrayList<>();
    List<Updating> remove = new ArrayList<>();

    static void update(int curTicks) {
        update.forEach(updating -> updating.tick(curTicks));
    }

    static void clear() {
        update.removeAll(remove);
        remove.clear();
    }

    default void create() {
        update.add(this);
    }

    default void remove() {
        remove.add(this);
    }

    void tick(int curTicks);
}
