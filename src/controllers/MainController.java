package controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import models.Farm;
import javafx.fxml.FXML;
import models.Enclosure;

public class MainController {
    @FXML private ProgressionController progressionViewController;
    @FXML private FarmController farmViewController;
    @FXML private ShopController shopViewController;
    @FXML private InventoryController inventoryViewController;
    @FXML private DebugController debugViewController;
    @FXML private EnclosureController enclosureViewController;

    Farm farm;
    Enclosure enclosure;

    private String selectedItemName = null;

    public void init(Farm farm, Enclosure enclosure) {
        this.farm = farm;
        this.enclosure = enclosure;

        progressionViewController.init(farm);
        farmViewController.init(farm, this);
        shopViewController.init(farm, enclosure, this);
        inventoryViewController.init(farm, this);
        debugViewController.init(farm, this);
        enclosureViewController.init(enclosure, this);

        refreshAll();
        startGameLoop();
    }

    public void startGameLoop() {
        Timeline loop = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            farm.update();

            if (enclosure != null && enclosure.getAnimals() != null) {
                for (models.Animal a : enclosure.getAnimals()) {
                    a.update();
                }
            }

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
        enclosureViewController.refresh();

        checkLevelMilestones();
    }

    public void checkLevelMilestones() {
        if (farm.getProgression().getLevel() >= 20) {
            System.out.println("Level 20 ready !");
        }
    }

    public void setSelectedItem(String itemName) {
        this.selectedItemName = itemName;
    }

    public String getSelectedItem() {
        return selectedItemName;
    }
}
