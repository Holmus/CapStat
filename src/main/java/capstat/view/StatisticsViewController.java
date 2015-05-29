package capstat.view;

import capstat.application.LoginController;
import capstat.model.statistics.Statistic;
import capstat.application.StatisticsController;
import capstat.infrastructure.eventbus.EventBus;
import capstat.model.CapStat;
import capstat.model.statistics.Plottable;
import capstat.model.user.User;
import capstat.model.user.UserLedger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Jakob on 21/05/15.
 *
 * Class for the user to view statistics by plotting Charts using desired Data.
 */
public class StatisticsViewController implements Initializable{
    XYChart.Series series = new XYChart.Series();
    CapStat cs = CapStat.getInstance();
    LoginController lc = new LoginController();
    StatisticsController sc = new StatisticsController();
    UserLedger ul = UserLedger.getInstance();
    EventBus eb = EventBus.getInstance();
    int length;
    @FXML ComboBox XComboBox, YComboBox;
    @FXML private LineChart<Double, Double> lineChart;
    @FXML NumberAxis yAxis;
    @FXML CategoryAxis xAxis;
    @FXML Label currentUserLabel, ratingLabel, statViewLabel, invalidUserLabel;
    @FXML TextField userTextField;
    @FXML Button logoutButton, plotButton, mainButton;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userTextField.setText(cs.getLoggedInUser().getNickname());
        invalidUserLabel.setVisible(false);
        ratingLabel.setText("Your rating: " + cs.getLoggedInUser().getRanking().getPoints());
        XComboBox.getSelectionModel().select(0);
        YComboBox.getSelectionModel().select(0);
        currentUserLabel.setText(cs.getLoggedInUser().getNickname());

        Platform.runLater(() -> {
            mainButton.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.ESCAPE), () -> {
                returnToMain();
            });
        });
        Platform.runLater(() -> {
            plotButton.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.P), () -> {
                plotClicked();
            });
        });
        Platform.runLater(() -> {
            logoutButton.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.L), () -> {
                logoutPressed();
            });
        });
    }


    @FXML public void logoutPressed(){
        lc.logoutUser();
        eb.notify(MainView.SETSCENE_LOGIN);
    }

    @FXML public void returnToMain(){
        eb.notify(MainView.SETSCENE_MAIN);
    }

    @FXML public void plotClicked(){
        User user;
        if(userTextField.getText().isEmpty()){
            invalidUserLabel.setVisible(true);
            return;
        } else{
            if(ul.doesUserExist(userTextField.getText())){
                invalidUserLabel.setVisible(false);
                user = ul.getUserByNickname(userTextField.getText());
                ratingLabel.setText(user.getNickname() + " rating: " + user.getRanking().getPoints());
            } else{
                invalidUserLabel.setVisible(true);
                return;
            }
        }
        lineChart.getData().remove(series);
        series = new XYChart.Series();
        addPlotData(user);
        xAxis.setLabel(XComboBox.getSelectionModel().getSelectedItem().toString());
        yAxis.setLabel(YComboBox.getSelectionModel().getSelectedItem().toString());
        lineChart.getData().add(series);
    }

    public void addPlotData(User user){
        if(getXArray(user).length>=getYArray(user).length){
            length = getYArray(user).length;
        } else{
            length = getXArray(user).length;
        }
        for(int i = 0; i<length; i++){
            series.getData().add(new XYChart.Data(getXArray(user)[i].getLabel(),
                    getYArray
                    (user)[i].getValue()));
        }
    }
    public Plottable[] getXArray(User user){
        String statisticType = XComboBox.getSelectionModel().getSelectedItem()
                        .toString();
        Statistic statisticStrategy =
                getStatisticTypeForString(statisticType);
        List<Plottable> plottablesList = sc.getData(statisticStrategy, user);

        return plottablesList.toArray(new Plottable[0]);

    }
    public Plottable[] getYArray(User user){
        String statisticType = YComboBox.getSelectionModel().getSelectedItem()
                .toString();
        Statistic statisticStrategy =
                getStatisticTypeForString(statisticType);
        List<Plottable> plottablesList = sc.getData(statisticStrategy, user);

        return plottablesList.toArray(new Plottable[0]);
    }

    private Statistic getStatisticTypeForString(String string) {
        switch (string) {
            case ("Accuracy"):
                return StatisticsController.ACCURACY;
            case ("Time"):
                return StatisticsController.TIME;
            case ("Number of Games"):
                return StatisticsController.MATCH_COUNT;
            case ("Longest Duel"):
                return StatisticsController.LONGEST_DUEL;
            case ("Total Number of Throws"):
                return StatisticsController.NUMBER_OF_THROWS;
            case ("Match Duration"):
                return StatisticsController.ELAPSED_TIME;
        }
        return null;

    }
}
