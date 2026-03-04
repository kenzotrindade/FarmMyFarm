package controllers;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import models.*;

public class FarmController {

    @FXML private GridPane farmGrid;
    @FXML private Label walletLabel;

    private Farm myFarm = new Farm();
    private Button[][] buttonGrid = new Button[4][4];
    
    private Seed selectedSeed = new Seed("Ble", 10, 10, 25);

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
        walletLabel.setText("Argent : " + myFarm.getWallet() + " €");
        
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