package capstat.infrastructure.database;

import java.util.List;

/**
 * Immutable value object class representing a database row for a single matchResult.
 * @Author Johan Andersson
 */
public final class MatchResultBlueprint {

    public final long id;
    public final String player1Nickname, player2Nickname, spectatorNickname;
    public final int player1score, player2score;
    public final long startTime, endTime;
    public final List<PartialSequenceBlueprint> sequences;

    /**
     * The main constructor for this class. Creates a new MatchResultBlueprint object.
     * @param id The id of a match. This is a unique identifier.
     * @param player1Nickname The nickname of player 1.
     * @param player2Nickname The nickname of player 2.
     * @param spectatorNickname The nickname of the spectator.
     * @param player1score The score of player 1.
     * @param player2score The score of player 2.
     * @param startTime The time at which the match started in the format epoch-second.
     * @param endTime The time at which the match ended in the format epoch-second.
     * @param sequences A List of all partial sequences for this match.
     */
    public MatchResultBlueprint(final long id,
                                final String player1Nickname,
                                final String player2Nickname,
                                final String spectatorNickname,
                                final int player1score,
                                final int player2score,
                                final long startTime,
                                final long endTime,
                                final List<PartialSequenceBlueprint> sequences) {
        this.id = id;
        this.player1Nickname = player1Nickname;
        this.player2Nickname = player2Nickname;
        this.spectatorNickname = spectatorNickname;
        this.player1score = player1score;
        this.player2score = player2score;
        this.startTime = startTime;
        this.endTime = endTime;
        this.sequences = sequences;
    }

    @Override
    public int hashCode() {
        int result;
        result = (int) (id ^ (id >>> 32));
        result = 31 * result + player1Nickname.hashCode();
        result = 31 * result + player2Nickname.hashCode();
        result = 31 * result + spectatorNickname.hashCode();
        result = 31 * result + player1score;
        result = 31 * result + player2score;
        result = 31 * result + (int) (startTime ^ (startTime >>> 32));
        result = 31 * result + (int) (endTime ^ (endTime >>> 32));
        result = 31 * result + sequences.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {

        if (o == null) return false;
        if (!(o instanceof MatchResultBlueprint)) return false;

        MatchResultBlueprint mrb = (MatchResultBlueprint)o;
        return (mrb.id == this.id
                && mrb.player1Nickname.equals(this.player1Nickname)
                && mrb.player2Nickname.equals(this.player2Nickname)
                && mrb.spectatorNickname.equals(this.spectatorNickname)
                && mrb.player1score == this.player1score
                && mrb.player2score == this.player2score
                && mrb.startTime == this.startTime
                && mrb.endTime == this.endTime
                && mrb.sequences.equals(this.sequences));
    }
}
