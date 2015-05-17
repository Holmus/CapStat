package capstat.model;

import capstat.infrastructure.MatchDatabaseHelper;
import capstat.infrastructure.MatchResultBlueprint;
import capstat.infrastructure.PartialSequenceBlueprint;

import java.util.*;

/**
 * @author hjorthjort
 */
public class ResultsLedger {

    private MatchDatabaseHelper dbHelper;
    /**
     * Saves the match to the ledger. Must only be called once per match. If
     * it is called more than one time, the behavior is undefined.
     * @param match the match to be saved
     */
    public void saveResult(Match match) {
        this.dbHelper.addMatchToDatabase(this.createBlueprint(match));
    }

    /**
     * Get the match with the specified ID.
     * @param id
     * @return the match result with specified id. If no such match exits,
     * return null
     */
    public MatchResult getResultById(long id) {
        return null;
    }

    public Collection<MatchResult> getMatchesWithUser(User user) {
        Set<MatchResultBlueprint> blueprints = this.dbHelper
                .getMatchesByUserNickname(user.getNickname());
        List<MatchResult> returnMatches = new ArrayList<>();
        for (MatchResultBlueprint bp : blueprints) {
            returnMatches.add(this.reconstituteFromBlueprint(bp));
        }
        //TODO Check logic, will this work?
        return returnMatches;
    }

    private MatchResult reconstituteFromBlueprint(final MatchResultBlueprint bp) {
        return new MatchResult(bp);
    }

    private MatchResultBlueprint createBlueprint(final Match match) {
        List<ThrowSequence.PartialSequence> sequences = match
                .getThrowSequence().getSequences();
        List<PartialSequenceBlueprint> sequencesBlueprint = new LinkedList<>();
        for (ThrowSequence.PartialSequence s : sequences) {
            sequencesBlueprint.add(s.createBlueprint());
        }
        return new MatchResultBlueprint(-1, match.getPlayer(Match.Player.ONE)
                .getNickname(), match.getPlayer(Match.Player.TWO).getNickname
                (), match.getSpectator().getNickname(), match.getPlayer1Score
                (), match.getPlayer2Score(), match.getStartTime(), match
                .getEndTime(), sequencesBlueprint);
    }


}
