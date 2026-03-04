package models;
import java.io.Serializable;

public class Seed implements Serializable {
    public String name;
    public int growthTime;
    public double buyPrice;
    public double sellPrice;

    public Seed(String name, int growthTime, double buyPrice, double sellPrice) {
        this.name = name;
        this.growthTime = growthTime;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
    }

    public String getName() {
        return name;
    }
}