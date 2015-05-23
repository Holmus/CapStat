package capstat.model;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import capstat.infrastructure.EventBus;
import capstat.infrastructure.NotifyEventListener;
import capstat.infrastructure.PartialSequenceBlueprint;
import capstat.model.Match;
import capstat.model.MatchFactory;
import capstat.model.MatchResult;
import capstat.model.PartialSequenceResult;

/**
 * @author Christian Persson
 */
public class SingleMatchCalculator {

    private MatchResult mr;

    public SingleMatchCalculator(MatchResult mr) {
        this.mr = mr;
    }

    /**
     * Calculates the accuracy for the given player. The accuracy is given as a double in the range [0, 1].
     * @param player the Player to calculate the accuracy for (Match.Player.ONE or Match.Player.TWO)
     * @return a double containing the accuracy
     */
    public double getAccuracy(Match.Player player) {
        int hits = 0, total;
        List<PartialSequenceResult> sequences = mr.getSequences();
        List<Match.Throw> playerThrows = getThrowsForPlayer(sequences, player);
        for (Match.Throw t : playerThrows) {
            if (t == Match.Throw.HIT) hits++;
        }

        total = getTotalNumberOfThrows(player);

        return (double) hits / (double) total;
    }

    /**
     * Returns the total number of throws for the entire match - that is, all
     * throws made by player 1, plus all throws made by player 2.
     * @return the number of throws
     */
    public int getTotalNumberOfThrows() {
        return getThrowsFromSequences(mr.getSequences()).size();
    }

    /**
     * Returns the total number of throws made by either player 1 or player 2.
     * @param player the {@link capstat.model.Match.Player} to get the number of throws for
     * @return the number of throws made by the given player
     */
    public int getTotalNumberOfThrows(Match.Player player) {
        return getThrowsForPlayer(mr.getSequences(), player).size();
    }

    public long getElapsedTime() {
        Instant start = mr.getStartTime();
        Instant end = mr.getEndTime();
        return start.until(end, ChronoUnit.SECONDS);
    }

    /**
     * Takes an arbitrary list of {@link capstat.model.PartialSequenceResult} and "replays" them, giving back a new list of {@link capstat.model.PartialSequenceResult} with each instance containing the sequence from the throw after the last duel (or from the beginning of the match) up to and including the throw that ends the next duel.
     * @param sequences the arbitrary list of sequences
     * @return a {@link java.util.List} of {@link capstat.model.PartialSequenceResult}
     */
    public static List<PartialSequenceResult> getDuelSequences(List<PartialSequenceResult> sequences) {
        List<PartialSequenceResult> duels = new ArrayList<>();
        List<PartialSequenceBlueprint> duelBlueprints = new ArrayList<>();

        List<Match.Throw> allThrows = getThrowsFromSequences(sequences);
        List<Match.Throw> duelThrows = new ArrayList<>();

        Match underlyingMatch = MatchFactory.createDefaultMatch();
        underlyingMatch.startMatch();

        NotifyEventListener listener = new NotifyEventListener() {

            private PartialSequenceResult psr = sequences.get(0);
            private boolean[] glasses = fromGlasses(psr.getGlasses());
            private int startingPlayer = fromPlayer(psr.getStartingPlayer());
            private boolean throwBeforeWasHit = psr.throwBeforeWasHit();

            @Override
            public void notifyEvent(String event) {
                PartialSequenceBlueprint psb = new PartialSequenceBlueprint(
                    glasses,
                    startingPlayer,
                    throwBeforeWasHit,
                    fromThrows(duelThrows)
                );
                duelBlueprints.add(psb);

                // Set the initial data for the next sequence
                glasses = fromGlasses(underlyingMatch.getGlasses());
                startingPlayer = fromPlayer(underlyingMatch.getPlayerWhoseTurnItIs());
                throwBeforeWasHit = false;
                duelThrows.clear();
            }

            private boolean[] fromGlasses(Match.Glass[] glasses) {
                boolean[] bools = new boolean[glasses.length];
                for (int i = 0; i < bools.length; i++) {
                    bools[i] = glasses[i].isActive();
                }
                return bools;
            }

            private int fromPlayer(Match.Player player) {
                return player == Match.Player.ONE ? 1 : 2;
            }

            private boolean[] fromThrows(List<Match.Throw> throwList) {
                boolean[] bools = new boolean[throwList.size()];
                for (int i = 0; i < bools.length; i++) {
                    bools[i] = throwList.get(i) == Match.Throw.HIT ? true : false;
                }
                return bools;
            }

        };

        EventBus eb = EventBus.getInstance();
        eb.addNotifyEventListener(Match.DUEL_ENDED, listener);

        for (Match.Throw t : allThrows) {
            duelThrows.add(t);
            if (t == Match.Throw.HIT)
                underlyingMatch.recordHit();
            else
                underlyingMatch.recordMiss();
        }

        for (PartialSequenceBlueprint blueprint : duelBlueprints) {
            duels.add(new PartialSequenceResult(blueprint));
        }

        return duels;
    }

    private static List<Match.Player> getPlayerTurnsFromSequences(List<PartialSequenceResult> sequences) {
        List<Match.Player> turns = new ArrayList<>();

        // The match logic is already defined in the Match class, so we use it instead of rewriting it here
        Match underlyingMatch = MatchFactory.createDefaultMatch();
        underlyingMatch.startMatch();

        for (PartialSequenceResult psr : sequences) {
            underlyingMatch.setCurrentPlayer(psr.getStartingPlayer());

            for (Match.Throw t : psr.getSequence()) {
                turns.add(underlyingMatch.getPlayerWhoseTurnItIs());
                if (t == Match.Throw.HIT)
                    underlyingMatch.recordHit();
                else
                    underlyingMatch.recordMiss();
            }
        }

        return turns;
    }

    private static List<Match.Throw> getThrowsFromSequences(List<PartialSequenceResult> sequences) {
        List<Match.Throw> throwList = new ArrayList<>();
        for (PartialSequenceResult psr : sequences) {
            Collections.addAll(throwList, psr.getSequence());
        }
        return throwList;
    }

    private static List<Match.Throw> getThrowsForPlayer(List<PartialSequenceResult> sequences, Match.Player player) {
        List<Match.Throw> playerThrows = new ArrayList<>();

        List<Match.Player> turnList = getPlayerTurnsFromSequences(sequences);
        List<Match.Throw> throwList = getThrowsFromSequences(sequences);
        int count = turnList.size() > throwList.size() ? turnList.size() : throwList.size();
        for (int i = 0; i < count; i++) {
            Match.Player currentPlayer = turnList.get(i);
            Match.Throw t = throwList.get(i);
            if (currentPlayer == player) playerThrows.add(t);
        }

        return playerThrows;
    }

}
