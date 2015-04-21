package capstat.model;

import capstat.utils.Throw;

import java.util.LinkedList;
import java.util.List;

/**
 * @author hjorthjort
 */
public class Match {

    private boolean isOngoing;
    private List throwSequence;
    private Match.Glass[] glasses;

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

    public void startMatch() {
        this.isOngoing = true;
    }

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
        }
    }

    /**
     * Updates the game with a new throw, which is a miss.
     */
    public void recordMiss() {
        this.throwSequence.add(Throw.createMiss());
        if (!this.isDuelling()) {
            this.notifyDuelObservers();
        }
    }

    public boolean isDuelling() {
        return throwSequence.getLast().hit();
    }


    public int getPlayer1Score() {
        return player1Score;
    }

    public int getPlayer2Score() {
        //TODO
    }

    public long getPlayer2() {
        //TODO
    }

    public long getPlayerNextUp() {
        //TODO
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
    public long getWinner() throws MatchNotOverException {
        return winner;
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
