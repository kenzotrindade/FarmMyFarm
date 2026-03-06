package controllers;

import java.util.Map;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import models.*;
import services.SaveManager;

public class FarmController {

    @FXML private GridPane farmGrid;

    private Farm myFarm;
    private Seed selectedSeed;
    public MainController mainController;

    @FXML
    public void initialize() {
        List<Seed> catalog = Seed.getCatalog();
        if (!catalog.isEmpty()) {
            this.selectedSeed = catalog.get(0);
        }
    }

    public void init(Farm farm, MainController mainController) {
        this.myFarm = farm;
        this.mainController = mainController;
        refresh();
    }

    public void refresh() {
        if (myFarm == null) return;
        refreshUI();
    }

    public void refreshUI() {
        if (myFarm == null || farmGrid == null) return;

        farmGrid.getChildren().clear();
        int gridSize = myFarm.getGridSize();
        for (int r = 0; r < gridSize; r++) {
            for (int c = 0; c < gridSize; c++) {
                Plot plot = myFarm.getPlot(r, c);
                Button btn = new Button();
                btn.setPrefSize(100, 100);

                if (plot.isEmpty()) {
                    btn.setText("Vide");
                    btn.setStyle("-fx-background-color: #a0522d; -fx-text-fill: white; -fx-font-weight: bold;");
                } else if (plot.isReady()) {
                    btn.setText("RÉCOLTER\n(" + plot.getPlantedSeed().getName() + ")");
                    btn.setStyle("-fx-background-color: #90EE90; -fx-font-weight: bold;");
                } else {
                    btn.setText(plot.getPlantedSeed().getName() + "\n" + plot.getTimeLeft() + "s");
                    btn.setStyle("-fx-font-weight: bold;");
                }

                int row = r;
                int col = c;
                btn.setOnAction(e -> handlePlotClick(row, col));
                farmGrid.add(btn, col, r);
            }
        }
    }

    private void handlePlotClick(int r, int c) {
        Plot plot = myFarm.getPlot(r, c);
        if (plot.isEmpty()) {
            if (myFarm.plantSeed(selectedSeed, r, c)) {
                mainController.refreshAll();
            }
        } else if (plot.isReady()) {
            myFarm.collectHarvest(r, c);
            mainController.refreshAll();
        }
    }

    public void setSelectedSeed(Seed s) {
        selectedSeed = s;
    }
}