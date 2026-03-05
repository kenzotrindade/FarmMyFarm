package models;

import java.io.Serializable;

public class Progression implements Serializable {
    int level = 1;
    int exp = 0;

    public void addExp(int amount) {
        exp += amount;
        while (exp >= getNextLevelThreshold()) {
            exp -= getNextLevelThreshold();
            level++;
        }
    }

    public int getNextLevelThreshold() {
        return level * 100;
    }

    public int getLevel() { return level; }
    public int getExp() { return exp; }
    
    public void setLevel(int level) { this.level = level; }
}