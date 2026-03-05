package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import models.Farm;

public class ProgressionController {
    @FXML private Label walletLabel;
    @FXML private Label levelLabel;
    @FXML private ProgressBar xpBar;

    Farm farm;

    public void init(Farm farm) {
        this.farm = farm;
        refresh();
    }

    public void refresh() {
        if (farm == null) return;
        walletLabel.setText(String.format("%.2f €", farm.getWallet()));
        
        int lv = farm.getProgression().getLevel();
        int exp = farm.getProgression().getExp();
        int next = farm.getProgression().getNextLevelThreshold();
        
        levelLabel.setText("Niveau " + lv);
        xpBar.setProgress((double) exp / next);
    }
}
