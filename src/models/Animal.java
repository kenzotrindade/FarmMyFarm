package models;

import java.util.List;

public class Animal {
    private String name;
    private String type;
    private int hunger;
    private boolean isReadyToProduce;
    private int productionTime;

    public Animal(String name, String type, int productionTime) {
        this.name = name;
        this.type = type;
        this.hunger = 100;
        this.isReadyToProduce = false;
        this.productionTime = productionTime;
    }

    public void update() {
        if (hunger > 0) {
            hunger--;
            if (productionTime > 0) {
                productionTime--;
            } else {
                isReadyToProduce = true;
            }
        }
    }

    public static List<Animal> getCatalog() {
        return List.of(
            new Animal("Vache", "Cow", 60),
            new Animal("Cochon", "Pig", 45),
            new Animal("Mouton", "Sheep", 30)
        );
    }

    public String getName() { return name; }
    public String getType() { return type; }
    public int getHunger() { return hunger; }
    public int getProductionTime() { return productionTime; }
    public boolean isReadyToProduce() { return isReadyToProduce; }
    public void setHunger(int hunger) { this.hunger = hunger; }
}