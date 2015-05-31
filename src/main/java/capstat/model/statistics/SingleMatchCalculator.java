package capstat.model.statistics;

import capstat.infrastructure.database.PartialSequenceBlueprint;
import capstat.infrastructure.eventbus.EventBus;
import capstat.infrastructure.eventbus.NotifyEventListener;
import capstat.model.match.Match;
import capstat.model.match.MatchFactory;
import capstat.model.user.User;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Christian Persson
 */
public class SingleMatchCalculator {

    private MatchResult mr;

    public SingleMatchCalculator(MatchResult mr) {
        this.mr = mr;
    }

    public MatchResult getMatchResult() {
        return mr;
    }

    /**
     * Calculates the accuracy for the given player. The accuracy is given as a
     * double in the range [0, 1].
     *
     * @param player the Player to calculate the accuracy for (Match.Player.ONE
     *               or Match.Player.TWO)
     *
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
     * Gets accuracy for this User. This can be helpful in cases where the
     * client is unsure if the given User is player 1 or player 2.
     * @param user
     * @return the accuracy of this User
     * @throws IllegalArgumentException if the User did not play this match
     */
    public double getAccuracy(User user) {
        return this.getAccuracy(decideWhichPlayerUserIs(user));
    }

    /**
     * Returns the length of the longest duel of the game. All throws that are
     * hits are counted as part of the duel; the miss that ends the duel is NOT
     * counted as part of the duel.
     *
     * @return the length of the longest duel
     */
    public int getLongestDuelLength() {
        List<PartialSequenceResult> psrs = getDuelSequences(mr.getSequences());
        int longest = 0;
        for (PartialSequenceResult psr : psrs) {
            int current = 0;
            for (Match.Throw t : psr.getSequence()) {
                if (t == Match.Throw.HIT) current++;
            }
            longest = current > longest ? current : longest;
        }
        return longest;
    }

    /**
     * Returns the total number of throws for the entire match - that is, all
     * throws made by player 1, plus all throws made by player 2.
     *
     * @return the number of throws
     */
    public int getTotalNumberOfThrows() {
        return getThrowsFromSequences(mr.getSequences()).size();
    }

    /**
     * Returns the total number of throws made by either player 1 or player 2.
     *
     * @param player the {@link Match.Player} to get the number
     *               of throws for
     *
     * @return the number of throws made by the given player
     */
    public int getTotalNumberOfThrows(Match.Player player) {
        return getThrowsForPlayer(mr.getSequences(), player).size();
    }

    /**
     * Convenience method for getting number of throws for a user in the
     * match, without figuring out which player that user is.
     * @param user the user who's throws is to be returned
     * @return number of throws the user made during match
     */
    public int getTotalNumberOfThrows(User user) {
        return this.getTotalNumberOfThrows(this.decideWhichPlayerUserIs(user));
    }

    /**
     * Returns the length, in seconds, that the match went on for.
     *
     * @return a <tt>long</tt> with the number of seconds
     */
    public long getElapsedTime() {
        Instant start = mr.getStartTime();
        Instant end = mr.getEndTime();
        return start.until(end, ChronoUnit.SECONDS);
    }

    /**
     * Takes an arbitrary list of {@link PartialSequenceResult}
     * and "replays" them, giving back a new list of {@link PartialSequenceResult}
     * with each instance containing the sequence from the throw after the last
     * duel (or from the beginning of the match) up to and including the throw
     * that ends the next duel.
     *
     * @param sequences the arbitrary list of sequences
     *
     * @return a {@link java.util.List} of {@link PartialSequenceResult}
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


    /**
     * Method for deciding which player in a match the given user is
     * @param user the user to find
     * @return Match.Player.ONE if user is player 1, Match.Player.TWO if user
     * is player 2.
     * @throws IllegalArgumentException if user did not play this match
     */
    private Match.Player decideWhichPlayerUserIs(User user) {
        User p1 = this.mr.getPlayer1();
        User p2 = this.mr.getPlayer2();
        if (user.equals(p1)) {
            return Match.Player.ONE;
        }
        else if (user.equals(p2)) {
            return Match.Player.TWO;
        } else {
            throw new IllegalArgumentException("User is not in this match");
        }
    }

}
