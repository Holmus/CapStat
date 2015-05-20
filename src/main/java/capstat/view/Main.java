package capstat.view;

import capstat.infrastructure.DataEventListener;
import capstat.infrastructure.EventBus;
import capstat.infrastructure.NotifyEventListener;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Jakob on 14/05/15.
 */
public class Main extends Application implements DataEventListener{
    public static final String CHANGE_SCENE = "Change scene";
    public static final String USER_REGISTERED = "User registered";
    EventBus eb = EventBus.getInstance();
    Stage stage;
    Parent root;
    Scene scene;
    public static void main (String [] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        eb.addDataEventListener(CHANGE_SCENE, this);
        root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        scene = new Scene(root, 600, 450);
        stage = primaryStage;
        stage.setTitle("CapStat");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void dataNotify(String event, Object data) {
        if(event.equals(CHANGE_SCENE)){
            changeScene(data);
        }
    }
    private void changeScene(Object scene){
        stage.setScene((Scene) scene);
    }
}
