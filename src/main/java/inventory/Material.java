package inventory;

public enum Material {

    sword("Schwert", 1), longsword("Langschwert", 10);

    private final String name;
    private int maxStack = 20;

    Material(String name, int maxStack) {
        this.name = name;
        this.maxStack = maxStack;
    }

    Material(String name) {
        this.name = name;
    }

    public int getMaxStack() {
        return maxStack;
    }

    public String getName() {
        return name;
    }
}
