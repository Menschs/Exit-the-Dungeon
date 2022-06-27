package inventory;

import objects.entities.DroppedItem;

import java.awt.*;
import java.util.Objects;

public class ItemStack {

    private final Material material;
    private final Itemtype type;
    private Rarity rarity;

    private int amount = 1;

    public ItemStack(Material material, Itemtype type, Rarity rarity) {
        this.material = material;
        this.type = type;
        this.rarity = rarity;
    }

    public int add(int amount) {
        return modifyAmount(amount);
    }

    @Override
    protected ItemStack clone() {
        return new ItemStack(material, type, rarity).setAmount(amount);
    }

    public int remove(int amount) {
        return modifyAmount(-amount);
    }

    public int modifyAmount(int amount) {
        int result = this.amount + amount;
        if(result > material.getMaxStack())  {
            this.amount = material.getMaxStack();
            return result - material.getMaxStack();
        }
        this.amount += amount;
        if(result <= 0) return Math.abs(this.amount);
        return 0;
    }

    public Material getMaterial() {
        return material;
    }

    public Itemtype getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public ItemStack setAmount(int amount) {
        amount = Math.min(amount, material.getMaxStack());
        this.amount = amount;
        return this;
    }

    public void paint(Graphics2D g, int x, int y) {
        g.setColor(rarity.getColor());
        g.fillRect(x, y, 120, 20);
        g.setColor(Color.black);
        g.drawString(material.getName() + " x" + amount, x + 5, y + 15);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemStack itemStack = (ItemStack) o;
        return material == itemStack.material && type == itemStack.type && rarity == itemStack.rarity;
    }

    public void drop(int x, int y) {
        new DroppedItem(x, y, this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(material, type, rarity);
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }
}
