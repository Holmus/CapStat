package capstat.tests;

import capstat.infrastructure.DatabaseQueryFactory;
import capstat.infrastructure.UserDatabaseRow;
import capstat.infrastructure.UserQueryHelper;
import capstat.model.User;
import capstat.model.UserFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author hjorthjort
 */
public class UserDatabaseFacadeTest {

    private static UserQueryHelper userdb;

    @BeforeClass
    public static void init() {
        DatabaseQueryFactory factory = new DatabaseQueryFactory();
        userdb = factory.createUserQueryHelper();
    }

    @Test
    public void getUserTest() {
        User dummy = UserFactory.createDummyUser1();
        userdb.addUserToDatabase(dummy);
        UserDatabaseRow u = userdb.getUserByNickname(dummy.getNickname());
        //TODO make sure User fields match UserDatabaseRowFields
    }

}
