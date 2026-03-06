package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import models.Enclosure;
import models.Farm;
import controllers.MainController;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
            BorderPane root = loader.load();

            Farm myFarm = new Farm();
            Enclosure myEnclosure = new Enclosure();

            MainController controller = loader.getController();
            controller.init(myFarm, myEnclosure);

            Scene scene = new Scene(root);
            primaryStage.setTitle("Ma Super Ferme 20x20");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}