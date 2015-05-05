package capstat.utils;

import capstat.model.Match;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * @author hjorthjort
 */
public class ThrowSequence {

    private LinkedList<PartialSequence> sequences;
    private PartialSequence currentSequence;
    private Stack<Match.Throw> undoStack;

    /**
     * Standard constructor
     * @param glassesState initial state of glasses
     * @param startingPlayerState player who starts
     * @param throwBeforeWasHit if the last throw was a hit. Use false if it
     *                          is the first throw of the match.
     */
    public ThrowSequence(Match.Glass[] glassesState, Match.Player
            startingPlayerState,
     boolean throwBeforeWasHit) {
        this.sequences = new LinkedList<>();
        this.currentSequence = new PartialSequence(glassesState,
                startingPlayerState, throwBeforeWasHit);
        this.undoStack = new Stack<>();
    }

    /**
     * Copy constructor returning deep clone
     * @param ts
     */
    public ThrowSequence(ThrowSequence ts) {
        //Get a deep clone of the sequences list
        this.sequences = new LinkedList<>(ts.getSequences());
        this.currentSequence = ts.currentSequence.clone();
        this.undoStack = (Stack<Match.Throw>)ts.undoStack.clone();
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
    public void updateRecordState(Match.Glass[] glassesState, Match.Player
            startingPlayerState, boolean throwBeforeWasHit) {
        //TODO Implement throwing exceptions, with method for checking if a
        // glasses state is valid.
        this.sequences.add(currentSequence);
        this.currentSequence = new PartialSequence(glassesState,
                startingPlayerState, throwBeforeWasHit);
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

    /**
     * This has great importance for knowing whether the game is in a duel.
     *
     * @return whether the last throw in the sequence was a hit or not.
     */
    public boolean lastThrowWasHit() {
        //If the current sequence is empty, check whether last throw was a
        // hit, which would indicate the game is in a duel.
        if (this.currentSequence.isEmpty()) {
            return this.currentSequence.throwBeforeWasHit();
        }
        //If the sequence is not empty, get the last element of it and check
        // whether it was a hit.
        List<Match.Throw> list = this.currentSequence.getSequence();
        return list.get(list.size()-1) == Match.Throw.HIT;
    }

    /**
     * Returns a deep copy of the list representing the partial sequences of
     * this ThrowSequence.
     * @return
     */
    public List<PartialSequence> getSequences() {
        LinkedList<PartialSequence> clone = new LinkedList<>(this.sequences);
        clone.add(this.currentSequence.clone());
        return clone;
    }

    /**
     * Objects of this class are immutable, except for by instances of
     * ThrowSequence.
     */
    public class PartialSequence implements Cloneable {
        private Match.Glass[] glasses;
        private Match.Player startingPlayer;
        private boolean throwBeforeWasHit;
        private LinkedList<Match.Throw> sequence;

        private PartialSequence(Match.Glass[] glasses, Match.Player
                startingPlayer, boolean throwBeforeWasHit) {
            this.glasses = glasses;
            this.startingPlayer = startingPlayer;
            this.throwBeforeWasHit = throwBeforeWasHit;
            this.sequence = new LinkedList<>();
        }

        private PartialSequence(final PartialSequence partialSequence) {
            this.glasses = partialSequence.glasses.clone();
            this.startingPlayer = partialSequence.startingPlayer;
            this.throwBeforeWasHit = partialSequence.throwBeforeWasHit;
            this.sequence = new LinkedList<>(partialSequence.sequence);
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
            return glasses.clone();
        }

        public Match.Player getStartingPlayer() {
            return startingPlayer;
        }

        public boolean throwBeforeWasHit() {
            return throwBeforeWasHit;
        }

        /**
         *
         * @return a deep copy of the throw sequence of this partial sequence
         */
        public List<Match.Throw> getSequence() {
            return new LinkedList<>(this.sequence);
        }

        /**
         *
         * @return a deep copy of this sequence;
         */
        public PartialSequence clone() {
            return new PartialSequence(this);
        }
    }

    public class EmptySequenceException extends RuntimeException {
    }
}
