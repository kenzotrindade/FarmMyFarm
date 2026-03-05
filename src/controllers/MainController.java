package controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import models.Farm;
import javafx.fxml.FXML;

public class MainController {
    @FXML private ProgressionController progressionViewController;
    @FXML private FarmController farmViewController;
    @FXML private ShopController shopViewController;
    @FXML private InventoryController inventoryViewController;
    @FXML private DebugController debugViewController;

    Farm farm;

    public void init(Farm farm) {
        this.farm = farm;

        progressionViewController.init(farm);
        farmViewController.init(farm, this);
        shopViewController.init(farm, farmViewController);
        inventoryViewController.init(farm, this);
        debugViewController.init(farm, this);
        

        startGameLoop();
    }

    public void startGameLoop() {
        Timeline loop = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            farm.update();
            refreshAll();
        }));

        loop.setCycleCount(Timeline.INDEFINITE);
        loop.play();
    }

    public void switchField(String sectorName) {
        farm.setActiveSector(sectorName);
        refreshAll();
    }

    public void refreshAll() {
        progressionViewController.refresh();
        farmViewController.refresh();
        shopViewController.refresh();
        inventoryViewController.refresh();

        checkLevelMilestones();
    }

    public void checkLevelMilestones() {
        if (farm.getProgression().getLevel() >= 20) {
            System.out.println("Level 20 ready !");
        }
    }
}
