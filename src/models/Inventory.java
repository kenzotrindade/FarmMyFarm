package models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Inventory implements Serializable {
    private static final long serialVersionUID = 1L;
    Map<String, Integer> items = new HashMap<>();

    public void add(String itemName, int quantity) {
        items.put(itemName, items.getOrDefault(itemName, 0) + quantity);
    }

    public boolean remove(String itemName, int quantity) {
        int current = items.getOrDefault(itemName, 0);
        if (current >= quantity) {
            items.put(itemName, current - quantity);
            return true;
        }
        return false;
    }

    public int getAmount(String name) {
        return items.getOrDefault(name, 0);
    }

    public Map<String, Integer> getItems() {
        return items;
    }
}