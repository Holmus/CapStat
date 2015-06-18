package capstat.view;

import capstat.application.LoginController;
import capstat.infrastructure.eventbus.EventBus;
import capstat.infrastructure.eventbus.NotifyEventListener;
import capstat.model.user.UserLedger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Jakob
 * A class to control the elements and data displayed in the LoginView (.FXML-file), uses LoginController in the
 * application layer to properly follow layered architecture
 */

public class LoginViewController implements NotifyEventListener, Initializable{
    private Scene scene;
    EventBus eb = EventBus.getInstance();
    LoginController lc = new LoginController();
    UserLedger ul = UserLedger.getInstance();
    @FXML PasswordField passField;
    @FXML TextField usernameField;
    @FXML Label registeredLabel, passwordLabel, usernameLabel;
    @FXML Button loginButton;

    /**
     * Sets the default behaviour and display of elements initializing the view.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eb.addNotifyEventListener(MainView.USER_REGISTERED, this);
        registeredLabel.setVisible(false);
        passwordLabel.setVisible(false);
        usernameLabel.setVisible(false);

        Platform.runLater(() -> {
            loginButton.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.ENTER), () -> {
                loginPressed();
            });
        });
    }

    /**
     * Method which runs when loginbutton is pressed, logs the user in and displays wrong input
     *
     */
    @FXML private void loginPressed(){
        usernameLabel.setVisible(false);
        passwordLabel.setVisible(false);
        usernameLabel.setText("No such user");
        passwordLabel.setText("Wrong password!");
        if(usernameField.getText().isEmpty()){
            usernameLabel.setText("Enter a username!");
            usernameLabel.setVisible(true);
        }
        if(passField.getText().isEmpty()){
            passwordLabel.setText("Enter a password!");
            passwordLabel.setVisible(true);
        }
        if(passField.getText().isEmpty() || usernameField.getText().isEmpty()){
            return;
        }
        if(!ul.doesUserExist(usernameField.getText())){
            usernameLabel.setVisible(true);
            return;      
        }
        if(lc.loginAsUser(usernameField.getText(), passField.getText())){
            eb.notify(MainView.SETSCENE_MAIN);
        } else {
            if(!usernameLabel.isVisible()) {
                passwordLabel.setVisible(true);
            }
        }
        }

    /**
     * Method called when login as guest is pressed, logs in the user as a temporary guest user
     */
    @FXML private void guestPressed(){
        lc.loginAsGuest();
        eb.notify(MainView.SETSCENE_MAIN);

    }

    /**
     * Method called when register button is pressed, opens the register view where the user can get registered
     *
     */
    @FXML private void registerPressed(){
        eb.notify(MainView.SETSCENE_REGISTER);
    }

    /**
     * Deals with incoming events from the EventBus, then takes proper action depending on event.
     * @param event the key of the event that occured
     */
    @Override
    public void notifyEvent(String event) {
        if(event.equals(MainView.USER_REGISTERED)){
            userRegistered();
        }
    }

    /**
     * Sets the registeredLabel to visible when a new user has just been registered
     *
     */
    public void userRegistered(){
        registeredLabel.setVisible(true);
    }
}
