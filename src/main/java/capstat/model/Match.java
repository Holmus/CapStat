package capstat.model;

import capstat.utils.Throw;

import java.util.LinkedList;

/**
 * @author hjorthjort
 */
public class Match {

    private boolean isOngoing;
    private LinkedList<Throw> throwSequence;
    private Match.Glass[] glasses;
    private User player1, player2, playerWhoseTurnItIs;
    private int player1Score, player2Score;


    public Match() {
        this(7);
    }

    public Match(int numberOfGlasses) {
        if (numberOfGlasses % 2 == 0)
            throw new IllegalArgumentException("Glasses must be an odd number");
        this.isOngoing = false;
        this.throwSequence = new LinkedList<Throw>();
        this.glasses = new Glass[numberOfGlasses];
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
            this.incrementScore();
        } else {
            //Only switch turns if the throw did not end a duel.
            this.switchPlayerUpNext();
        }
    }


    /**
     * Increments the score of whichever player is not on turn
     */
    private void incrementScore() {
        if (this.playerWhoseTurnItIs == this.player1) {
            this.player2Score++;
        } else {
            this.player1Score++;
        }
    }

    /**
     *
     * @return Whether game is currently in a duel or not.
     */
    public boolean isDuelling() {
        return throwSequence.getLast().hit();
    }


    public int getPlayer1Score() {
        return this.player1Score;
    }

    public int getPlayer2Score() {
        return this.player2Score;
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

    public void addGameOverObserver(final MatchOverObserver recordMatchTest) {
        //TODO
    }

    private void notifyDuelObservers() {
        //TODO
    }

    public Match.Glass[] getGlasses() {
        return null; //TODO
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

    private class Glass {
        private boolean isActive = true;

        public boolean isActive() {
            return isActive;
        }
    }
}
