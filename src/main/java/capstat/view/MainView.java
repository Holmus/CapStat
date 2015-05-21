package capstat.view;

import capstat.infrastructure.DataEventListener;
import capstat.infrastructure.EventBus;
import capstat.infrastructure.NotifyEventListener;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Jakob on 14/05/15.
 */
public class MainView extends Application implements NotifyEventListener{
    public static final String USER_REGISTERED = "New user registered";
    public static final String SETSCENE_LOGIN = "/fxml/login.fxml";
    public static final String SETSCENE_MAIN = "/fxml/main.fxml";
    public static final String SETSCENE_MATCH = "/fxml/match.fxml";
    public static final String SETSCENE_REGISTER = "/fxml/register.fxml";
    public static final String SETSCENE_STATISTICS = "/fxml/statistics.fxml";
    public static final String MATCH_REGISTERED = "Match registered";
    EventBus eb = EventBus.getInstance();
    Stage stage;
    Parent root;
    Scene scene;
    public static void start(String [] args){
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        eb.addNotifyEventListener(SETSCENE_LOGIN, this);
        eb.addNotifyEventListener(SETSCENE_MAIN, this);
        eb.addNotifyEventListener(SETSCENE_MATCH, this);
        eb.addNotifyEventListener(SETSCENE_REGISTER, this);
        eb.addNotifyEventListener(SETSCENE_STATISTICS, this);
        root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        scene = new Scene(root, 600, 450);
        stage = primaryStage;
        stage.setTitle("CapStat");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void notify(String event) {
        if(event.equals(SETSCENE_LOGIN)){
            changeScene(event);
        }
        if(event.equals(SETSCENE_MAIN)){
            changeScene(event);
        }
        if(event.equals(SETSCENE_MATCH)){
            changeScene(event);
        }
        if(event.equals(SETSCENE_REGISTER)){
            changeScene(event);
        }
        if(event.equals(SETSCENE_STATISTICS)) {
            changeScene(event);
        }
    }
    private void changeScene(String rootPath){
        try{
            scene = new Scene(FXMLLoader.load(getClass().getResource(rootPath)), 600, 450);
            stage.setScene(scene);
        } catch (IOException e){
            //Scene will remain unchanged if the new scene root cant be found
            e.printStackTrace();
            stage.setScene(scene);
        }
    }
}
