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

    private String selectedItemName = null;

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

                String itemName = entry.getKey();

                if (entry.getValue() > 0) {
                    Button itemBtn = new Button("📦 " + entry.getKey() + " x" + entry.getValue());
                    itemBtn.setMaxWidth(Double.MAX_VALUE);

                    String baseStyle = "-fx-font-weight: bold; -fx-cursor: hand; -fx-background-color: #ffffff; -fx-border-color: #ddd; -fx-border-width: 2;";
                    String activeStyle = baseStyle + "-fx-border-color: #2ecc71; -fx-background-color: #e8f8f5;";
                    
                    if (itemName.equals(selectedItemName)) {
                        itemBtn.setStyle(activeStyle);
                    } else {
                        itemBtn.setStyle(baseStyle);
                    }

                    itemBtn.setOnAction(e -> {
                        selectedItemName = itemName;

                        mainController.setSelectedItem(itemName);

                        refresh();
                    });
                    
                    itemsContainer.getChildren().add(itemBtn);
                }
            }
        }
    }

    @FXML
    private void handleSellAll() {
        for (Seed s : Seed.getCatalog()) {
            String name = s.getSeedName();
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