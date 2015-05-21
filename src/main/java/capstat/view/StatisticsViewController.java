package capstat.view;

import capstat.application.LoginController;
import capstat.infrastructure.EventBus;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Jakob on 21/05/15.
 *
 * Class for the user to view statistics by plotting Charts using desired Data.
 */
public class StatisticsViewController implements Initializable{
    LoginController lc = new LoginController();
    EventBus eb = EventBus.getInstance();
    @FXML public void logoutPressed(){
        lc.logoutUser();
        eb.notify(MainView.SETSCENE_LOGIN);
    }

    @FXML public void returnToMain(){
        eb.notify(MainView.SETSCENE_MAIN);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
