package capstat.tests;

import capstat.infrastructure.DatabaseQueryFactory;
import capstat.infrastructure.UserDatabaseRow;
import capstat.infrastructure.UserDatabaseHelper;
import capstat.model.*;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author hjorthjort
 */
public class UserDatabaseFacadeTest {

    private static UserDatabaseHelper userdb;

    @BeforeClass
    public static void init() {
        DatabaseQueryFactory factory = new DatabaseQueryFactory();
        userdb = factory.createUserQueryHelper();
    }

    @Test
    public void getUserTest() {
        User dummyUser = UserFactory.createDummyUser1();
        userdb.addUserToDatabase(dummyUser);
        UserDatabaseRow userDBRow = userdb.getUserByNickname(dummyUser.getNickname());
        String standardMessage = "Make sure the stored users fields and the database " +
                "fields contain the same information: ";
        assertEquals(standardMessage + "Nickname ", dummyUser.getNickname(), userDBRow.nickname);
        assertEquals(standardMessage + "Name", dummyUser.getName(), userDBRow.name);
        assertEquals(standardMessage + "Hashed password", dummyUser.getHashedPassword(), userDBRow
                .hashedPassword);
        //Create a new ChalmersAge from the fields in the database row object
        ChalmersAge userDBChalmersAge = new ChalmersAge(
                new Birthday(userDBRow.birthdayYear, userDBRow.birthdayMonth,
                        userDBRow.birthdayDay),
                new Admittance(userDBRow.admittanceYear,
                        userDBRow.admittanceReadingPeriod)
        );
        assertEquals(standardMessage + "Chalmers age", dummyUser
                .getChalmersAge(), userDBChalmersAge);
        assertEquals(standardMessage + "ELORanking", dummyUser.getRanking(),
                new ELORanking(userDBRow.ELORanking));
    }

}
