package capstat.infrastructure;

import java.util.Date;
import java.util.Set;

/**
 * @author jibbs
 * An interface for a Facade used to get and add matches from the database.
 */
public interface MatchDatabaseHelper {

	void addMatch(MatchBlueprint match, ThrowSequenceBlueprint throwSequence);
	void addMatchSet(Set<MatchBlueprint> matches, Set<ThrowSequenceBlueprint> throwSequences);
	void removeMatch(int id);
	MatchBlueprint getMatch(int id);
	Set<MatchBlueprint> getAllMatches();
	Set<MatchBlueprint> getMatchesForUser(String player);
	Set<MatchBlueprint> getMatchesForUsers(String p1, String p2);
	Set<MatchBlueprint> getMatchesInDateRange(Date from, Date to);
	Set<MatchBlueprint> getMatchForSpectator(String spectator);

}
