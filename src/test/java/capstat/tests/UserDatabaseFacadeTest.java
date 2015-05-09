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
    public void getUserByNicknameTest() {
        UserDatabaseRow dummyRow = getDummyRow();
        userdb.addUserToDatabase(dummyRow);
        UserDatabaseRow userDBRow = userdb.getUserByNickname(dummyRow.nickname);
        testUsersAreEqual(dummyRow, userDBRow);
    }

    @Test
    public void getUserByNameTest() {
        UserDatabaseRow dummyRow = getDummyRow();
        userdb.addUserToDatabase(dummyRow);
        UserDatabaseRow userDBRow = userdb.getUserByName(dummyRow.nickname);
        testUsersAreEqual(dummyRow, userDBRow);
    }

    private void testUsersAreEqual(final UserDatabaseRow user, final
    UserDatabaseRow userDB) {
        String standardMessage = "Make sure the stored users fields and the database " +
                "fields contain the same information: ";
        assertEquals(standardMessage + "Nickname ", user.nickname, userDB
                .nickname);
        assertEquals(standardMessage + "Name", user.nickname, userDB.name);
        assertEquals(standardMessage + "Hashed password", user
                .hashedPassword, userDB
                .hashedPassword);

        //Create a new ChalmersAge from the fields in the database row objects
        ChalmersAge dummyUserChalmersAge = new ChalmersAge(
                new Birthday(user.birthdayYear, user.birthdayMonth,
                        user.birthdayDay),
                new Admittance(user.admittanceYear,
                        user.admittanceReadingPeriod)
        );
        ChalmersAge userDBChalmersAge = new ChalmersAge(
                new Birthday(userDB.birthdayYear, userDB.birthdayMonth,
                        userDB.birthdayDay),
                new Admittance(userDB.admittanceYear,
                        userDB.admittanceReadingPeriod)
        );
        assertEquals(standardMessage + "Chalmers age", dummyUserChalmersAge,
                userDBChalmersAge);
        assertEquals(standardMessage + "ELORanking", user.ELORanking,
                userDB.ELORanking);
    }

    public UserDatabaseRow getDummyRow() {
        User dummyUser = UserFactory.createDummyUser1();
        Birthday bd = dummyUser.getChalmersAge().getBirthday();
        Admittance ad = dummyUser.getChalmersAge().getAdmittance();
        ELORanking elo = dummyUser.getRanking();
        return new UserDatabaseRow(dummyUser.getName(),
                dummyUser.getNickname(), dummyUser.getHashedPassword(),
                bd.getYear(), bd.getMonth(), bd.getDay(), ad.getYear(), ad
                .getReadingPeriod(), elo.getPoints()
                ));

    }
}
