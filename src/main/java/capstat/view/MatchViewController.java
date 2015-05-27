package capstat.view;
import capstat.application.MatchController;
import capstat.infrastructure.eventbus.EventBus;
import capstat.infrastructure.eventbus.NotifyEventListener;
import capstat.model.Match;
import capstat.model.UserLedger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Jakob on 18/05/15.
 *
 * Class to update the MatchView
 */
public class MatchViewController implements NotifyEventListener, Initializable{
    EventBus eb = EventBus.getInstance();
    Match match = MatchController.createNewMatch();
    MatchController mc = new MatchController(match);
    String winner = "";
    @FXML Button hitButton, missButton,startMatchButton, menuButton;
    @FXML Circle glass1, glass2, glass3, glass4, glass5, glass6, glass7;
    @FXML Pane p1Pane, p2Pane, mainPane, matchOverPane, preMatchPane;
    @FXML Label hitLabel, missLabel, duelLabel, p1Name, p2Name, p1Rank, p2Rank, p1Rounds, p2Rounds, winnerLabel, nickname1Label, nickname2Label;
    @FXML TextField setPlayer1Field, setPlayer2Field;
    Background activeBackground = new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY));
    Background inactiveBackground = new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY));
    //ToDo: matchOverPane should have a button to not save the result
    //ToDo: add functionality for saving result

    @Override
    public void initialize(URL location, ResourceBundle resources){
        this.mc.setEndGameStrategy(this.mc.RANKED);
        mainPane.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        //p1Name.setText(match.getPlayer(Match.Player.ONE).getName());
        //p2Name.setText(match.getPlayer(Match.Player.TWO).getName());
        //p1Rank.setText("" + match.getPlayer(Match.Player.ONE).getRanking().getPoints());
        //p2Rank.setText("" + match.getPlayer(Match.Player.TWO).getRanking().getPoints());
        eb.addNotifyEventListener(Match.HIT_RECORDED, this);
        eb.addNotifyEventListener(Match.MISS_RECORDED,this);
        eb.addNotifyEventListener(Match.DUEL_ENDED, this);
        eb.addNotifyEventListener(Match.ROUND_ENDED, this);
        eb.addNotifyEventListener(Match.MATCH_ENDED, this);
        nickname1Label.setVisible(false);
        nickname2Label.setVisible(false);
        hitLabel.setVisible(false);
        missLabel.setVisible(false);
        duelLabel.setVisible(false);
        matchOverPane.setVisible(false);
        p1Pane.setVisible(false);
        p2Pane.setVisible(false);
        mainPane.setVisible(false);
        hitButton.setFocusTraversable(false);
        missButton.setFocusTraversable(false);
        preMatchPane.setVisible(true);
        startMatchButton.setDisable(false);
        resetGlasses();
        updatePlayer();
        Platform.runLater(() -> {
            missButton.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.G), () -> {
                missPressed();
            });
        });
        Platform.runLater(() -> {
            hitButton.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.SPACE), () -> {
                hitPressed();
            });
        });
        Platform.runLater(() -> {
            startMatchButton.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.ENTER), () -> {
                startMatchPressed();
            });
        });
        Platform.runLater(() -> {
            menuButton.getScene().getAccelerators().put(new KeyCodeCombination(KeyCode.ESCAPE), () -> {
                returnToMenu();
            });
        });
    }
    @FXML private void returnToMenu(){
        eb.notify(MainView.SETSCENE_MAIN);
    }

    @FXML private void missPressed() {
        if (!match.isOngoing()) {
            mc.startMatch();
        }
        hitLabel.setVisible(false);
        missLabel.setVisible(true);
        duelLabel.setVisible(false);
        mc.recordMiss();
    }

    @FXML private void hitPressed(){
        if (!match.isOngoing()) {
            mc.startMatch();
        }
        missLabel.setVisible(false);
        hitLabel.setVisible(true);
        duelLabel.setVisible(true);
        mc.recordHit();
    }

    @FXML private void saveResultPressed(){
        System.out.println("Save result pressed");
        eb.notify(MainView.SETSCENE_MAIN);
        eb.notify(MainView.MATCH_REGISTERED);

    }

    @FXML private void startMatchPressed(){
        nickname1Label.setVisible(false);
        nickname2Label.setVisible(false);
        if(setPlayer1Field.getText().isEmpty() || setPlayer2Field.getText().isEmpty()){
            if(setPlayer1Field.getText().isEmpty()){
                nickname1Label.setVisible(true);
            }
            if(setPlayer2Field.getText().isEmpty()){
                nickname2Label.setVisible(true);
            }
            return;
        }else{
            //Get strings inputted in text fields
            String p1Nickname = setPlayer1Field.getText();
            mc.setPlayer1(p1Nickname);
            String p2Nickname = setPlayer2Field.getText();
            mc.setPlayer2(p2Nickname);

            //Only show ranking if user is registered.
            boolean p1Exists = UserLedger.getInstance().doesUserExist
                    (p1Nickname);
            if (p1Exists) {
                p1Rank.setText("" + match.getPlayer(Match.Player.ONE).getRanking().getPoints());
            } else {
                p1Rank.setText("(Not registered)");
            }

            boolean p2Exists = UserLedger.getInstance().doesUserExist
                    (p2Nickname);
            if (p2Exists){
                p2Rank.setText("" + match.getPlayer(Match.Player.TWO).getRanking().getPoints());
            } else {
                p2Rank.setText("(Not registered)");
            }

            p1Name.setText(setPlayer1Field.getText());
            p2Name.setText(setPlayer2Field.getText());
            p1Pane.setVisible(true);
            p2Pane.setVisible(true);
            mainPane.setVisible(true);
            preMatchPane.setVisible(false);
            startMatchButton.setDisable(true);
        }

    }

    @Override
    public void notifyEvent(String event) {
        switch (event) {
            case Match.MISS_RECORDED:
                System.out.println("miss");
                updatePlayer();
                break;
            case Match.HIT_RECORDED:
                System.out.println("hit");
                updatePlayer();
                break;
            case Match.DUEL_ENDED:
                System.out.println("duel ended");
                updateGlasses();
                break;
            case Match.ROUND_ENDED:
                System.out.println("Round winner is: " + match.getRoundWinner().name());
                p1Rounds.setText("" + match.getPlayerRoundsWon(Match.Player.ONE));
                p2Rounds.setText("" + match.getPlayerRoundsWon(Match.Player
                        .TWO));
                resetGlasses();
                break;
            case Match.MATCH_ENDED:
                System.out.println("Match over! Winner is: " + match.getRoundWinner().name());
                disableReg();
                break;
            default:
                //Do nothing
        }
    }
    private void updateGlasses(){
        //ToDo: Find nice solution?
        Match.Glass[] glasses = match.getGlasses();
        if(!glasses[0].isActive()){
            glass1.setFill(Color.LIGHTGRAY);
        }
        if(!glasses[1].isActive()){
            glass2.setFill(Color.LIGHTGRAY);
        }
        if(!glasses[2].isActive()){
            glass3.setFill(Color.LIGHTGRAY);
        }
        if(!glasses[3].isActive()){
            glass4.setFill(Color.LIGHTGRAY);
        }
        if(!glasses[4].isActive()){
            glass5.setFill(Color.LIGHTGRAY);
        }
        if(!glasses[5].isActive()){
            glass6.setFill(Color.LIGHTGRAY);
        }
        if(!glasses[6].isActive()){
            glass7.setFill(Color.LIGHTGRAY);
        }
    }
    private void updatePlayer(){
        if(match.getPlayerWhoseTurnItIs().equals(Match.Player.ONE)){
            p1Pane.setBackground(activeBackground);
            p2Pane.setBackground(inactiveBackground);
        } else {
            p1Pane.setBackground(inactiveBackground);
            p2Pane.setBackground(activeBackground);
        }
    }
    private void disableReg(){
        if(Match.Player.ONE.equals(match.getRoundWinner())){
            winner = p1Name.getText();
        } else {
            winner = p2Name.getText();
        }
        winnerLabel.setText("The winner is: " + winner);
        mainPane.setVisible(false);
        p1Pane.setBackground(inactiveBackground);
        p2Pane.setBackground(inactiveBackground);
        p1Pane.setVisible(false);
        p2Pane.setVisible(true);
        matchOverPane.setVisible(true);

    }
    private void resetGlasses(){
        glass1.setFill(Color.ORANGE);
        glass2.setFill(Color.ORANGE);
        glass3.setFill(Color.ORANGE);
        glass4.setFill(Color.ORANGE);
        glass5.setFill(Color.ORANGE);
        glass6.setFill(Color.ORANGE);
        glass7.setFill(Color.ORANGE);
    }
}
