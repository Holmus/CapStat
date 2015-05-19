package capstat.view;

import capstat.infrastructure.DataEventListener;
import capstat.infrastructure.EventBus;
import capstat.infrastructure.NotifyEventListener;
import capstat.model.Match;
import capstat.model.MatchFactory;
import capstat.model.UserFactory;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.Mnemonic;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import javax.xml.bind.SchemaOutputResolver;
import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Jakob on 18/05/15.
 */
public class MatchController implements NotifyEventListener, Initializable{
    EventBus eb = EventBus.getInstance();
    Match match = MatchFactory.createDefaultMatch();
    @FXML Button hitButton, missButton;
    @FXML Circle glass1, glass2, glass3, glass4, glass5, glass6, glass7;
    @FXML Pane p1Pane, p2Pane, mainPane, matchOverPane;
    @FXML Label hitLabel, missLabel, duelLabel, p1Name, p2Name, p1Rank, p2Rank, p1Rounds, p2Rounds;
    Background activeBackground = new Background(new BackgroundFill(Color.CORNFLOWERBLUE, CornerRadii.EMPTY, Insets.EMPTY));
    Background inactiveBackground = new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY));

    @Override
    public void initialize(URL location, ResourceBundle resources){
        mainPane.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        match.setPlayer1(UserFactory.createDummyUser1());
        match.setPlayer2(UserFactory.createDummyUser2());
        p1Name.setText(match.getPlayer(Match.Player.ONE).getName());
        p2Name.setText(match.getPlayer(Match.Player.TWO).getName());
        p1Rank.setText("" + match.getPlayer(Match.Player.ONE).getRanking().getPoints());
        p2Rank.setText("" + match.getPlayer(Match.Player.TWO).getRanking().getPoints());
        eb.addNotifyEventListener(Match.HIT_RECORDED,this);
        eb.addNotifyEventListener(Match.MISS_RECORDED,this);
        eb.addNotifyEventListener(Match.DUEL_ENDED, this);
        eb.addNotifyEventListener(Match.ROUND_ENDED, this);
        eb.addNotifyEventListener(Match.MATCH_ENDED, this);
        hitLabel.setVisible(false);
        missLabel.setVisible(false);
        duelLabel.setVisible(false);
        matchOverPane.setVisible(false);
        hitButton.setFocusTraversable(false);
        missButton.setFocusTraversable(false);
        resetGlasses();
        updatePlayer();
        /*hitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                hitPressed();
            }
        });*/
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
    }
    @FXML private void missPressed() {
        if (!match.isOngoing()) {
            match.startMatch();
        }
        hitLabel.setVisible(false);
        missLabel.setVisible(true);
        duelLabel.setVisible(false);
        match.recordMiss();
    }
    @FXML private void hitPressed(){
        if (!match.isOngoing()) {
            match.startMatch();
        }
        missLabel.setVisible(false);
        hitLabel.setVisible(true);
        duelLabel.setVisible(true);
        match.recordHit();
    }


    @Override
    public void notify(String event) {
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
                p1Rounds.setText("" + match.getPlayer1RoundsWon());
                p2Rounds.setText("" + match.getPlayer2RoundsWon());
                resetGlasses();
                break;
            case Match.MATCH_ENDED:
                System.out.println("Match over! Winner is: " + match.getRoundWinner().name());
                disableReg();
                break;

        }
    }
    private void updateGlasses(){
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
        mainPane.setVisible(true);
        p1Pane.setBackground(inactiveBackground);
        p2Pane.setBackground(inactiveBackground);
        p1Pane.setVisible(true);
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
