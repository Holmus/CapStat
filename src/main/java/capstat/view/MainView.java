package capstat.view;

import capstat.infrastructure.eventbus.EventBus;
import capstat.infrastructure.eventbus.NotifyEventListener;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Jakob on 14/05/15.
 * @author Jakob
 * The MainView which launches the entire application and initializes the launchview
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

    /**
     * Method which launches the entire application
     * @param args
     */
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

    /**
     * Deals with incoming events from the EventBus, then takes proper action depending on event.
     * @param event the key of the event that occured
     */
    @Override
    public void notifyEvent(String event) {
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

    /**
     * Method which controllers use to change the active view
     * @param rootPath the path to the .fxml-file wanted to display
     */
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
