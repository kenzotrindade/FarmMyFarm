package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Farm;
import services.SaveManager;
import controllers.FarmController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Farm myFarm = SaveManager.loadFarm();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainView.fxml"));
        Parent root = loader.load();

        FarmController controller = loader.getController();

        controller.setFarm(myFarm);
        
        primaryStage.setTitle("Farm My Farm - MVP");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}