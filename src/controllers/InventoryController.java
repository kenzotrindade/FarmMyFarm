package controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import models.Farm;
import models.Seed;
import java.util.Map;

public class InventoryController {
    @FXML VBox itemsContainer;

    Farm farm;
    MainController mainController;

    public void init(Farm farm, MainController main) {
        this.farm = farm;
        this.mainController = main;
        refresh();
    }

    public void refresh() {
        if (farm == null || itemsContainer == null) return;
        
        itemsContainer.getChildren().clear();

        Map<String, Integer> items = farm.getInventory().getItems();
        
        if (items.isEmpty()) {
            Label empty = new Label("Inventaire vide...");
            empty.setStyle("-fx-font-style: italic; -fx-text-fill: #888;");
            itemsContainer.getChildren().add(empty);
        } else {
            for (Map.Entry<String, Integer> entry : items.entrySet()) {
                if (entry.getValue() > 0) {
                    Label itemLabel = new Label("📦 " + entry.getKey() + " x" + entry.getValue());
                    itemLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 13px;");
                    itemsContainer.getChildren().add(itemLabel);
                }
            }
        }
    }

    @FXML
    private void handleSellAll() {
        for (Seed s : Seed.getCatalog()) {
            String name = s.getName();
            int qty = farm.getInventory().getItems().getOrDefault(name, 0);
            
            if (qty > 0) {
                for (int i = 0; i < qty; i++) {
                    farm.sellItem(name, s.sellPrice);
                }
            }
        }
        mainController.refreshAll();
    }
}