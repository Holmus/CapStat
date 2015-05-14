package capstat.model;

import capstat.infrastructure.PartialSequenceBlueprint;

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
    public final boolean[] glasses;

    /**
     * An int representing starting player. Must be either 1 or 2.
     */
    public final int startingPlayer;

    public final boolean throwBeforeWasHit;

    /**
     * An array representing the throws. True indicates hit, false indicates
     * miss
     */
    public final boolean[] sequence;

    public PartialSequenceResult(PartialSequenceBlueprint blueprint) {
        this.glasses = blueprint.glasses;
        this.startingPlayer = blueprint.startingPlayer;
        this.throwBeforeWasHit = blueprint.throwBeforeWasHit;
        this.sequence = blueprint.sequence;
    }
}
