package capstat.view;
import capstat.infrastructure.EventBus;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * Created by Jakob on 14/05/15.
 */
public class MainViewController {
    private EventBus eb = EventBus.getInstance();
    private Scene scene;
    private Parent root;

    @FXML private void playPressed(){
        eb.notify(MainView.SETSCENE_MATCH);
    }
}
