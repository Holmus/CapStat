package capstat.view;
import capstat.application.LoginController;
import capstat.infrastructure.eventbus.EventBus;
import capstat.infrastructure.eventbus.NotifyEventListener;
import capstat.model.user.LoggedInUser;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Jakob on 14/05/15.
 * Class to control and initialize the Main-view of the application
 */
public class MainViewController implements Initializable, NotifyEventListener{
    private LoginController lc = new LoginController();
    private LoggedInUser cs = LoggedInUser.getInstance();
    private EventBus eb = EventBus.getInstance();
    private Scene scene;
    private Parent root;
    @FXML Label userLabel, matchRegisteredLabel;
    @FXML Button playButton, statisticsButton, logoutButton;

    /**
     * Sets the default behaviour and display of elements initializing the view.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eb.addNotifyEventListener(MainView.MATCH_REGISTERED, this);
        userLabel.setText(cs.getLoggedInUser().getNickname());
        matchRegisteredLabel.setVisible(false);
        Platform.runLater(() -> {
            playButton.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.P), () -> {
                playPressed();
            });
        });
        Platform.runLater(() -> {
            statisticsButton.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.S), () -> {
                statisticsPressed();
            });
        });
        Platform.runLater(() -> {
            logoutButton.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.L), () -> {
                logoutPressed();
            });
        });
        Platform.runLater(() -> {
            logoutButton.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.ESCAPE), () -> {
                logoutPressed();
            });
        });
    }

    /**
     * Triggered when the playbutton is pressed, sends a notification to the EventBus
     */
    @FXML private void playPressed(){
        eb.notify(MainView.SETSCENE_MATCH);
    }
    /**
     * Triggered when the logoutbutton is pressed, sends a notification to the EventBus
     */
    @FXML private void logoutPressed(){
        lc.logoutUser();
        eb.notify(MainView.SETSCENE_LOGIN);
    }
    /**
     * Triggered when the statisticsbutton is pressed, sends a notification to the EventBus
     */
    @FXML private void statisticsPressed(){
        eb.notify(MainView.SETSCENE_STATISTICS);
    }

     /**
     * Deals with incoming events from the EventBus, then takes proper action depending on event.
     * @param event the key of the event that occured
     */
    @Override
    public void notifyEvent(String event) {
        if(event.equals(MainView.MATCH_REGISTERED)){
            matchRegisteredLabel.setVisible(true);
        }

    }
}
