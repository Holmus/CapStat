package capstat.infrastructure;

import java.time.Instant;
import java.util.List;

/**
 * @author hjorthjort
 */
public final class MatchResultBlueprint {
    public final long id;
    public final String player1Nickname, player2Nickname, spectatorNickname;
    public final int player1score, player2score;
    public final long startTime, endTime;
    public final List<PartialSequenceBlueprint> sequences;

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
}
