package capstat.model;

import java.time.Instant;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author hjorthjort, holmus
 */

/*
 * ToDo: Update constructors when Enum for Player1, Player2 is implemented.
 * ToDo: Set default number of rounds to win (2?)
 * ToDo: Bad implementation of GameOver, work out better solution
 * ToDo: Reset state of glasses after round is over
 */
public class Match {

    private boolean isOngoing;
    private ThrowSequence throwSequence;
    private Match.Glass[] glasses;
    private User player1, player2, winner;
    private Player roundWinner, playerWhoseTurnItIs;
    private Set<MatchOverObserver> matchOverObservers;
    private Set<DuelObserver> duelObservers;
    private int p1RoundsWon, p2RoundsWon, roundsToWin, numberOfGlasses;
    private Instant startTime, endTime;

    /**
     * Creates a new match.
     * @param numberOfGlasses the total number of glasses that should be in
     *                        play each round. Must be an odd number larger
     *                        than 1
     * @param roundsToWin the number of rounds required for a plyer to win
     *                    the game.
     * @throws IllegalArgumentException if
     */
    public Match(int numberOfGlasses, int roundsToWin) {
        if (numberOfGlasses < 3 || numberOfGlasses % 2 == 0)
            throw new IllegalArgumentException("Glasses must be an odd number" +
                    " larger than 1");
        if (roundsToWin < 1) {
            throw new IllegalArgumentException("Rounds to win must be set > 1");
        }
        this.numberOfGlasses = numberOfGlasses;
        this.isOngoing = false;
        this.roundsToWin = roundsToWin;
        this.matchOverObservers = new HashSet<>();
        this.duelObservers = new HashSet<>();
        this.playerWhoseTurnItIs = Player.ONE;
        p1RoundsWon = 0;
        p2RoundsWon = 0;
        createGlasses();
        this.throwSequence = new ThrowSequence(this.glasses, this
                .playerWhoseTurnItIs, false);
    }

    /**
     * The player that will be returned by calling getPlayer(Match.Player.ONE)
     * @param player1
     */
    public void setPlayer1(User player1) {
        this.player1 = player1;
    }

    /**
     * The player that will be returned by calling getPlayer(Match.Player.TWO)
     * @param player2
     */
    public void setPlayer2(User player2) {
        this.player2 = player2;
    }

    /**
     * Sets the currently active player.
     * @param player the player, Match.Player.ONE or Match.Player.TWO
     */
    public void setCurrentPlayer(Player player) {
        this.playerWhoseTurnItIs = player;
    }

    /**
     * Gives the starting player of a match, according to caps rules. The rules
     * says that the "youngest" player starts. See ChalmersAge.compareTo for
     * more details.
     * @return the starting player of this match
     */
    public User getStartingPlayer() {
        // Suppose player 1 is younger
        User user = this.player1;
        ChalmersAge age1 = this.player1.getChalmersAge();
        ChalmersAge age2 = this.player2.getChalmersAge();
        int comparison = age1.compareTo(age2);

        // Only change the starting player if player 1 is "older" than player 2
        if (comparison > 0)
            user = this.player2;

        return new User(user);
    }

    /**
     * Makes the match start.
     */
    public void startMatch() {
        this.startTime = Instant.now();
        this.isOngoing = true;
    }

    /**
     * @return true if the match has started but not yet finished. Returns
     * true also if the match is paused. Returns false otherwise.
     */
    public boolean isOngoing() {
        return this.isOngoing;
    }

    /**
     * Updates the game with a new Throw, which is a hit.
     */
    public void recordHit() {
        if(isOngoing) {
            if (!this.isDuelling()) {
                this.notifyDuelObserversDuelStarted();
            }
            this.switchPlayerUpNext();
            this.throwSequence.add(Throw.HIT);
        }
    }

    /**
     * Updates the game with a new throw, which is a miss.
     */
    public void recordMiss() {
        if(isOngoing) {
            if (this.isDuelling()) {
                this.notifyDuelObserversDuelEnded();
                this.removeGlass();
                this.endRoundIfNecessary();
            } else {
                //Only switch turns if the throw did not end a duel.
                this.switchPlayerUpNext();
            }
            this.throwSequence.add(Throw.MISS);
        }
    }

