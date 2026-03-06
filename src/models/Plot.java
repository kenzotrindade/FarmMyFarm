package models;

public class Plot {
    private static final long serialVersionUID = 1L;
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

    public boolean isEmpty() {
        return plantedSeed == null;
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

    public void setTimeLeft(int time) {
        timeLeft = time;
    }

    public Seed getPlantedSeed() {
        return plantedSeed;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public Seed getPlantSeed() {
        return plantedSeed;
    }

    public void setPlantedSeed(Seed s) {
        plantedSeed = s;
    }
}