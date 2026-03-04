package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import models.*;

public class FarmController {

    @FXML private TabPane mainTabPane;
    @FXML private Label walletLabel;
    @FXML private Label levelLabel;
    @FXML private Label weatherLabel;
    @FXML private Label seasonLabel;
    @FXML private VBox seedSelectionBox;
    @FXML private Button btnArrosoir;
    @FXML private Button btnFaux;
    @FXML private GridPane farmGrid;
    @FXML private VBox contractsBox;
    @FXML private Tab tabGarage;
    @FXML private ComboBox<String> debugWeatherChoice;

    private Farm myFarm;
    private Seed selectedSeed;

    @FXML
    public void initialize() {
        this.selectedSeed = Seed.getCatalog().get(0);

        AnimationTimer gameLoop = new AnimationTimer() {
            private long lastUpdate = 0;
            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 1_000_000_000) {
                    updateGameTime();
                    lastUpdate = now;
                }
            }
        };
        gameLoop.start();

        if (debugWeatherChoice != null) {
            debugWeatherChoice.getItems().addAll("Soleil", "Pluie", "Orage");
        }
    }

    public void setFarm(Farm farm) {
        this.myFarm = farm;
        refreshSeedStore();
        refreshUI();
    }

    private void updateGameTime() {
        if (myFarm == null) return;
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                Plot p = myFarm.getPlot(r, c);
                if (p.getPlantedSeed() != null && !p.isReady()) {
                    p.setTimeLeft(p.getTimeLeft() - 1);
                }
            }
        }
        Platform.runLater(() -> refreshUI());
    }

    private void refreshUI() {
        if (myFarm == null) return;

        walletLabel.setText("Argent : " + myFarm.getWallet() + " €");
        levelLabel.setText("Niveau : " + myFarm.getLevel());

        farmGrid.getChildren().clear();
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                Plot plot = myFarm.getPlot(r, c);
                Button btn = new Button();
                btn.setPrefSize(100, 100);

                if (plot.isEmpty()) {
                    btn.setText("Vide");
                } else if (plot.isReady()) {
                    btn.setText("RÉCOLTER\n(" + plot.getPlantedSeed().getName() + ")");
                    btn.setStyle("-fx-background-color: #90EE90;");
                } else {
                    btn.setText(plot.getPlantedSeed().getName() + "\n" + plot.getTimeLeft() + "s");
                }

                int row = r;
                int col = c;
                btn.setOnAction(e -> handlePlotClick(row, col));
                farmGrid.add(btn, col, r);
            }
        }
        
        if (myFarm.getLevel() >= 20) {
            tabGarage.setDisable(false);
            tabGarage.setText("Garage");
        }
    }

    private void refreshSeedStore() {
        seedSelectionBox.getChildren().clear();
        for (Seed s : Seed.getCatalog()) {
            Button btn = new Button(s.getName() + " (" + s.getBuyPrice() + "€)");
            btn.setMaxWidth(Double.MAX_VALUE);

            if (myFarm.getLevel() < s.getMinLevel()) {
                btn.setDisable(true);
                btn.setText(s.getName() + " (Lvl " + s.getMinLevel() + ")");
            }

            if (selectedSeed != null && selectedSeed.getName().equals(s.getName())) {
                btn.setStyle("-fx-border-color: #2ecc71; -fx-border-width: 2px; -fx-background-color: #e8f8f5;");
            }

            btn.setOnAction(e -> {
                selectedSeed = s;
                System.out.println("Graine sélectionnée : " + s.getName());
                refreshSeedStore();
            });

            seedSelectionBox.getChildren().add(btn);
        }
    }

    private void handlePlotClick(int r, int c) {
        Plot plot = myFarm.getPlot(r, c);
        if (plot.isEmpty()) {
            if (myFarm.getWallet() >= selectedSeed.getBuyPrice()) {
                myFarm.subMoney((int)selectedSeed.getBuyPrice());
                myFarm.plantSeed(selectedSeed, r, c);
            }
        } else if (plot.isReady()) {
            myFarm.collectHarvest(r, c);
            refreshSeedStore();
        }
        refreshUI();
    }


    @FXML
    private void saveGame() {
        System.out.println("Jeu sauvegardé !");
    }

    @FXML
    private void debugAddMoney() {
        if (myFarm != null) {
            myFarm.addMoney(1000);
            refreshUI();
        }
    }

    @FXML
    private void debugResetMoney() {
        if (myFarm != null) {
            int currentWallet = (int) myFarm.getWallet();
            myFarm.subMoney(currentWallet);
            myFarm.addMoney(500);
            refreshUI();
        }
    }

    @FXML
    private void debugFastForward() {
        if (myFarm != null) {
            for (int r = 0; r < 4; r++) {
                for (int c = 0; c < 4; c++) {
                    Plot p = myFarm.getPlot(r, c);
                    if (p.getPlantedSeed() != null) p.setTimeLeft(0);
                }
            }
            refreshUI();
        }
    }

    @FXML
    private void debugLevelUp() {
        if (myFarm != null) {
            myFarm.addExp(100); 
            refreshSeedStore();
            refreshUI();
        }
    }

    @FXML
    private void debugUnlockAll() {
        if (myFarm != null) {
            myFarm.setLevel(20);
            myFarm.setWallet(1000000000);
            refreshSeedStore();
            refreshUI();
        }
    }
}