    /**
     * Checks if middle glass is inactive and ends game if it is.
     */
    private void endRoundIfNecessary() {
        if (!this.glasses[this.glasses.length / 2].isActive) {
            updateRoundWinner();
            endMatchIfNecessary();
            createGlasses();
        }
    }

    /**
     *
     */
    private void updateRoundWinner() {
        roundWinner = this.getPlayer1Score() > this.getPlayer2Score() ? Player.ONE: Player.TWO;
        if (roundWinner == Player.ONE) p1RoundsWon = p1RoundsWon + 1;
        if (roundWinner == Player.TWO) p2RoundsWon = p2RoundsWon + 1;
    }

    private void endMatchIfNecessary() {
        if (p1RoundsWon == this.roundsToWin || p2RoundsWon == this.roundsToWin) endMatch();
    }

    private void endMatch() {
        this.endTime = Instant.now();
        this.isOngoing = false;
        if(p1RoundsWon == p2RoundsWon){
            throw new ArithmeticException("We've dun goofed");
        }
        this.winner = p1RoundsWon > p2RoundsWon ? this
                .player1 : this.player2;
        this.notifyMatchOverObservers();
    }

    private void removeGlass() {
        if (this.playerWhoseTurnItIs == Player.ONE) {
            this.removeNextGlassPlayer1();
        } else {
            this.removeNextGlassPlayer2();
        }
    }

    private void createGlasses() {
        this.glasses = new Glass[this.numberOfGlasses];
        for (int i = 0; i < this.numberOfGlasses; i++) {
            this.glasses[i] = new Match.Glass();
        }
    }

    /**
     * Finds next active glass on player 1's side and removes it. Middle
     * glass can be removed.
     */
    private void removeNextGlassPlayer1() {
        for (int i = 0; i <= (this.glasses.length / 2); i++) {
            if (this.removeSpecificGlass(i)) break;
        }
    }

    /**
     * Finds next active glass on player 2's side and removes it. Middle
     * glass can be removed.
     */
    private void removeNextGlassPlayer2() {
        for (int i = this.glasses.length - 1; i >= this.glasses.length / 2; i--) {
            if (this.removeSpecificGlass(i)) break;
        }
    }

    /**
     * Sets specified glass to unactive if it was active, otherwise does
     * nothing.
     *
     * @param i
     * @return true if glass was removed, false otherwise.
     */
    private boolean removeSpecificGlass(final int i) {
        if (glasses[i].isActive()) {
            glasses[i].isActive = false;
            return true;
        }
        return false;
    }

    /**
     * @return Whether game is currently in a duel or not.
     */
    public boolean isDuelling() {
        return this.isOngoing && this.throwSequence.lastThrowWasHit();
    }

    public int getPlayer1Score() {
        int score = 0;
        for (int i = this.glasses.length - 1; i >= this.glasses.length / 2; i--) {
            if (this.glasses[i].isActive()) break;
            else score++;
        }
        return score;
    }

    public int getPlayer2Score() {
        int score = 0;
        for (int i = 0; i <= this.glasses.length / 2; i++) {
            if (this.glasses[i].isActive()) break;
            else score++;
        }
        return score;
    }

    public User getPlayer1() {
        return this.player1;
    }

    public User getPlayer2() {
        return this.player2;
    }

    public Player getPlayerWhoseTurnItIs() {
        if (playerWhoseTurnItIs == Player.ONE) {
            return Player.ONE;
        }
        return Player.TWO;
    }

    /**
     *
     * @return a deep clone of the ThrowSequence of this match.
     */
    public ThrowSequence getThrowSequence() {
        return new ThrowSequence(this.throwSequence);
    }

    /**
     * Add an observer that will be notified when the game is over.
     *
     * @param observer
     */
    //TODO Change method to make use of event bus
    public void addMatchOverObserver(final MatchOverObserver observer) {
        this.matchOverObservers.add(observer);
    }

    /**
     * Call the matchOver method on all observers that have been added to this
     * match.
     */
    //TODO Change method to make use of event bus
    private void notifyMatchOverObservers() {
        Iterator<MatchOverObserver> iterator = this.matchOverObservers.iterator();
        while (iterator.hasNext()) {
            iterator.next().matchOver();
        }
    }

