package models;

import java.util.ArrayList;
import java.util.List;

public class Enclosure {
    List<Animal> animals = new ArrayList<>();
    int maxCapacity = 5;

    public boolean addAnimal(Animal animal) {
        if (animals.size() < maxCapacity) {
            animals.add(animal);
            return true;
        }

        return false;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }
}
