package capstat.infrastructure;

/**
 * @author hjorthjort
 */
public class DatabaseQueryFactory {

    public UserDatabaseHelper createUserQueryHelper() {
        return new DatabaseFacade();
    }
}
