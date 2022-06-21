package entities;

import java.util.ArrayList;
import java.util.List;

public interface Updating {

    List<Updating> update = new ArrayList<>();

    static void update() {
        update.forEach(updating -> {
            if(!updating.isRemoved()) updating.tick();
        });
    }

    default void create() {
        update.add(this);
    }

    boolean isRemoved();

    void tick();
}