    //TODO Change method to make use of event bus
    public void addDuelObserver(final DuelObserver observer) {
        this.duelObservers.add(observer);
    }

    //TODO Change method to make use of event bus
    private void notifyDuelObserversDuelStarted() {
        Iterator<DuelObserver> iterator = this.duelObservers.iterator();
        while (iterator.hasNext()) {
            iterator.next().duelStarted();
        }
    }

    //TODO Change method to make use of event bus
    private void notifyDuelObserversDuelEnded() {
        Iterator<DuelObserver> iterator = this.duelObservers.iterator();
        while (iterator.hasNext()) {
            iterator.next().duelEnded();
        }
    }

    /**
     * @return An array representing the glasses in the match, starting with
     * the glasses of Player 1, then the middle glass, and then the glasses
     * of Player 2.
     */
    public Match.Glass[] getGlasses() {
        return this.glasses.clone();
    }
    public void manuallyChangeGameState(Match.Glass[] glasses, Player
            startingPlayer, boolean lastThrowWasHit) {
        this.throwSequence.updateRecordState(glasses, startingPlayer, lastThrowWasHit);
    }

    /**
     * @return the User who is the winner of the game. If the players have
     * not been set, null will be returned.
     * @throws MatchNotOverException if and only if match is ongoing, which
     *                               can be checked by call to isOngoing.
     */
    public User getWinner() throws MatchNotOverException {
        if (this.isOngoing) {
            throw new MatchNotOverException();
        }
        return winner;
    }

    /**
     * @param player
     * @return the User representation of the Enum player sent as param, could return null if Users are not defined yet.
     */
    public User getPlayer(Player player){
        if(player == null){
            throw new NullPointerException("Player can't be null");
        }
        if(player == Player.ONE){
            return this.player1;
        } else{
            return this.player2;
        }
    }

    /**
     * If it's player ONE's turn when this is called, turn moves to player
     * TWO. And vice versa.
     */
    private void switchPlayerUpNext() {
        this.playerWhoseTurnItIs = this.playerWhoseTurnItIs == Player.ONE ? Player.TWO :
                Player.ONE;
    }

    /**
     * @return the Instant object created at the start of the match. Returns
     * null if tha match has not been started.
     */
    public Instant getStartTime() {
        //This is safe since Instants are immutable
        return startTime;
    }

    /**
     *
     * @return the Instant object created at the end of the match. Returns
     * null if the match has not ended.
     */
    public Instant getEndTime() {
        return endTime;
    }

    public class MatchNotOverException extends Exception {
    }

    /**
     * Class representing the glasses in the match. A glass is either active
     * or inactive.
     */
    public class Glass {
        private boolean isActive = true;

        public boolean isActive() {
            return isActive;
        }
    }

    /**
     *
     * @return a String representing match as player names with an asterisk
     * at player who's turn it is and a representation of the glasses, where
     * 'O' marks active glass and 'X' marks inactive glass.
     */
    public String toString() {
        String p1 = player1 == null ? "Not set" : player1.getNickname();
        String p2 = player2 == null ? "Not set" : player2.getNickname();
        String gl = "  ";
        for (int i = 0; i < this.glasses.length; i++) {
            if (this.glasses[i].isActive) gl = gl + "O    ";
            else gl = gl + "X     ";
        }
        int spaces = gl.length() - (p1.length() + p2.length()) - 1;
        String sp = "";
        for (int i = 0; i < spaces; i++) {
            sp = sp + " ";
        }
        if (this.playerWhoseTurnItIs == Player.ONE) p1 = p1 + "*";
        else p2 = "*" + p2;

        return p1 + sp + p2 + "\n" + gl;
    }

    /**
     * A class representing a Throw. It in turn has two inner classes: one
     * representing a hit and one representing a miss. These are not shown to
     * the user, and there will always be only one objcet of each class,
     * which can be retrieved by the create-methods in Throw.
     */
    public enum Throw {
        HIT,
        MISS
    }

    public enum Player{
        ONE,
        TWO
    }
}
