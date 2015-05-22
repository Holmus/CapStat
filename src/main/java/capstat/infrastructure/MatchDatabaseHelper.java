package capstat.infrastructure;

import java.util.Set;

/**
 * @author hjorthjort
 */
public interface MatchDatabaseHelper {

    void addMatchToDatabase(MatchResultBlueprint user);

    void addMatchSetToDatabase(Set<MatchResultBlueprint> userSet);

    void removeMatchFromDatabase(MatchResultBlueprint user);

    MatchResultBlueprint getMatchByUserNickname(String nickname);

    MatchResultBlueprint getMatchById(long id);

    Set<MatchResultBlueprint> getMatchesByUserNickname(String nickname);

}
