package capstat.infrastructure;

import java.util.Date;
import java.util.Set;

/**
 * @author jibbs
 * An interface for a Facade used to get and add matches from the database.
 */
public interface MatchDatabaseHelper {

	void addMatch(MatchResultBlueprint match, ThrowSequenceBlueprint throwSequence);
	void addMatchSet(Set<MatchResultBlueprint> matches, Set<ThrowSequenceBlueprint> throwSequences);
	void removeMatch(int id);
	MatchResultBlueprint getMatch(int id);
	Set<MatchResultBlueprint> getAllMatches();
	Set<MatchResultBlueprint> getMatchesForUser(String player);
	Set<MatchResultBlueprint> getMatchesForUsers(String p1, String p2);
	Set<MatchResultBlueprint> getMatchesInDateRange(Date from, Date to);
	Set<MatchResultBlueprint> getMatchForSpectator(String spectator);

}
