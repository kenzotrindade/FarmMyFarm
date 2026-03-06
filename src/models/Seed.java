package models;
import java.io.Serializable;
import java.util.List;

public class Seed implements Serializable {
    private static final long serialVersionUID = 1L;
    public String name;
    public int growthTime;
    public double buyPrice;
    public double sellPrice;
    public int minLevel;

    public Seed(String name, int growthTime, double buyPrice, double sellPrice, int minLevel) {
        this.name = name;
        this.growthTime = growthTime;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.minLevel = minLevel;
    }

    public static List<Seed> getCatalog() {
        return List.of(
            new Seed("Blé", 10, 25, 5, 1),
            new Seed("Maïs", 50, 150, 15, 3),
            new Seed("Tomate", 200, 500, 40, 5),
            new Seed("Citrouille", 1000, 3000, 120, 10)
        );
    }

    public String getName() { return name; }
    public int getGrowthTime() { return growthTime; }
    public double getBuyPrice() { return buyPrice; }
    public double getSellPrice() { return sellPrice; }
    public int getMinLevel() { return minLevel; }
}