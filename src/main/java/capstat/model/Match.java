package capstat.model;

/**
 * @author hjorthjort
 */
public class Match {
    public void startMatch() {}

    public boolean isOngoing() {
        //TODO
    }

    public void recordHit() {
        //TODO
    }

    public void recordMiss() {
        //TODO
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
