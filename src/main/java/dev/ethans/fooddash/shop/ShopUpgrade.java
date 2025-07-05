package dev.ethans.fooddash.shop;

// TODO Actually use this after the initial game loop is testing without upgrades
public enum ShopUpgrade {

    GROW_SPEED(10.0),
    HOE_DURABILITY(10.0),
    PLOW_RANGE(25.0);

    private int level = 0;
    private final double baseCost;

    ShopUpgrade(double baseCost) {
        this.baseCost = baseCost;
    }

    public double getCost() {
        return baseCost * (level + 1);
    }

    public int getLevel() {
        return level;
    }

    public void upgrade() {
        level++;
    }
}
