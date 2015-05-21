package capstat.view;

import capstat.application.LoginController;
import capstat.infrastructure.EventBus;
import capstat.infrastructure.NotifyEventListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginViewController implements NotifyEventListener, Initializable{
    private Scene scene;
    EventBus eb = EventBus.getInstance();
    LoginController lc = new LoginController();
    @FXML PasswordField passField;
    @FXML TextField usernameField;
    @FXML Label registeredLabel;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eb.addNotifyEventListener(MainView.USER_REGISTERED, this);
        registeredLabel.setVisible(false);
    }

    @FXML private void loginPressed(){
        if(usernameField.getText().isEmpty() || passField.getText().isEmpty()){
            //ToDo: Give visual feedback
            return;
        }
        if(lc.loginAsUser(usernameField.getText(), passField.getText())){
            eb.notify(MainView.SETSCENE_MAIN);
        }
    }
    @FXML private void guestPressed(){
        lc.loginAsGuest();
        eb.notify(MainView.SETSCENE_MAIN);

    }
    @FXML private void registerPressed(){
        eb.notify(MainView.SETSCENE_REGISTER);
    }
    @Override
    public void notify(String event) {
        if(event.equals(MainView.USER_REGISTERED)){
            userRegistered();
        }
    }
    public void userRegistered(){
        registeredLabel.setVisible(true);
    }
}
