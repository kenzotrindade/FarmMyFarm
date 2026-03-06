package controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import models.Farm;
import models.Seed;
import models.Animal;
import models.Enclosure;

import java.util.Optional;
import javax.security.auth.callback.TextInputCallback;

public class ShopController {
    @FXML private VBox seedButtonContainer;

    private Farm farm;
    private Enclosure enclosure;
    private MainController mainController;

    public void init(Farm farm, Enclosure enclosure, MainController mainController) {
        this.farm = farm;
        this.enclosure = enclosure;
        this.mainController = mainController;
        refresh();
    }

    public void refresh() {
        if (farm == null || seedButtonContainer == null) return;
        seedButtonContainer.getChildren().clear();

        seedButtonContainer.getChildren().add(new Label("🌱 GRAINES"));
        for (Seed s : Seed.getCatalog()) {
            Button btn = new Button(s.getSeedName() + " (" + s.getBuyPrice() + " €)");
            btn.setMaxWidth(Double.MAX_VALUE);

            if (farm.getProgression().getLevel() < s.getMinLevel()) {
                btn.setDisable(true);
                btn.setText("🔒 " + s.getSeedName() + " (Niv. " + s.getMinLevel() + ")");
            }

            btn.setOnAction(e -> {
                TextInputDialog dialog = new TextInputDialog("1");
                dialog.setTitle("Achat de graines");
                dialog.setHeaderText("Acheter : " + s.getSeedName());
                dialog.setContentText("Quantité : ");

                dialog.showAndWait().ifPresent(qtyString -> {
                    try {
                        int quantity = Integer.parseInt(qtyString);
                        if (quantity > 0) {
                            double totalPrice = s.getBuyPrice() * quantity;
                            if (farm.getWallet() >= totalPrice) {
                                farm.setWallet((int)(farm.getWallet() - totalPrice));
                                farm.getInventory().add(s.getSeedName(), quantity);
                                mainController.refreshAll();
                            } else {
                                showWarning("Pas assez d'argent");
                            }
                        }
                    } catch (NumberFormatException ex) {
                        showError("Veuillez entrer un nombre valide");
                    }
                });
            });
            seedButtonContainer.getChildren().add(btn);
        }

        seedButtonContainer.getChildren().add(new Separator());

        seedButtonContainer.getChildren().add(new Label("🐄 ANIMAUX"));
        for (Animal a : Animal.getCatalog()) {
            double pricePerUnit = 100.0; 
            Button btn = new Button(a.getType() + " (" + pricePerUnit + " €)");
            btn.setMaxWidth(Double.MAX_VALUE);

            if (farm.getWallet() < pricePerUnit) {
                btn.setDisable(true);
            }
            btn.setOnAction(e -> {
                TextInputDialog dialog = new TextInputDialog("1");
                dialog.setTitle("Achat d'animaux");
                dialog.setHeaderText("Acheter : " + a.getType());
                dialog.setContentText("Quantité :");

                dialog.showAndWait().ifPresent(qtyString -> {
                    try {
                        int quantity = Integer.parseInt(qtyString);
                        if (quantity > 0) {
                            double totalprice = pricePerUnit * quantity;
                            if (farm.getWallet() >= totalprice) {
                                farm.setWallet((int)(farm.getWallet() - totalprice));
                                farm.getInventory().add(a.getType(), quantity);
                                mainController.refreshAll();
                            } else {
                                showWarning("Pas assez d'argent !");
                            }
                        }
                    } catch (NumberFormatException ex) {
                        showError("Veuillez entrer un nombre valide.");
                    }
                });
            });
            seedButtonContainer.getChildren().add(btn);
        }

        seedButtonContainer.getChildren().add(new Separator());

        double cost = farm.getGridSize() * 1000;
        Button buyLandBtn = new Button("Agrandir Terrain (" + (int)cost + " €)");
        buyLandBtn.setMaxWidth(Double.MAX_VALUE);
        buyLandBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");

        buyLandBtn.setOnAction(e -> {
            if (farm.upgradeGrid()) {
                mainController.refreshAll();
            }
        });
        seedButtonContainer.getChildren().add(buyLandBtn);
    }

    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING, message);
        alert.setHeaderText(null);
        alert.setTitle("Attention");
        alert.show();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.setHeaderText(null);
        alert.setTitle("Erreur");
        alert.show();
    }
}