package controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
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
                    Button itemBtn = new Button("📦 " + entry.getKey() + " x" + entry.getValue());
                    itemBtn.setMaxWidth(Double.MAX_VALUE);
                    itemBtn.setStyle("-fx-font-weight: bold; -fx-cursor: hand;");
                    
                    itemBtn.setOnAction(e -> {
                        mainController.setSelectedItem(entry.getKey());
                    });
                    
                    itemsContainer.getChildren().add(itemBtn);
                }
            }
        }
    }

    @FXML
    private void handleSellAll() {
        for (Seed s : Seed.getCatalog()) {
            String name = s.getName();
            int qty = farm.getInventory().getAmount(name);
            
            if (qty > 0) {
                double totalGain = qty * s.getSellPrice();
                farm.setWallet((int)(farm.getWallet() + totalGain));
                farm.getInventory().remove(name, qty);
            }
        }
        mainController.refreshAll();
    }
}