package controllers;

import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import models.*;

public class FarmController {

    @FXML private GridPane farmGrid;

    private Farm myFarm;
    private MainController mainController;

    public void init(Farm farm, MainController mainController) {
        this.myFarm = farm;
        this.mainController = mainController;
        refresh();
    }

    public void refresh() {
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
                    btn.setText("RÉCOLTER\n" + plot.getPlantedSeed().getName());
                    btn.setStyle("-fx-background-color: #90EE90; -fx-font-weight: bold; -fx-text-alignment: center;");
                } else {
                    btn.setText(plot.getPlantedSeed().getName() + "\n" + plot.getTimeLeft() + "s");
                    btn.setStyle("-fx-font-weight: bold; -fx-text-alignment: center;");
                }

                addPlotEvents(btn, r, c);
                
                farmGrid.add(btn, c, r);
            }
        }
    }

    private void addPlotEvents(Button btn, int row, int col) {
        btn.setOnDragDetected(e -> btn.startFullDrag());

        btn.setOnMousePressed(e -> processPlacement(row, col));

        btn.setOnMouseDragEntered(e -> processPlacement(row, col));
    }

    private void processPlacement(int row, int col) {
        String selected = mainController.getSelectedItem();
        Plot plot = myFarm.getPlot(row, col);

        if (!plot.isEmpty() && plot.isReady()) {
            myFarm.collectHarvest(row, col);
            mainController.refreshAll();
        } 
        else if (plot.isEmpty() && selected != null) {
            if (myFarm.getInventory().getAmount(selected) > 0) {
                Seed seedToPlant = null;
                for (Seed s : Seed.getCatalog()) {
                    if (s.getName().equalsIgnoreCase(selected)) {
                        seedToPlant = s;
                        break;
                    }
                }

                if (seedToPlant != null) {
                    if (myFarm.plantSeed(seedToPlant, row, col)) {
                        myFarm.getInventory().remove(selected, 1);
                        mainController.refreshAll();
                    }
                }
            }
        }
    }

    public void setSelectedSeed(Seed s) {
        if (s != null) {
            mainController.setSelectedItem(s.getName());
        }
    }
}