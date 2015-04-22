package capstat.model;

import capstat.utils.Throw;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 * @author hjorthjort
 */
public class Match {

    private boolean isOngoing;
    private LinkedList<Throw> throwSequence;
    private Match.Glass[] glasses;
    private User player1, player2, playerWhoseTurnItIs, winner;;
    private Set<MatchOverObserver> matchOverObservers;


    public Match() {
        this(7);
    }

    public Match(int numberOfGlasses) {
        if (numberOfGlasses % 2 == 0)
            throw new IllegalArgumentException("Glasses must be an odd number");
        this.isOngoing = false;
        this.throwSequence = new LinkedList<Throw>();
        this.glasses = new Glass[numberOfGlasses];
        this.matchOverObservers = new HashSet<>();
        for (int i = 0; i < numberOfGlasses; i++) {
            this.glasses[i] = new Match.Glass();
        }
    }

    /**
     * Makes the match start.
     */
    public void startMatch() {
        this.isOngoing = true;
    }

    /**
     *
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
        this.throwSequence.add(Throw.createHit());
        if (!this.isDuelling()) {
            this.notifyDuelObservers();
            this.switchPlayerUpNext();
        }
    }

    /**
     * Updates the game with a new throw, which is a miss.
     */
    public void recordMiss() {
        this.throwSequence.add(Throw.createMiss());
        if (this.isDuelling()) {
            this.notifyDuelObservers();
            this.removeGlass();
            this.endMatchIfNecessary();
        } else {
            //Only switch turns if the throw did not end a duel.
            this.switchPlayerUpNext();
        }
    }

    /**
     * Checks if middle glass is unactive and ends game if it is.
     */
    private void endMatchIfNecessary() {
        if(!this.glasses[this.glasses.length/2].isActive) this.endMatch();
    }

    private void endMatch() {
        this.isOngoing = false;
        this.winner = this.getPlayer1Score() > this.getPlayer2Score() ? this
                .player1 : this.player2;
        this.notifyMatchOverObservers();
    }


    private void removeGlass() {
        if (this.playerWhoseTurnItIs == player1) {
            this.removeNextGlassPlayer1();
        } else {
            this.removeNextGlassPlayer2();
        }
    }

    /**
     * Finds next active glass on player 1's side and removes it. Middle
     * glass can be removed.
     */
    private void removeNextGlassPlayer1() {
        for (int i = 0; i <= (this.glasses.length/2); i++) {
            if (this.removeSpecificGlass(i)) break;
        }
    }

    /**
     * Finds next active glass on player 2's side and removes it. Middle
     * glass can be removed.
     */
    private void removeNextGlassPlayer2() {
        for (int i = this.glasses.length-1; i >= this.glasses.length/2; i--) {
            if (this.removeSpecificGlass(i)) break;
        }
    }

    /**
     * Sets specified glass to unactive if it was active, otherwise does
     * nothing.
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
     *
     * @return Whether game is currently in a duel or not.
     */
    public boolean isDuelling() {
        return throwSequence.getLast().hit();
    }


    public int getPlayer1Score() {
        int score = 0;
        for (int i = this.glasses.length-1; i >= this.glasses.length/2; i++) {
            if (this.glasses[i].isActive()) break;
            else score++;
        }
        return score;
    }

    public int getPlayer2Score() {
        int score = 0;
        for (int i = 0; i <= this.glasses.length/2; i++) {
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

    public User getPlayerWhoseTurnItIs() {
        return this.playerWhoseTurnItIs;
    }

    /**
     * Add an observer that will be notified when the game is over.
     * @param observer
     */
    public void addMatchOverObserver(final MatchOverObserver observer) {
        this.matchOverObservers.add(observer);
    }

    /**
     * Call the matchOver method on all observers that have been added to this
     * match.
     */
    private void notifyMatchOverObservers() {
        Iterator<MatchOverObserver> iterator = this.matchOverObservers.iterator();
        while (iterator.hasNext()) {
            iterator.next().matchOver();
        }
    }

    private void notifyDuelObservers() {
        //TODO
    }

    /**
     * @return An array representing the glasses in the match, starting with
     * the glasses of Player 1, then the middle glass, and then the glasses
     * of Player 2.
     */
    public Match.Glass[] getGlasses() {
        return this.glasses.clone();
    }


    /**
     *
     * @return
     * @throws MatchNotOverException if and only if match is ongoing, which
     * can be checked by call to isOngoing.
     */
    public User getWinner() throws MatchNotOverException {
        return null; //TODO
    }

    private void switchPlayerUpNext() {
        this.playerWhoseTurnItIs = this.playerWhoseTurnItIs == this.player1 ? player2 :
                player1;
    }

    public class MatchNotOverException extends Exception {
    }

    public class Glass {
        private boolean isActive = true;

        public boolean isActive() {
            return isActive;
        }
    }
}
