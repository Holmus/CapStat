package capstat.infrastructure.database;

import java.util.Arrays;

/**
 * A value object a throw sequence, as stored in a database table.
 * @author Rikard Hjort
 * @Author Johan Andersson
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

    /**
     * True if the hit before the current one was a hit, false if not.
     */
    public final boolean throwBeforeWasHit;

    /**
     * An array representing the throws. True indicates hit, false indicates
     * miss.
     */
    public final boolean[] sequence;

    /**
     * The main constructor for this class. Creates a new PartialSequenceBlueprint.
     * @param glasses An array of booleans representing all glasses on the field.
     *                True is an active glass, false is an inactive glass.
     * @param startingPlayer The player starting player for this partial sequence.
     *                       Should be one or two.
     * @param throwBeforeWasHit Boolean telling if the hit before this sequence was a hit or not.
     *                          True if hit, false if miss.
     * @param sequence An array representing the throws. True indicates hit, false indicates miss.
     */
    public PartialSequenceBlueprint(final boolean[] glasses, final int
            startingPlayer, final boolean throwBeforeWasHit, final boolean[]
                                            sequence) {
        this.glasses = glasses;
        this.startingPlayer = startingPlayer;
        this.throwBeforeWasHit = throwBeforeWasHit;
        this.sequence = sequence;
    }

    @Override
    public int hashCode() {
        int result;
        result = Arrays.hashCode(glasses);
        result = 31 * result + startingPlayer;
        result = 31 * result + (throwBeforeWasHit ? 1 : 0);
        result = 31 * result + Arrays.hashCode(sequence);
        return result;
    }

    @Override
    public boolean equals(Object o) {

        if (o == null) return false;
        if (!(o instanceof PartialSequenceBlueprint)) return false;

        PartialSequenceBlueprint psb = (PartialSequenceBlueprint)o;
        return (Arrays.equals(psb.glasses, this.glasses)
                && psb.startingPlayer == this.startingPlayer
                && psb.throwBeforeWasHit == this.throwBeforeWasHit
                && Arrays.equals(psb.sequence, this.sequence));
    }
}
