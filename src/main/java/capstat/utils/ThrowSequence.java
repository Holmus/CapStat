package capstat.utils;

import capstat.model.Match;

import java.util.LinkedList;
import java.util.List;

/**
 * @author hjorthjort
 */
public class ThrowSequence {

    private List<PartialSequence> sequences;
    private PartialSequence currentSequence;

    public ThrowSequence(Match.Glass[] glassesState, int startingPlayerState) {
        this.sequences = new LinkedList<>();
        this.currentSequence = new PartialSequence(glassesState,
                startingPlayerState);
    }

    public void add(Match.Throw newThrow) {
        this.currentSequence.add(newThrow);
    }

    /**
     *
     * @param glassesState an array of glasses, representing the state of the
     *                     game.
     * @param startingPlayerState an integer, 1 or 2, representing which
     *                            player starts.
     * @throws IllegalArgumentException if glassesState does not represent a
     * valid state, or startingPlayerState is not 1 or 2.
     */
    public void updateRecordState(Match.Glass[] glassesState, int
            startingPlayerState) {
        //TODO Implement throwing exceptions, with method for checking if a
        // glasses state is valid.
        this.sequences.add(currentSequence);
        this.currentSequence = new PartialSequence(glassesState,
                startingPlayerState);
    }

    private class PartialSequence {
        private Match.Glass[] glasses;
        private int startingPlayer;
        private LinkedList<Match.Throw> sequence;

        private PartialSequence(Match.Glass[] glasses, int
                startingPlayer) {
            this.glasses = glasses;
            this.startingPlayer = startingPlayer;
            this.sequence = new LinkedList<>();
        }

        private void add(Match.Throw newThrow) {
            this.sequence.add(newThrow);
        }
    }
}
