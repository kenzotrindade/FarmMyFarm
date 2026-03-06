package controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import models.Animal;
import models.Enclosure;

public class EnclosureController {

    @FXML private FlowPane enclosurePane;
    private Enclosure myEnclosure;
    private MainController mainController;

    public void init(Enclosure enclosure, MainController main) {
        this.myEnclosure = enclosure;
        this.mainController = main;
        refresh();
    }

    public void refresh() {
        if (myEnclosure == null || enclosurePane == null) return;
        enclosurePane.getChildren().clear();

        for (Animal animal : myEnclosure.getAnimals()) {
            enclosurePane.getChildren().add(createAnimalCard(animal));
        }

        int emptySlots = myEnclosure.getMaxCapacity() - myEnclosure.getAnimals().size();
        for (int i = 0; i < emptySlots; i++) {
            VBox slot = createEmptySlot();
            addSlotEvents(slot);
            enclosurePane.getChildren().add(slot);
        }
    }

    private VBox createAnimalCard(Animal animal) {
        VBox card = new VBox(8);
        card.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-border-color: #2e7d32; -fx-border-width: 2; -fx-border-radius: 10; -fx-background-radius: 10; -fx-alignment: CENTER;");
        card.setPrefSize(140, 200);

        Label label = new Label(animal.getName() + " (" + animal.getType() + ")");
        label.setStyle("-fx-font-weight: bold;");
        
        String timeText = animal.isReadyToProduce() ? "Prêt !" : "Temps : " + (animal.getHunger() > 0 ? "En cours" : "Affamé");
        Label statusLabel = new Label(timeText);
        
        ProgressBar hunger = new ProgressBar(animal.getHunger() / 100.0);
        hunger.setPrefWidth(100);

        Button feedBtn = new Button("Nourrir");
        feedBtn.setMaxWidth(Double.MAX_VALUE);
        
        feedBtn.setOnAction(e -> {
            if (mainController.farm.getInventory().getAmount("Wheat") > 0) {
                mainController.farm.getInventory().remove("Wheat", 1);
                animal.setHunger(100);
                mainController.refreshAll();
            }
        });

        card.getChildren().addAll(label, statusLabel, hunger, feedBtn);
        return card;
    }

    private VBox createEmptySlot() {
        VBox card = new VBox();
        card.setStyle("-fx-background-color: rgba(200, 200, 200, 0.2); -fx-border-color: #ccc; -fx-border-style: dashed; -fx-border-width: 2; -fx-border-radius: 10; -fx-background-radius: 10; -fx-alignment: CENTER;");
        card.setPrefSize(140, 200);
        
        Label label = new Label("Emplacement\nLibre");
        label.setStyle("-fx-text-fill: #999; -fx-text-alignment: CENTER;");
        
        card.getChildren().add(label);
        return card;
    }

    private void addSlotEvents(VBox slot) {
        slot.setOnMouseDragEntered(e -> processAnimalPlacement());
        slot.setOnMousePressed(e -> processAnimalPlacement());
    }

    private void processAnimalPlacement() {
        String selected = mainController.getSelectedItem();
        if (selected == null) return;

        boolean isAnimal = selected.equalsIgnoreCase("Vache") || 
                           selected.equalsIgnoreCase("Cochon") || 
                           selected.equalsIgnoreCase("Mouton") ||
                           selected.equalsIgnoreCase("Cow") ||
                           selected.equalsIgnoreCase("Pig") ||
                           selected.equalsIgnoreCase("Sheep");

        if (isAnimal) {
            if (mainController.farm.getInventory().getAmount(selected) > 0) {
                Animal newAnimal = new Animal(selected, selected, 60);
                if (myEnclosure.addAnimal(newAnimal)) {
                    mainController.farm.getInventory().remove(selected, 1);
                    mainController.refreshAll();
                }
            }
        }
    }
}