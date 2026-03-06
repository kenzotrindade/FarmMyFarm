package controllers;

import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import models.*;
import javafx.scene.Node;

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

        int gridSize = myFarm.getGridSize();
        
        if (farmGrid.getChildren().isEmpty()) {
            for (int r = 0; r < gridSize; r++) {
                for (int c = 0; c < gridSize; c++) {
                    Button btn = new Button();
                    btn.setPrefSize(100, 100);
                    addPlotEvents(btn, r, c);

                    farmGrid.add(btn, c, r);
                }
            }
        }

        for (Node node : farmGrid.getChildren()) {
            if (node instanceof Button) {
                Button btn = (Button) node;

                Integer col = GridPane.getColumnIndex(node);
                Integer row = GridPane.getRowIndex(node);

                if (row != null && col != null) {
                    Plot plot = myFarm.getPlot(row, col);
                    updateSingleButton(btn, plot);
                }
            }
        }
    }

    private void addPlotEvents(Button btn, int row, int col) {
        btn.setOnDragDetected(e -> {
            if (mainController.getSelectedItem() != null) {
                btn.startFullDrag();
            }
        });

        btn.setOnMousePressed(e -> {
            processPlacement(row, col, btn);
        });

        btn.setOnMouseDragEntered(e -> {
            processPlacement(row, col, btn);
        });
    }

    private void processPlacement(int row, int col, Button btn) {
        String selected = mainController.getSelectedItem();
        Plot plot = myFarm.getPlot(row, col);
        boolean actionDone = false;

        if (!plot.isEmpty() && plot.isReady()) {
            myFarm.collectHarvest(row, col);
            actionDone = true;
        } 
        else if (plot.isEmpty() && selected != null) {
            if (myFarm.getInventory().getAmount(selected) > 0) {
                Seed seedToPlant = null;
                for (Seed s : Seed.getCatalog()) {
                    if (s.getSeedName().equalsIgnoreCase(selected)) {
                        seedToPlant = s;
                        break;
                    }
                }

                if (seedToPlant != null) {
                    if (myFarm.plantSeed(seedToPlant, row, col)) {
                        myFarm.getInventory().remove(selected, 1);
                        actionDone = true;
                    }
                }
            }
        }

        if (actionDone) {
            updateSingleButton(btn, plot);
            mainController.refreshAll();
        }
    }

    public void setSelectedSeed(Seed s) {
        if (s != null) {
            mainController.setSelectedItem(s.getSeedName());
        }
    }

    private void updateSingleButton(Button btn, Plot plot) {
        if (plot.isEmpty()) {
            btn.setText("Vide");
            btn.setStyle("-fx-background-color: #a0522d; -fx-text-fill: white; -fx-font-weight: bold;");
        } else if (plot.isReady()) {
            btn.setText("RÉCOLTER\n" + plot.getPlantedSeed().getSeedName());
            btn.setStyle("-fx-background-color: #90EE90; -fx-font-weight: bold; -fx-text-alignment: center;");
        } else {
            btn.setText(plot.getPlantedSeed().getSeedName() + "\n" + plot.getTimeLeft() + "s");
            btn.setStyle("-fx-font-weight: bold; -fx-text-alignment: center;");
        }
    }
}