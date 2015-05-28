package capstat.view;

import capstat.application.RegisterController;
import capstat.infrastructure.eventbus.EventBus;
import capstat.model.user.Admittance;
import capstat.model.user.UserLedger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by Jakob on 16/05/15.
 */
//ToDo: UPDATE from row 123, lp behaves differently now
public class RegisterViewController implements Initializable{
    EventBus eb = EventBus.getInstance();
    UserLedger ul = UserLedger.getInstance();
    RegisterController rc = new RegisterController();
    Scene scene;
    @FXML TextField usernameInput;
    @FXML PasswordField passField, passField1;
    @FXML TextField fullNameInput;
    @FXML DatePicker birthDateInput;
    @FXML ComboBox attendYear, attendLP;
    @FXML Label wrongBirthdayLabel, wrongAttendLabel, passwordMatchLabel, usernameTakenLabel, nameLabel;
    @FXML Button registerButton, loginButton;
    Date today;
    DateFormat dateYear, dateMonth, dateDay;
    Integer admittanceYear, lp, inputYear, inputMonth, inputDay, todayYear, todayMonth, todayDay;
    LocalDate birth;
    Admittance admitTime;
    Boolean testFailed = false;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        wrongAttendLabel.setVisible(false);
        wrongBirthdayLabel.setVisible(false);
        passwordMatchLabel.setVisible(false);
        usernameTakenLabel.setVisible(false);
        nameLabel.setVisible(false);
        birthDateInput.setValue(null);
        attendYear.setValue(null);

        Platform.runLater(() -> {
            registerButton.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.ENTER), () -> {
                registerPressed();
            });
        });
        Platform.runLater(() -> {
            loginButton.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.ESCAPE), () -> {
                goToLogin();
            });
        });

    }
    @FXML private void registerPressed() throws NumberFormatException{
        if(checkInput()){
            return;
        }
        registerUser();
    }

    @FXML private void goToLogin(){
        eb.notify(MainView.SETSCENE_LOGIN);
    }

    private void registerUser(){
        rc.registerNewUser(usernameInput.getText(), fullNameInput.getText(), passField.getText(), birth, admitTime.getYear(), admitTime.getReadingPeriod().ordinal() + 1);
        eb.notify(MainView.SETSCENE_LOGIN);
        eb.notify(MainView.USER_REGISTERED);
    }

    private boolean checkInput(){
        testFailed = false;
        today = new Date();
        dateYear = new SimpleDateFormat("yyyy");
        dateMonth = new SimpleDateFormat("MM");
        dateDay = new SimpleDateFormat("dd");
        todayYear = Integer.parseInt(dateYear.format(today));
        todayMonth = Integer.parseInt(dateMonth.format(today));
        todayDay = Integer.parseInt(dateDay.format(today));
        if(fullNameInput.getText().isEmpty()){
            updateName();
        } else {
            nameLabel.setVisible(false);
        }
        if(usernameInput.getText().isEmpty()){
            usernameTakenLabel.setText("Pick a username!");
            updateUsername();
        } else {
            checkUsernameValid();
        }
        if(birthDateInput.getValue() == null){
            updateBirth();
        } else {
            wrongBirthdayLabel.setVisible(false);
            inputYear = birthDateInput.getValue().getYear();
            inputMonth = birthDateInput.getValue().getMonth().getValue();
            inputDay = birthDateInput.getValue().getDayOfMonth();
            birth = LocalDate.of(inputYear, inputMonth, inputDay);
            if(todayYear<inputYear){
                updateBirth();
            } else if(todayYear.equals(inputYear) && todayMonth<inputMonth){
                updateBirth();
            } else if(todayYear.equals(inputYear) && todayMonth.equals
                    (inputMonth) && todayDay<inputDay){
                updateBirth();
            }
        }
        if(passwordsMatch()) {
            passwordMatchLabel.setVisible(false);
        } else {
            updatePasswords();
        }
        if(attendYear.getValue() == null){
            updateLP();
        } else {;
            try {
                admittanceYear = Integer.parseInt(attendYear.getSelectionModel().getSelectedItem().toString());
            } catch (NumberFormatException e){
                updateLP();
            }
            wrongAttendLabel.setVisible(false);
        }
        if(attendLP.getSelectionModel().getSelectedItem() == null){
            updateLP();
        } else {
            wrongAttendLabel.setVisible(false);
            try {
                lp = Integer.parseInt(attendLP.getSelectionModel().getSelectedItem().toString());
            } catch (NumberFormatException e){
                updateLP();
            }
            try {
                admitTime = new Admittance(Year.of(admittanceYear), Admittance.Period.values()[lp-1]);
            } catch (NullPointerException e){
                updateLP();
            }
            if (lp > 4 || lp < 1) {
                updateLP();
            } else {
                wrongAttendLabel.setVisible(false);
            }
        }
        return testFailed;
    }

    private void updateName(){
        nameLabel.setVisible(true);
        testFailed = true;
    }

    private void updateLP(){
        wrongAttendLabel.setVisible(true);
        testFailed = true;
    }

    private void updateBirth(){
        wrongBirthdayLabel.setVisible(true);
        testFailed = true;
    }

    private void updatePasswords(){
        passwordMatchLabel.setVisible(true);
        testFailed = true;
    }
    private void updateUsername(){
        usernameTakenLabel.setVisible(true);
        testFailed = true;
    }

    private void checkUsernameValid(){
        if(ul.isNicknameValid(usernameInput.getText())){
            usernameTakenLabel.setVisible(false);
            return;
        }
        usernameTakenLabel.setText("That username is taken/invalid!");
        updateUsername();
    }

    private boolean passwordsMatch(){
        if(passField.getText().isEmpty() && passField1.getText().isEmpty()){
            passwordMatchLabel.setText("Enter a password!");
            return false;
        }
        passwordMatchLabel.setText("Passwords don't match!");
        return passField.getText().equals(passField1.getText());
    }

}
