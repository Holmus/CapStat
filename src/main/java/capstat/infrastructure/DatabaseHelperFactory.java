package capstat.infrastructure;

/**
 * @author hjorthjort
 */
public class DatabaseHelperFactory {

    public UserDatabaseHelper createUserQueryHelper() {
        return new DatabaseFacade();
    }

    public MatchDatabaseHelper createMatchQueryHelper() {
        return new DatabaseFacade();
    }
}
