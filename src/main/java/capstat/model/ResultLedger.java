package capstat.model;

import capstat.infrastructure.MatchDatabaseHelper;

import java.util.Map;

/**
 * A class representing a book or ledger where all matches are stored, and from
 * which matches can be retrieved. This class represents a repository in
 * the domain and delegates the issue of persistant sotrage of Match objects.
 */
public class ResultLedger {
	private static UserLedger instance;
	private Map<String, User> users;
	private MatchDatabaseHelper dbHelper;
}
