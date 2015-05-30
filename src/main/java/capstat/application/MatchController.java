package capstat.application;

import capstat.infrastructure.eventbus.NotifyEventListener;
import capstat.model.match.Match;
import capstat.model.match.MatchFactory;
import capstat.model.statistics.ResultLedger;
import capstat.model.user.ELORanking;
import capstat.model.user.User;
import capstat.model.user.UserLedger;

/**
 * Created by Jakob on 20/05/15.
 * @author hjorthjort, Jakob
 *
 * Class to control the match in the model-layer
 *
 */

public class MatchController implements NotifyEventListener {

    private Match match;
    private UserLedger userLedger = UserLedger.getInstance();
    private ResultLedger resultLedger = ResultLedger.getInstance();
    private EndGameStrategy endGameStrategy;

    public final EndGameStrategy UNRANKED = new UnrankedStrategy();
    public final EndGameStrategy RANKED = new RankedStrategy();

    //Static methods

    /**
     * Creates a new Match using the MatchFactory.
     * @return default instance of a match, created in MatchFactory.
     */
    public static Match createNewMatch(){
        return MatchFactory.createDefaultMatch();
    }

    //Creation

    /**
     * Creates a new MatchController with a match.
     * @param match
     */
    public MatchController(Match match){
        this.match = match;
        this.match.addNotificationEventListener(Match.MATCH_ENDED, this);
        this.endGameStrategy = new UnrankedStrategy();
    }

    //Gameplay

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

    //Match setup

    /**
     * Set which user is player 1. If the given nickname does not exist,
     * player 1 will be set to null. If the given user is set as player 2,
     * this method call has no effect
     * @param nickname nickname of the player which should be set as player 1
     */
    public void setPlayer1(String nickname) {
        final User p1 = userLedger.getUserByNickname(nickname);
        User p2 = match.getPlayer(Match.Player.TWO);
        if (p2 == null || !p2.equals(p1))
            match.setPlayer1(p1);
    }

    /**
     * Set which user is player 2. If the given nickname does not exist,
     * player 2 will be set to null. If the given user is set as player 1,
     * this method call has no effect
     * @param nickname nickname of the player which should be set as player 2
     */
    public void setPlayer2(String nickname) {
        final User p2 = userLedger.getUserByNickname(nickname);
        User p1 = match.getPlayer(Match.Player.ONE);
        if (p1 == null || !p1.equals(p2))
            match.setPlayer2(p2);
    }

    public void setEndGameStrategy(EndGameStrategy strategy) {
        this.endGameStrategy = strategy;
    }

    /**
     * Make the match continue recording its sequence from this state.
     * @param glasses the state of the glasses, active glass indicated by true
     * @param startingPlayer which player has the next turn
     * @param duelIsOngoing if there is a duel ongoing
     */
    public void setNewGameState(Match.Glass[] glasses, Match.Player
            startingPlayer, boolean duelIsOngoing) {
        this.match.manuallyChangeGameState(glasses, startingPlayer,
                duelIsOngoing);
    }

    //Observer method
    @Override
    public void notifyEvent(final String event) {
        if (event.equals(Match.MATCH_ENDED + this.match.getEventId()))
            this.endGameStrategy.endGame();
    }

    //Saving

    /**
     * Save this game.
     * @throws IllegalStateException if the match is still ongoing.
     */
    public void saveGame() {
        if (this.match.isOngoing())
            throw new IllegalStateException("Match is not over");
        this.resultLedger.registerResult(this.match);
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
            double[] newRanking = ELORanking.calculateNewRanking(winner
                            .getRanking(),
                    loser.getRanking(), match.getPlayerRoundsWon
                            (winningPlayer), match
                            .getPlayerRoundsWon(losingPlayer));
            winner.setRanking(newRanking[0]);
            loser.setRanking(newRanking[1]);
            ledger.save();
        }
    }
}
