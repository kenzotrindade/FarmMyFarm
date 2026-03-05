package controllers;

import javafx.fxml.FXML;
import models.Farm;
import services.SaveManager;

public class DebugController {
    private Farm farm;
    private MainController mainController;

    public void init(Farm farm, MainController main) {
        this.farm = farm;
        this.mainController = main;
    }

    @FXML
    private void addMoneyDebug() {
        farm.addMoney(100000000);
        mainController.refreshAll();
    }

    @FXML
    private void addExpDebug() {
        farm.getProgression().addExp(500);
        mainController.refreshAll();
    }

    @FXML
    private void setLevel20() {
        while (farm.getProgression().getLevel() < 20) {
            farm.getProgression().addExp(100);
        }
        mainController.refreshAll();
    }

    @FXML
    private void speedUpTime() {
        for (int i = 0; i < 10; i++) {
            farm.update();
        }
        mainController.refreshAll();
    }

    @FXML
    public void handleUpgradeGrid() {
        System.out.println("--- TENTATIVE D'AGRANDISSEMENT ---");
        if (farm == null) {
            System.out.println("ERREUR : L'objet 'farm' est null dans le contrôleur !");
            return;
        }

        double moneyBefore = farm.getWallet();
        int sizeBefore = farm.getGridSize();
        
        boolean success = farm.upgradeGrid();
        
        if (success) {
            System.out.println("SUCCÈS !");
            System.out.println("Argent : " + moneyBefore + " -> " + farm.getWallet());
            System.out.println("Taille : " + sizeBefore + " -> " + farm.getGridSize());
            mainController.refreshAll();
        } else {
            System.out.println("ÉCHEC : Vérifie l'argent (" + farm.getWallet() + ") ou si MAX_SIZE est atteint.");
        }
    }

    @FXML
    public void handleSave() {
        services.SaveManager.saveFarm(farm);
        System.out.println("Partie sauvegarder");
    }

    @FXML
    public void handleLoad() {
        models.Farm loadedFarm = services.SaveManager.loadFarm();
        if (loadedFarm != null) {
            farm = loadedFarm;

            mainController.init(farm);
            mainController.refreshAll();
            System.out.println("Partie chargée");
        } 
    }
}