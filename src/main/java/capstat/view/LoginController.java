package capstat.view;

import capstat.infrastructure.DataEventListener;
import capstat.infrastructure.EventBus;
import capstat.infrastructure.NotifyEventListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements NotifyEventListener, Initializable{
    private Scene scene;
    EventBus eb = EventBus.getInstance();
    @FXML PasswordField passField;
    @FXML Label registeredLabel;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eb.addNotifyEventListener(Main.USER_REGISTERED, this);
        registeredLabel.setVisible(false);
    }

    @FXML private void loginPressed(){
        //VALIDATE USER HERE, BEFORE CHANGING SCENE
        eb.notify(Main.SETSCENE_MAIN);
    }
    @FXML private void guestPressed(){
        //SET USER TO GUESTUSER
        eb.notify(Main.SETSCENE_MAIN);

    }
    @FXML private void registerPressed(){
        eb.notify(Main.SETSCENE_REGISTER);
    }
    @Override
    public void notify(String event) {
        if(event.equals(Main.USER_REGISTERED)){
            userRegistered();
        }
    }
    public void userRegistered(){
        registeredLabel.setVisible(true);
    }
}
