package capstat.infrastructure;

/**
 * A value object a throw sequence, as stored in a database table.
 * @author hjorthjort
 */
public final class PartialSequenceBlueprint {
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
     * An array representing the throws. True indicates hit, false indictes
     * miss
     */
    public final boolean[] sequence;

    public PartialSequenceBlueprint(final boolean[] glasses, final int
            startingPlayer, final boolean throwBeforeWasHit, final boolean[]
                                            sequence) {
        this.glasses = glasses;
        this.startingPlayer = startingPlayer;
        this.throwBeforeWasHit = throwBeforeWasHit;
        this.sequence = sequence;
    }
}
