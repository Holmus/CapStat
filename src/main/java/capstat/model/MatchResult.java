package capstat.model;

import capstat.infrastructure.MatchResultBlueprint;
import capstat.infrastructure.PartialSequenceBlueprint;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

/**
 * An immutable value object class that can be queried to figure out how a
 * game turned out. It
 * @author hjorthjort
 */
public class MatchResult {
    public final long id;
    public final User player1, player2, spectator;
    public final int player1score, player2score;
    public final Instant startTime, endTime;
    public final List<PartialSequenceResult> sequences;

    public MatchResult(MatchResultBlueprint blueprint) {
        this.id = blueprint.id;
        UserLedger userLedger = UserLedger.getInstance();
        this.player1 = userLedger.getUserByNickname(blueprint.player1Nickname);
        this.player2 = userLedger.getUserByNickname(blueprint.player2Nickname);
        this.spectator = userLedger.getUserByNickname(blueprint.spectatorNickname);
        this.player1score = blueprint.player1score;
        this.player2score = blueprint.player2score;
        this.startTime = blueprint.startTime;
        this.endTime = blueprint.endTime;
        this.sequences = convertToPartialSequences(blueprint.sequences);
    }

    private List<PartialSequenceResult> convertToPartialSequences(final
                                           List<PartialSequenceBlueprint> sequences) {
        List<PartialSequenceResult> retList = new LinkedList<>();
        for (PartialSequenceBlueprint sequence : sequences) {
            retList.add(this.convertSingleSequenceFromBlueprint(sequence));
        }
        return retList;
    }

    private PartialSequenceResult convertSingleSequenceFromBlueprint
            (final PartialSequenceBlueprint sequence) {
        return new PartialSequenceResult(sequence);
    }
}
