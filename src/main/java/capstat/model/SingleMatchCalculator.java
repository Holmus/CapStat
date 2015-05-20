package capstat.model;

import capstat.model.Match;
import capstat.model.MatchFactory;
import capstat.model.MatchResult;
import capstat.model.PartialSequenceResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SingleMatchCalculator {

    private MatchResult mr;

    public SingleMatchCalculator(MatchResult mr) {
        this.mr = mr;
    }

    /**
     * Calculates the accuracy for the given player. The accuracy is given as a double d in the range 0 ≤ d ≤ 1.
     * @param player the Player to calculate the accuracy for (Match.Player.ONE or Match.Player.TWO)
     * @return a double containing the accuracy
     */
    public double getAccuracy(Match.Player player) {
        int hits = 0, total;
        List<PartialSequenceResult> sequences = mr.getSequences();
        List<Match.Throw> playerThrows = getThrowsForPlayer(sequences, player);
        for (Match.Throw t : playerThrows) {
            if (t == Match.Throw.HIT) hits++;
        }

        total = getTotalNumberOfThrows(player);

        return (double) hits / (double) total;
    }

    public int getTotalNumberOfThrows() {
        return getThrowsFromSequences(mr.getSequences()).size();
    }

    public int getTotalNumberOfThrows(Match.Player player) {
        return getThrowsForPlayer(mr.getSequences(), player).size();
    }

    private static List<Match.Player> getPlayerTurnsFromSequences(List<PartialSequenceResult> sequences) {
        List<Match.Player> turns = new ArrayList<>();

        // The match logic is already defined in the Match class, so we use it instead of rewriting it here
        Match underlyingMatch = MatchFactory.createDefaultMatch();
        underlyingMatch.startMatch();

        for (PartialSequenceResult psr : sequences) {
            underlyingMatch.setCurrentPlayer(psr.getStartingPlayer());

            for (Match.Throw t : psr.getSequence()) {
                turns.add(underlyingMatch.getPlayerWhoseTurnItIs());
                if (t == Match.Throw.HIT)
                    underlyingMatch.recordHit();
                else
                    underlyingMatch.recordMiss();
            }
        }

        return turns;
    }

    private static List<Match.Throw> getThrowsFromSequences(List<PartialSequenceResult> sequences) {
        List<Match.Throw> throwList = new ArrayList<>();
        for (PartialSequenceResult psr : sequences) {
            Collections.addAll(throwList, psr.getSequence());
        }
        return throwList;
    }

    private static List<Match.Throw> getThrowsForPlayer(List<PartialSequenceResult> sequences, Match.Player player) {
        List<Match.Throw> playerThrows = new ArrayList<>();

        List<Match.Player> turnList = getPlayerTurnsFromSequences(sequences);
        List<Match.Throw> throwList = getThrowsFromSequences(sequences);
        int count = turnList.size() > throwList.size() ? turnList.size() : throwList.size();
        for (int i = 0; i < count; i++) {
            Match.Player currentPlayer = turnList.get(i);
            Match.Throw t = throwList.get(i);
            if (currentPlayer == player) playerThrows.add(t);
        }

        return playerThrows;
    }

}
