package controllers;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import models.*;

public class FarmController {

    private Farm myFarm = new Farm();
    private Button[][] buttonGrid = new Button[4][4];
    private int autoSaveCounter = 0;
    private Seed selectedSeed = new Seed("Ble", 10, 10, 25);

    @FXML Label levelLabel;
    @FXML Label weatherLabel;
    @FXML Label seasonLabel;
    @FXML VBox seedSelectionBox;
    @FXML VBox contractsBox;
    @FXML Tab tabGarage;
    @FXML ComboBox<String> debugWeatherChoice;
    @FXML GridPane farmGrid;
    @FXML Label walletLabel;

    @FXML
    private void debugAddMoney() {
        myFarm.addMoney(1000000);
        refreshUI();
        System.out.println("Debug: Ajout argent");
    }

    @FXML
    private void debugResetMoney() {
        System.out.println("Debug: Reset argent");
    }

    @FXML
    private void debugFastForward() {
        for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
            Plot p = myFarm.getPlot(i, j);
            if (p.getPlantedSeed() != null) {
                p.setTimeLeft(0);
            }
        }
    }
    refreshUI();
        System.out.println("Debug: Avance rapide");
    }

    @FXML
    private void debugLevelUp() {
        myFarm.addExp(myFarm.getLevel() * 100);
        refreshUI();
        System.out.println("Debug: Level up");
    }

    @FXML
    private void debugUnlockAll() {
        System.out.println("Debug: Tout débloquer");
    }

    @FXML
    public void initialize() {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Button btn = new Button("Vide");
                btn.setPrefSize(100, 100);
 
                int row = i;
                int col = j;
                
                btn.setOnAction(e -> handlePlotClick(row, col));
                
                farmGrid.add(btn, col, i);
                buttonGrid[i][j] = btn;
            }
        }

        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 1_000_000_000) {
                    myFarm.update();
                    refreshUI();
                    lastUpdate = now;

                    autoSaveCounter++;
                }

                if (autoSaveCounter >= 60) {
                    services.SaveManager.saveFarm(myFarm);
                    System.out.println("Autosave effectuée");
                    autoSaveCounter = 0;
                }
            }
        };
        timer.start();
    }

    @FXML
    private void onBuyWheat() {
        this.selectedSeed = new Seed("Ble", 10, 10, 25);
        System.out.println("Graine de blé sélectionnée !");
    }

    @FXML
    private void onSaveButtonClick() {
        if (myFarm != null) {
            services.SaveManager.saveFarm(myFarm);
            System.out.println("Sauvegarde manuelle réussie !");
        }
    }

    @FXML
    private void saveGame() {
        if (myFarm != null) {
            services.SaveManager.saveFarm(myFarm);
            System.out.println("Sauvegarde réussie !");
        }
    }

    public void setFarm(Farm farm) {
        this.myFarm = farm;
        refreshUI();
    }

    private void handlePlotClick(int r, int c) {
        Plot plot = myFarm.getPlot(r, c);
        
        if (plot.getPlantedSeed() == null) {
            myFarm.plantSeed(selectedSeed, r, c);
        } else if (plot.isReady()) {
            myFarm.collectHarvest(r, c);
        }
        refreshUI();
    }

    private void refreshUI() {
        if (myFarm == null) return;
        walletLabel.setText("Argent : " + myFarm.getWallet() + " €");
        levelLabel.setText("Niveau : " + myFarm.getLevel());
        
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Plot plot = myFarm.getPlot(i, j);
                Button btn = buttonGrid[i][j];
                
                if (plot.getPlantedSeed() == null) {
                    btn.setText("Vide");
                } else if (plot.isReady()) {
                    btn.setText("RÉCOLTER\n(" + plot.getPlantedSeed().name + ")");
                } else {
                    btn.setText(plot.getPlantedSeed().name + "\n" + plot.getTimeLeft() + "s");
                }
            }
        }
    }
    
}