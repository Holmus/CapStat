package capstat.controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Jakob on 14/05/15.
 */
public class Main extends Application{
    Stage stage;
    Parent root;
    Scene scene;
    public static void main (String [] args){
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        scene = new Scene(root, 600, 450);
        stage = primaryStage;
        stage.setTitle("CapStat");
        stage.setScene(scene);
        stage.show();
    }
}
