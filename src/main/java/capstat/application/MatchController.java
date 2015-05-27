package capstat.application;

import capstat.infrastructure.NotifyEventListener;
import capstat.model.*;

/**
 * Created by Jakob on 20/05/15.
 *
 * Class to control the match in the model-layer
 *
 */

public class MatchController implements NotifyEventListener {

    private Match match;
    private UserLedger ul = UserLedger.getInstance();
    private EndGameStrategy endGameStrategy;

    public final EndGameStrategy UNRANKED = new UnrankedStrategy();
    public final EndGameStrategy RANKED = new RankedStrategy();


    /**
     * Creates a new Match using the MatchFactory.
     * @return default instance of a match, created in MatchFactory.
     */
    public static Match createNewMatch(){
        return MatchFactory.createDefaultMatch();
    }

    /**
     * Creates a new MatchController with a match.
     * @param match
     */
    public MatchController(Match match){
        this.match = match;
        this.match.addNotificationEventListener(Match.MATCH_ENDED, this);
        this.endGameStrategy = new UnrankedStrategy();
    }

    /**
     * Starts the match in the Model-layer
     */
    public void startMatch() {
        match.startMatch();
    }

    /**
     * Registers the latest Throw as a Miss
     */
    public void recordMiss() {
        match.recordMiss();
    }

    /**
     * Registers the latest Throw as a Hit
     */
    public void recordHit() {
        match.recordHit();
    }

    public void setPlayer1(String text) {
        final User p1 = ul.getUserByNickname(text);
        match.setPlayer1(p1);
    }
    public void setPlayer2(String text) {
        final User p2 = ul.getUserByNickname(text);
        match.setPlayer2(p2);
    }

    public void setEndGameStrategy(EndGameStrategy strategy) {
        this.endGameStrategy = strategy;
    }

    public void setNewGameState(Match.Glass[] glasses, Match.Player
            startingPlayer, boolean duelIsOngoing) {
        this.match.manuallyChangeGameState(glasses, startingPlayer, duelIsOngoing);
    }

    public void rewind() {
        this.match.rewind();
    }

    public void forward() {
        this.match.forward();
    }

    @Override
    public void notifyEvent(final String event) {
        if (event.equals(Match.MATCH_ENDED))
            this.endGameStrategy.endGame();
    }

    //Inner classes

    /**
     * @author hjorthjort
     */
    public interface EndGameStrategy {
        void endGame();
    }

    private class UnrankedStrategy implements EndGameStrategy {
        @Override
        public void endGame() {
            //do nothing
        }
    }

    private class RankedStrategy implements EndGameStrategy {
        @Override
        public void endGame() {
            //First make sure that updating ranking is even meaningful. If
            // not, end method execution.
            UserLedger ledger = UserLedger.getInstance();
            String player1Nickname = match.getPlayer(Match.Player.ONE) ==
                    null ? null : match.getPlayer(Match.Player.ONE)
                    .getNickname();
            String player2Nickname = match.getPlayer(Match.Player.TWO) ==
                    null ? null : match.getPlayer(Match.Player.TWO)
                    .getNickname();
            if (!ledger.doesUserExist(player1Nickname) ||
                    !ledger.doesUserExist(player2Nickname))
                return ;

            //Then, update rankings
            Match.Player winningPlayer = null;
            try {
                if (!match.isOngoing()) {
                    winningPlayer = match.getWinner();
                }
            } catch (Match.MatchNotOverException e) {
                throw new RuntimeException("Match not over " +
                        "exception was thrown even though mathc reported " +
                        "being over.");
            }
            Match.Player losingPlayer = winningPlayer == Match.Player.ONE ?
                    Match.Player.TWO : Match.Player.ONE;
            //At this point, a new exception will have been thrown if
            // winningPlayer has not been declared to somehting other than null.
            User winner = match.getPlayer(winningPlayer);
            User loser = match.getPlayer(losingPlayer);
            double[] newRanking = ELORanking.calculateNewRanking(winner.getRanking(),
                    loser.getRanking(), match.getPlayerRoundsWon(winningPlayer), match
                            .getPlayerRoundsWon(losingPlayer));
            winner.setRanking(newRanking[0]);
            loser.setRanking(newRanking[1]);
        }
    }
}
