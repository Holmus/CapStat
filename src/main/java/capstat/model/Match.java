package capstat.model;

import capstat.utils.Throw;

import java.util.LinkedList;
import java.util.List;

/**
 * @author hjorthjort
 */
public class Match {

    private boolean isOngoing, isDuelling;
    private List throwSequence;

    public Match() {
        this.isOngoing = false;
        this.isDuelling = false;
        this.throwSequence = new LinkedList<Throw>;
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
        if (!this.isDuelling) {
            this.isDuelling = true;
            this.notifyDuelObservers();
        }
    }

    /**
     * Updates the game with a new throw, which is a miss.
     */
    public void recordMiss() {
        this.throwSequence.add(Throw.createMiss());
        if (!this.isDuelling) {
            this.isDuelling = false;
            this.notifyDuelObservers();
        }
    }

    public boolean isDuelling() {
        //TODO
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
}
