package capstat.view;

import capstat.infrastructure.EventBus;
import capstat.model.Admittance;
import capstat.model.Birthday;
import capstat.model.ChalmersAge;
import capstat.model.UserLedger;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

/**
 * Created by Jakob on 16/05/15.
 */
//ToDo: On input username check if it's available using ul.isNicknameValid();

public class RegisterController {
    EventBus eb = EventBus.getInstance();
    UserLedger ul = UserLedger.getInstance();
    Scene scene;
    @FXML TextField usernameInput;
    @FXML PasswordField passField;
    @FXML TextField fullNameInput;
    @FXML DatePicker birthDateInput;
    @FXML ComboBox attendYear, attendLP;
    @FXML private void registerPressed() throws IOException {
        Birthday birth = new Birthday(birthDateInput.getValue().getYear(),
                            birthDateInput.getValue().getMonth().getValue(),birthDateInput.getValue().getDayOfMonth());
        Integer year = Integer.parseInt(attendYear.getSelectionModel().getSelectedItem().toString());
        Integer lp = Integer.parseInt(attendLP.getSelectionModel().getSelectedItem().toString());
        Admittance admitTime = new Admittance(year, lp);
        ul.registerUser(usernameInput.getText(), fullNameInput.getText(), passField.getText(), birth, admitTime);
        scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/login.fxml")), 600, 450);
        eb.dataNotify(Main.CHANGE_SCENE, scene);
        eb.notify(Main.USER_REGISTERED);
    }
}
