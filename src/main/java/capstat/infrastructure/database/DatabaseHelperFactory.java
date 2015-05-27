package capstat.infrastructure.database;

/**
 * A class for dependency injection of the facades for interacting with the
 * database.
 *
 * This class returns objects for interacting with the database. The static
 * type of the objects is an interface. This decouples the rest of the system
 * from the database implementation, which may thus be migrated, as long as
 * the interface is satisfied.
 *
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
