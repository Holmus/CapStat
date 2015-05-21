package capstat.view;
import capstat.application.LoginController;
import capstat.infrastructure.EventBus;
import capstat.model.CapStat;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Jakob on 14/05/15.
 */
public class MainViewController implements Initializable{
    private LoginController lc = new LoginController();
    private CapStat cs = CapStat.getInstance();
    private EventBus eb = EventBus.getInstance();
    private Scene scene;
    private Parent root;
    @FXML Label userLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userLabel.setText(cs.getLoggedInUser().getNickname());
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


}
