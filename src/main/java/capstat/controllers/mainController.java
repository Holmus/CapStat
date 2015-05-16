package capstat.controllers;
import capstat.controllers.Main;
import capstat.infrastructure.DataEventListener;
import capstat.infrastructure.EventBus;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Jakob on 14/05/15.
 */
public class MainController implements DataEventListener{
    private EventBus eb = EventBus.getInstance();
    private Scene scene;
    private Parent root;
    public static final String SCENE_CHANGED = "New Scene";

    @Override
    public void dataNotify(String event, Object data){
        if(event.equals(SCENE_CHANGED)){
        }
    }
}
