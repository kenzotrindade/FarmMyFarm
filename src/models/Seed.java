package models;
import java.io.Serializable;
import java.util.List;

public class Seed implements Serializable {
    private static final long serialVersionUID = 1L;
    public String name;
    public String productName;
    public int growthTime;
    public double buyPrice;
    public double sellPrice;
    public int minLevel;

    public Seed(String name, String productName, int growthTime, double buyPrice, double sellPrice, int minLevel) {
        this.name = name;
        this.productName = productName;
        this.growthTime = growthTime;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.minLevel = minLevel;
    }

    public static List<Seed> getCatalog() {
        return List.of(
            new Seed("Graine de Blé", "Blé", 15, 7.5, 10, 1),
            new Seed("Graine de Maïs", "Maïs", 30, 15, 30, 3),
            new Seed("Graine de Tomate" ,"Tomate", 5, 2.5, 5, 5),
            new Seed("Graine de Citrouille", "Citrouille", 60, 50, 120, 10)
        );
    }

    public String getSeedName() { return name; }
    public String getName() { return productName; }
    public int getGrowthTime() { return growthTime; }
    public double getBuyPrice() { return buyPrice; }
    public double getSellPrice() { return sellPrice; }
    public int getMinLevel() { return minLevel; }
}