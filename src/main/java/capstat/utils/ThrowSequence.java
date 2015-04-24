package capstat.utils;

import capstat.model.Match;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * @author hjorthjort
 */
public class ThrowSequence {

    private List<PartialSequence> sequences;
    private PartialSequence currentSequence;
    private Stack<Match.Throw> undoStack;

    public ThrowSequence(Match.Glass[] glassesState, int startingPlayerState) {
        this.sequences = new LinkedList<>();
        this.currentSequence = new PartialSequence(glassesState,
                startingPlayerState);
        this.undoStack = new Stack<>();
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
        this.undoStack.clear();
    }

    /**
     *
     * @throws EmptySequenceException if the current sequence is empty, even
     * if there are previous sequences.
     */
    public void rewind() {
        if (currentSequence.isEmpty()) throw new EmptySequenceException();
        //Removes last added element from current sequence and pushes it to
        // undoStack.
        this.undoStack.push(this.currentSequence.popLast());
    }

    /**
     *
     * @throws EmptySequenceException if the current sequence is empty, even
     * if there are previous sequences.
     */
    public void forward() {
        if(undoStack.empty()) throw new EmptySequenceException();
        this.currentSequence.add(this.undoStack.pop());
    }

    public boolean canRewind() {
        return !this.currentSequence.isEmpty();
    }

    public boolean canForward() {
        return !this.undoStack.empty();
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

        private boolean isEmpty() {
            return sequence.isEmpty();
        }

        private Match.Throw popLast() {
            return this.sequence.removeLast();
        }

        public Match.Glass[] getGlasses() {
            return glasses;
        }

        public int getStartingPlayer() {
            return startingPlayer;
        }
    }

    private class EmptySequenceException extends RuntimeException {
    }
}
