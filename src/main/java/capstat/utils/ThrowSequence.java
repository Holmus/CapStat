package capstat.utils;

import capstat.model.Match;

import java.util.LinkedList;

/**
 * @author hjorthjort
 */
public class ThrowSequence {

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
    }
}
