package capstat.tests;

import capstat.infrastructure.DatabaseQueryFactory;
import capstat.infrastructure.UserDatabaseRow;
import capstat.infrastructure.UserDatabaseHelper;
import capstat.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author hjorthjort
 */
public class UserDatabaseFacadeTest {

    private static UserDatabaseHelper userdb;
    private UserDatabaseRow dummyRow;

    @BeforeClass
    public static void init() {
        DatabaseQueryFactory factory = new DatabaseQueryFactory();
        userdb = factory.createUserQueryHelper();
    }

    @Before
    public void addNewUser() {
        UserDatabaseRow dummyRow = getDummyRow(UserFactory.createDummyUser1());
        userdb.addUserToDatabase(dummyRow);
    }

    @After
    public void removeNewUser() {
        userdb.removeUserFromDatabase(dummyRow);
    }

    @Test
    public void getUserByNicknameTest() {
        UserDatabaseRow userDBRow = userdb.getUserByNickname(dummyRow.nickname);
        testUsersAreEqual(dummyRow, userDBRow);
    }

    @Test
    public void getUserByNameTest() {
        UserDatabaseRow userDBRow = userdb.getUserByName(dummyRow.nickname);
        testUsersAreEqual(dummyRow, userDBRow);
    }

    @Test
    public void getUsersByNickRegexTest() {
        //Create a new dummy row, IN ADDITION to the one already added in
        // addNewUser method, executed @Before
        UserDatabaseRow dummyRow2 = getDummyRow(UserFactory.createDummyUser2());
        userdb.addUserToDatabase(dummyRow2);
        //Get a set containing only the dummy user above
        Set<UserDatabaseRow> matches = userdb.getUsersByNicknameMatch(dummyRow2
                .nickname);
        //contains-method in Set uses equals, not ==.
        assertTrue(matches.contains(dummyRow2));
        assertFalse(matches.contains(dummyRow));
    }

    @Test
    public void getUsersByNameRegexTest() {
        //Create a new dummy row, IN ADDITION to the one already added in
        // addNewUser method, executed @Before
        UserDatabaseRow dummyRow2 = getDummyRow(UserFactory.createDummyUser2());
        userdb.addUserToDatabase(dummyRow2);
        //Get a set containing only the dummy user above
        Set<UserDatabaseRow> matches = userdb.getUsersByNameMatch(dummyRow2
                .name);
        //contains-method in Set uses equals, not ==.
        assertTrue(matches.contains(dummyRow2));
        assertFalse(matches.contains(dummyRow));
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

    public UserDatabaseRow getDummyRow(User dummyUser) {
        Birthday bd = dummyUser.getChalmersAge().getBirthday();
        Admittance ad = dummyUser.getChalmersAge().getAdmittance();
        ELORanking elo = dummyUser.getRanking();
        return new UserDatabaseRow(dummyUser.getName(),
                dummyUser.getNickname(), dummyUser.getHashedPassword(),
                bd.getYear(), bd.getMonth(), bd.getDay(), ad.getYear(), ad
                .getReadingPeriod(), elo.getPoints()
                );
    }
}
