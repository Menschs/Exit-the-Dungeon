package inventory.items.items;

import inventory.items.ItemStack;
import inventory.items.Itemtype;
import inventory.items.Material;
import inventory.items.Rarity;
import util.frame.gui.Text;
import util.frame.gui.TextCenter;

import java.util.ArrayList;
import java.util.List;

public class Sword extends ItemStack {

    public Sword(Rarity rarity) {
        super(Material.sword, Itemtype.weapon, rarity);
    }

    private List<Text> lastResult = new ArrayList<>();
    private int lastLevel = -1;

    @Override
    public List<Text> getLore() {
        if(getRarity().getIndex() == lastLevel) return lastResult;
        List<Text> result = new ArrayList<>();
        result.add(new Text("§alol " + (10 * getRarity().getIndex() + 1), 0, 0, TextCenter.left));
        lastResult = result;
        lastLevel = getRarity().getIndex();
        return result;
    }
}
