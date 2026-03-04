package models;

public class Farm {
    private double wallet;
    private Plot[][] grid;

    public Farm() {
        this.wallet = 500.0;
        this.grid = new Plot[4][4];
        
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                grid[i][j] = new Plot();
            }
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
            Seed recolte = grid[line][column].harvest();
            wallet += recolte.sellPrice;
        }
    }

    public double getWallet() {
        return this.wallet;
    }

    public Plot getPlot(int line, int col) {
        return this.grid[line][col];
    }

}