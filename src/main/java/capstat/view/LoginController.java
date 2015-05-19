package capstat.view;

import capstat.infrastructure.DataEventListener;
import capstat.infrastructure.EventBus;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;

import java.io.IOException;

public class LoginController implements DataEventListener{
    private Scene scene;
    EventBus eb = EventBus.getInstance();
    private String SCENE_CHANGED = MainController.SCENE_CHANGED;

    @FXML PasswordField passField;
    @FXML private void loginPressed() throws IOException {
        //VALIDATE USER HERE, BEFORE CHANGING SCENE
        scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/main.fxml")), 600, 450);
        eb.dataNotify(SCENE_CHANGED, scene);
    }
    @FXML private void guestPressed() throws IOException {
        //SET USER TO GUESTUSER
        scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/main.fxml")), 600, 450);
        eb.dataNotify(SCENE_CHANGED, scene);

    }
    @FXML private void registerPressed() throws IOException {
        scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/register.fxml")), 600, 450);
        eb.dataNotify(SCENE_CHANGED, scene);
    }

    @Override
    public void dataNotify(String event, Object data) {

    }
}
