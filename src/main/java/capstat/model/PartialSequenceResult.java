package capstat.model;

import capstat.infrastructure.PartialSequenceBlueprint;
import capstat.model.Match;

/**
 * An immutable value class representing a partial sequence. To preserve the
 * immutability of {@link capstat.model.ThrowSequence.PartialSequence} and
 * make sure that no extra functionality needs to be added to the {@link
 * PartialSequenceBlueprint} class if it is needed in the domain layer. This
 * class may be extended for added funcionality in the domain layer.
 * @author hjorthjort
 */
public class PartialSequenceResult {
    /**
     * An array representing the glasses at the time the partial sequence
     * starts. true value indicates active, false inactive.
     */
    public final Match.Glass[] glasses;

    /**
     * An int representing starting player. Must be either 1 or 2.
     */
    public final Match.Player startingPlayer;

    public final boolean throwBeforeWasHit;

    /**
     * An array representing the throws. True indicates hit, false indicates
     * miss
     */
    public final Match.Throw[] sequence;

    public PartialSequenceResult(PartialSequenceBlueprint blueprint) {
        this.glasses = glassesFromBooleans(blueprint.glasses);
        this.startingPlayer = playerFromInt(blueprint.startingPlayer);
        this.throwBeforeWasHit = blueprint.throwBeforeWasHit;
        this.sequence = sequenceFromBooleans(blueprint.sequence);
    }

    private static Match.Glass[] glassesFromBooleans(boolean[] bools) {
        Match.Glass[] glasses = new Match.Glass[bools.length];
        for (int i = 0; i < glasses.length; i++) {
            Match.Glass glass = new Match.Glass();
            glass.setActive(bools[i]);
            glasses[i] = glass;
        }
        return glasses;
    }

    private static Match.Player playerFromInt(int player) {
        if (player == 1)
            return Match.Player.ONE;
        else if (player == 2)
            return Match.Player.TWO;
        else
            throw new IllegalArgumentException("Must either get Player.ONE or Player.TWO");
    }

    private static Match.Throw[] sequenceFromBooleans(boolean[] bools) {
        Match.Throw[] sequence = new Match.Throw[bools.length];
        for (int i = 0; i < sequence.length; i++) {
            Match.Throw t = bools[i] ? Match.Throw.HIT : Match.Throw.MISS;
            sequence[i] = t;
        }
        return sequence;
    }
}
