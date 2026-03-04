package models;
import java.io.Serializable;

public class Farm implements Serializable {
    private double wallet;
    private Plot[][] grid;
    private int level;
    private int exp;

    public Farm() {
        this.wallet = 500.0;
        this.grid = new Plot[4][4];
        this.level = 1;
        this.exp = 0;
        
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                grid[i][j] = new Plot();
            }
        }
    }

    public void addExp(int amount) {
        exp += amount;

        int nexLevel = level * 100;

        if (exp >= nexLevel) {
            exp -= nexLevel;
            level++;
        }

    }

    public void update() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                grid[i][j].growth();
            }
        }
    }

    public boolean plantSeed(Seed s, int line, int column) {
        if (wallet >= s.buyPrice) {
            if (grid[line][column].getPlantedSeed() == null) {
                wallet -= s.buyPrice;
                grid[line][column].plant(s);
                return true;
            }
        }
        return false;
    }

    public void collectHarvest(int line, int column) {
        if (grid[line][column].isReady()) {
            Seed gain = grid[line][column].harvest();
            wallet += gain.sellPrice;

            addExp(25);
        }
    }

    public int addMoney(int amout) {
        wallet += amout;
        return amout;
    }

    public double getWallet() {
        return this.wallet;
    }

    public Plot getPlot(int line, int col) {
        return this.grid[line][col];
    }

    public int getLevel() {
        return level;
    }

    public int getExp() {
        return exp;
    }

}