package models;
import java.util.Map;

public class Farm {
    private static final long serialVersionUID = 1L;
    double wallet;
    Plot[][] grid;
    Inventory inventory = new Inventory();
    Progression progression = new Progression();
    Map<String, Plot[][]> sectors = new HashMap<>();
    Enclosure enclosure = new Enclosure();

    int currentGridSize = 4;
    final int MAX_SIZE = 20;

    public Farm() {
        this.wallet = 500.0;

        initSector("CHAMP_1");
        initSector("CHAMP_2");
        initSector("ENCLOS_1");
        initSector("ENCLOS_2");
        this.grid = sectors.get("CHAMP_1");
    }

    public void update() {
        for (Plot[][] sectorGrid : sectors.values()) {
            for (int i = 0; i < currentGridSize; i++) {
                for (int j = 0; j < currentGridSize; j++) {
                    sectorGrid[i][j].growth();
                }
            }
        }
    }

    public void initSector(String key) {
        Plot[][] newGrid = new Plot[MAX_SIZE][MAX_SIZE];
        for (int i = 0; i < MAX_SIZE; i++) {
            for (int j = 0; j < MAX_SIZE; j++) {
                newGrid[i][j] = new Plot();
            }
        }
        sectors.put(key, newGrid);
    }

    public void setActiveSector(String key) {
        if(sectors.containsKey(key)) {
            this.grid = sectors.get(key);
        }
    }

    public boolean plantSeed(Seed s, int line, int column) {
        if (grid[line][column].isEmpty()) {
                grid[line][column].plant(s);
                return true;
            }
            return false;
    }

    public void collectHarvest(int line, int column) {
        if (grid[line][column].isReady()) {
            Seed planted = grid[line][column].getPlantedSeed();
            inventory.add(planted.getName(), 1);
            grid[line][column].harvest();
            progression.addExp(25);
        }
    }

    public void sellItem(String name, double price) {
        if (inventory.remove(name, 1)) {
            wallet += price;
        }
    }

    public boolean upgradeGrid() {
        if (currentGridSize < MAX_SIZE) {
            double cost = currentGridSize * 1000;
            if (wallet >= cost) {
                wallet -= cost;
                currentGridSize++;
                return true;
            }
        }

        return false;
    }

    public int getGridSize() {
        return currentGridSize;
    }

    public Progression getProgression() {
        return progression;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Enclosure getEnclosure() {
        return enclosure;
    }

    public int addMoney(int amout) {
        wallet += amout;
        return amout;
    }

    public void subMoney(int amount) {
        wallet -= amount;
    }

    public double getWallet() {
        return this.wallet;
    }

    public Plot getPlot(int line, int col) {
        return this.grid[line][col];
    }

    public void setWallet(int amount) {
        wallet = amount;
    }

}