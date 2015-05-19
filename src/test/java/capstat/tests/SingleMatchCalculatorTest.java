package capstat.tests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import capstat.infrastructure.MatchResultBlueprint;
import capstat.infrastructure.PartialSequenceBlueprint;
import capstat.model.Match;
import capstat.model.MatchResult;
import capstat.model.SingleMatchCalculator;

public class SingleMatchCalculatorTest {

    @Test
    public void accuracyTest() {
        List<PartialSequenceBlueprint> psbs = new ArrayList<>();
        MatchResultBlueprint mrb;
        Instant start = Instant.now();
        Instant end = start.plusSeconds(637);
        double player1Accuracy;
        double player2Accuracy;
        MatchResult mr;
        SingleMatchCalculator smc;

        // A test using a single partial sequence
        PartialSequenceBlueprint psb = new PartialSequenceBlueprint(
            new boolean[] { true, true, true, true, true, true, true },
            1,
            false,
            new boolean[] { false, false, false, false, false, true, true, true, false, false, true, true, true, false, false, false, true, false, false, true, true, true, true, false, false, false, false, false, true, true, false, false, true, true, true, true, false, false, false, true, true, true, true, true, true, true, false, true, true, true, true, false, false, false, false, false, false, false, true, true, true, false, false, true, true, true, false, false, false, false, false, false, false, true, true, true, true, true, false, false, false, true, true, true, true, true, true, true, false }
        );

        psbs.clear();
        psbs.add(psb);
        mrb = new MatchResultBlueprint(
            -1,
            "Dummy 1",
            "Dummy 2",
            "Spectator",
            0,
            2,
            start,
            end,
            psbs
        );
        mr = new MatchResult(mrb);

        smc = new SingleMatchCalculator(mr);

        player1Accuracy = smc.getAccuracy(Match.Player.ONE);
        player2Accuracy = smc.getAccuracy(Match.Player.TWO);

        assertEquals("Player 1 accuracy for match should be around 46,81%", 0.4681, player1Accuracy, 0.01);
        assertEquals("Player 2 accuracy for match should be around 58,14%", 0.5814, player2Accuracy, 0.01);

        // ---------------------------------------------------------------------

        // The same match as above, with the same sequence, but split into
        // several partial sequences, where each sequence starts at the
        // beginning of a duel and ends with a miss that ends a duel

        // The first two duels in the first partial sequence
        PartialSequenceBlueprint psb1 = new PartialSequenceBlueprint(
            new boolean[] { true, true, true, true, true, true, true },
            1,
            false,
            new boolean[] { false, false, false, false, false, true, true, true, false, false, true, true, true, false }
        );
        // Duels 3 (inclusive) to 5 (inclusive)
        PartialSequenceBlueprint psb2 = new PartialSequenceBlueprint(
            new boolean[] { false, false, true, true, true, true, true },
            1,
            false,
            new boolean[] { false, false, true, false, false, true, true, true, true, false, false, false, false, false, true, true, false }
        );
        // Duel 5 ended the match, so a new round is initiated (hence all glasses being active)

        // Duel 6
        PartialSequenceBlueprint psb3 = new PartialSequenceBlueprint(
            new boolean[] { true, true, true, true, true, true, true },
            1,
            false,
            new boolean[] { false, true, true, true, true, true, false }
        );
        // Duel 7 to 8
        PartialSequenceBlueprint psb4 = new PartialSequenceBlueprint(
            new boolean[] { false, true, true, true, true, true, true },
            1,
            false,
            new boolean[] { false, false, true, true, true, true, true, true, true, false, true, true, true, true, false }
        );
        // Duel 9 to 12 (ending the match)
        PartialSequenceBlueprint psb5 = new PartialSequenceBlueprint(
            new boolean[] { false, true, true, true, true, false, false },
            2,
            false,
            new boolean[] { false, false, false, false, false, false, true, true, true, false, false, true, true, true, false, false, false, false, false, false, false, true, true, true, true, true, false, false, false, true, true, true, true, true, true, true, false }
        );

        psbs.clear();
        psbs.add(psb1);
        psbs.add(psb2);
        psbs.add(psb3);
        psbs.add(psb4);
        psbs.add(psb5);
        mrb = new MatchResultBlueprint(
            -1,
            "Dummy 1",
            "Dummy 2",
            "Spectator",
            0,
            2,
            start,
            end,
            psbs
        );
        mr = new MatchResult(mrb);

        smc = new SingleMatchCalculator(mr);

        player1Accuracy = smc.getAccuracy(Match.Player.ONE);
        player2Accuracy = smc.getAccuracy(Match.Player.TWO);

        assertEquals("Player 1 accuracy for match should be around 46,81%", 0.4681, player1Accuracy, 0.01);
        assertEquals("Player 2 accuracy for match should be around 58,14%", 0.5814, player2Accuracy, 0.01);
    }

}
