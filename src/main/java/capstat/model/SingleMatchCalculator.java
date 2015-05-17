package capstat.model;

import capstat.model.Match;
import capstat.model.MatchResult;
import capstat.model.PartialSequenceResult;

import java.util.List;

public abstract class SingleMatchCalculator {

    public static double getAccuracy(MatchResult mr, Match.Player player) {
        int hits = 0, total = 0;
        List<PartialSequenceResult> sequences = mr.getSequences();
        for (PartialSequenceResult psr : sequences) {
            Match.Player current = psr.getStartingPlayer();
            for (Match.Throw t : psr.getSequence()) {
                if (t == Match.Throw.HIT && current == player) {
                    hits++;
                }

                current = current == Match.Player.ONE ? Match.Player.TWO : Match.Player.ONE;
                total++;
            }
        }

        return (double) hits / (double) total;
    }

}
