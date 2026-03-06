package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.Farm;
import controllers.MainController;
import services.SaveManager;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
            BorderPane root = loader.load();

            Farm myFarm = SaveManager.loadFarm();

            MainController controller = loader.getController();
            controller.init(myFarm, myFarm.getEnclosure());

            Scene scene = new Scene(root);
            primaryStage.setTitle("FarmMyFarm");
            primaryStage.setScene(scene);

            primaryStage.setOnCloseRequest(event -> {
                SaveManager.saveFarm(myFarm);
            });

            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}