package controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import models.Farm;
import models.Plot;
import models.Seed;

public class FarmController {
    @FXML private GridPane farmGrid;

    Farm farm;
    MainController mainController;
    Seed selectSeed;

    public void init(Farm farm, MainController mainController) {
        this.farm = farm;
        this.mainController = mainController;
        this.selectSeed = Seed.getCatalog().get(0);
        refresh();
    }

    public void setSelectedSeed(Seed s) {
        this.selectSeed = s;
    }

    public void refresh() {
        if (farm == null) return;

        farmGrid.getChildren().clear();
        int size = farm.getGridSize();
        
        int totalDisplaySize = 500; 
        int btnSize = totalDisplaySize / size;

        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                Plot plot = farm.getPlot(r, c);
                Button btn = new Button();
                
                btn.setPrefSize(btnSize, btnSize);
                btn.setMinSize(btnSize, btnSize);
                btn.setMaxSize(btnSize, btnSize);

                if (plot.isEmpty()) {
                    btn.setText(size > 10 ? "" : "Vide");
                    btn.setStyle("-fx-border-color: #ccc; -fx-background-color: #dcdde1;");
                } else if (plot.isReady()) {
                    btn.setText(size > 10 ? "!" : "Prêt");
                    btn.setStyle("-fx-background-color: #4cd137; -fx-text-fill: white;");
                } else {
                    btn.setText(size > 10 ? "" : plot.getTimeLeft() + "s");
                    btn.setStyle("-fx-background-color: #e1b12c;");
                }

                int row = r;
                int col = c;
                btn.setOnAction(e -> handlePlotClick(row, col));

                farmGrid.add(btn, col, r);
            }
        }
    }

    public void handlePlotClick(int r, int c) {
        Plot plot = farm.getPlot(r, c);

        if (plot.isEmpty()) {
            if (farm.plantSeed(selectSeed, r, c)) {
                mainController.refreshAll();
            }
        } else if (plot.isReady()) {
            farm.collectHarvest(r, c);
            mainController.refreshAll();
        }
    }

    public void handleUpgradeGrid() {
        if (farm.upgradeGrid()) {
            System.out.println("Terrain agrandi avec succès");

            mainController.refreshAll();
        } else {
            System.out.println("Fonds insuffisants");
        }
    }
}
