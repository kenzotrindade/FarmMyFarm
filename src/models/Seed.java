package models;

public class Seed {
    String name;
    double buyPrice;
    double sellPrice;
    int growth;

    public Seed(String name, double buyPrice, double sellPrice, int growth) {
        this.name = name;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.growth = growth;
    }

    public String getName() {
        return name;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public int getGrowth() {
        return growth;
    }
}
