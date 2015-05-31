package capstat.infrastructure.database;

import java.util.Set;

/**
 * An interface for a Facade used to get and add matches from the database.
 * @author Johan Andersson
 */
public interface ResultDatabaseHelper {

	/**
	 * Stores a match in the database.
	 * @param match The blueprint of a match to be inserted into the database.
	 */
	void addMatch(MatchResultBlueprint match);

	/**
	 * Stores a set of matches in the database.
	 * @param matches The set of blueprints of matches to be inserted into the database.
	 */
	void addMatchSet(Set<MatchResultBlueprint> matches);

	/**
	 * Removes a match from the database.
	 * @param id the matchID of the match to remove.
	 */
	void removeMatch(long id);

	/**
	 * Returns the match matching the specified match ID.
	 * @param id The ID of the match to be found.
	 * @return MatchResultBlueprint Match result blueprint for the match with the
	 * given id if that match exists in the database. Returns null otherwise.
	 */
	MatchResultBlueprint getMatchById(long id);

	/**
	 * Returns a set of all matches currently saved in the database.
	 * @return Set The set of all matches.
	 */
	Set<MatchResultBlueprint> getAllMatches();

	/**
	 * Returns a set of matches that has a player with the specified nickname.
	 * @param player The user to get all matches for.
	 * @return Set The set of matches for the specified user.
	 */
	Set<MatchResultBlueprint> getMatchesForUser(String player);

	/**
	 * Returns a set of matches that has two players with the specified nicknames.
	 * @param p1 One of the users to matches for.
	 * @param p2 One of the users to matches for.
	 * @return Set The set of matches where the two specified users are players.
	 */
	Set<MatchResultBlueprint> getMatchesForUsers(String p1, String p2);

	/**
	 * Returns a set of matches which all has the end date/time in the specified range.
	 * @param epochFrom The lower value in the range of date/time to search for.
	 * @param epochTo The upper value in the range of date/time to search for.
	 * @return Set The set of matches that have an end date/time within the given range.
	 */
	Set<MatchResultBlueprint> getMatchesInDateRange(long epochFrom, long epochTo);

	/**
	 * Returns a set of matches that has a spectator with the specified nickname.
	 * @param spectator The user to get all matches for.
	 * @return Set The set of matches for the specified user.
	 */
	Set<MatchResultBlueprint> getMatchesForSpectator(String spectator);

}
