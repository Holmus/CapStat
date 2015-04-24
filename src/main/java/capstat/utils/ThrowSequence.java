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
