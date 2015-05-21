package capstat.view;

import capstat.application.LoginController;
import capstat.application.StatisticsController;
import capstat.infrastructure.EventBus;
import capstat.model.CapStat;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.net.URL;
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
    EventBus eb = EventBus.getInstance();
    int length;
    @FXML ComboBox XComboBox, YComboBox;
    @FXML private LineChart<Double, Double> lineChart;
    @FXML NumberAxis xAxis, yAxis;
    @FXML Label currentUserLabel;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        XComboBox.getSelectionModel().select(1);
        YComboBox.getSelectionModel().select(0);
        currentUserLabel.setText(cs.getLoggedInUser().getNickname());
    }


    @FXML public void logoutPressed(){
        lc.logoutUser();
        eb.notify(MainView.SETSCENE_LOGIN);
    }

    @FXML public void returnToMain(){
        eb.notify(MainView.SETSCENE_MAIN);
    }

    @FXML public void plotClicked(){
        lineChart.getData().remove(series);
        series = new XYChart.Series();
        addPlotData();
        xAxis.setLabel(XComboBox.getSelectionModel().getSelectedItem().toString());
        yAxis.setLabel(YComboBox.getSelectionModel().getSelectedItem().toString());
        lineChart.getData().add(series);
    }

    public void addPlotData(){
        if(getXArray().length>=getYArray().length){
            length = getYArray().length;
        } else{
            length = getXArray().length;
        }
        for(int i = 0; i<length; i++){
            series.getData().add(new XYChart.Data(getXArray()[i], getYArray()[i]));
        }
    }
    public Double[] getXArray(){
            return sc.getData(XComboBox.getSelectionModel().getSelectedItem().toString(), cs.getLoggedInUser());

    }
    public Double[] getYArray(){
            return sc.getData(YComboBox.getSelectionModel().getSelectedItem().toString(), cs.getLoggedInUser());
    }
}
