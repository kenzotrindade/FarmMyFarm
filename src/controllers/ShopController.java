package controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import models.Farm;
import models.Seed;

public class ShopController {
    @FXML private VBox seedButtonContainer;

    Farm farm;
    FarmController farmController;

    public void init(Farm farm, FarmController farmController) {
        this.farm = farm;
        this.farmController = farmController;
        refresh();
    }

    public void refresh() {
        if (farm == null || seedButtonContainer == null) return;

        seedButtonContainer.getChildren().clear();

        for (Seed s : Seed.getCatalog()) {
            Button btn = new Button(s.getName() + " (" + s.getBuyPrice() + " €)");
            btn.setMaxWidth(Double.MAX_VALUE);

            if (farm.getProgression().getLevel() < s.getMinLevel()) {
                btn.setDisable(true);
                btn.setText("🔒 " + s.getName() + " (Niv. " + s.getMinLevel() + ")");
            }

            btn.setOnAction(e -> {
                farmController.setSelectedSeed(s);
                System.out.println("Seed selected : " + s.getName());
            });

            seedButtonContainer.getChildren().add(btn);
        }

        double cost = farm.getGridSize() * 1000;
        Button buyLandBtn = new Button("Agrandir Terrain (" + (int)cost + " €)");
        buyLandBtn.setMaxWidth(Double.MAX_VALUE);
        buyLandBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-margin-top: 10;");

        int nextSize = farm.getGridSize() + 1;
        int requiredLevel = (nextSize - 4) * 2;

        if (farm.getWallet() < cost || farm.getProgression().getLevel() < requiredLevel) {
            buyLandBtn.setDisable(true);
            if (farm.getProgression().getLevel() < requiredLevel) {
                buyLandBtn.setText("🔒 Agrandir (Niv. " + requiredLevel + ")");
            }
        }

        buyLandBtn.setOnAction(e -> {
            if (farm.upgradeGrid()) {
                if (farmController != null && farmController.mainController != null) {
                    farmController.mainController.refreshAll(); 
                }
            }
        });

        seedButtonContainer.getChildren().add(buyLandBtn);
    }
}