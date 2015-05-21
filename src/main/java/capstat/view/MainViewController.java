package capstat.view;
import capstat.application.LoginController;
import capstat.infrastructure.EventBus;
import capstat.infrastructure.NotifyEventListener;
import capstat.model.CapStat;
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
 */
public class MainViewController implements Initializable, NotifyEventListener{
    private LoginController lc = new LoginController();
    private CapStat cs = CapStat.getInstance();
    private EventBus eb = EventBus.getInstance();
    private Scene scene;
    private Parent root;
    @FXML Label userLabel, matchRegisteredLabel;
    @FXML Button playButton, statisticsButton, logoutButton;
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

    @FXML private void playPressed(){
        eb.notify(MainView.SETSCENE_MATCH);
    }
    @FXML private void logoutPressed(){
        lc.logoutUser();
        eb.notify(MainView.SETSCENE_LOGIN);
    }
    @FXML private void statisticsPressed(){
        eb.notify(MainView.SETSCENE_STATISTICS);
    }


    @Override
    public void notify(String event) {
        if(event.equals(MainView.MATCH_REGISTERED)){
            matchRegisteredLabel.setVisible(true);
        }

    }
}
