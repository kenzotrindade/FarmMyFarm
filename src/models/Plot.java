package models;

public class Plot {
    Seed plantedSeed;
    int timeLeft;

    public void plant(Seed s) {
        plantedSeed = s;
        timeLeft = s.growthTime;
    }

    public void growth() {
        if (timeLeft > 0 && plantedSeed != null) {
            timeLeft--;
        }
    }

    public boolean isReady() {
        if (timeLeft == 0 && plantedSeed != null) {
            return true;
        }

        return false;
    }

    public Seed harvest() {
        Seed actuallySeed = this.plantedSeed;

        this.plantedSeed = null;
        this.timeLeft = 0;

        return actuallySeed;
    }

    public Seed getPlantedSeed() {
        return plantedSeed;
    }

    public int getTimeLeft() {
        return timeLeft;
    }
}