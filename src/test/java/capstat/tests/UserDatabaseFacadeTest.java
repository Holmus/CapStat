package capstat.tests;

import capstat.infrastructure.DatabaseHelperFactory;
import capstat.infrastructure.UserBlueprint;
import capstat.infrastructure.UserDatabaseHelper;
import capstat.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Year;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author hjorthjort
 */
public class UserDatabaseFacadeTest {

    private static UserDatabaseHelper userdb;
    private UserBlueprint dummyRow1, dummyRow2;

    @BeforeClass
    public static void init() {
        DatabaseHelperFactory factory = new DatabaseHelperFactory();
        userdb = factory.createUserQueryHelper();
    }

    @Before
    public void addNewUser() {
        dummyRow1 = getDummyRow(UserFactory.createDummyUser1());
        userdb.addUserToDatabase(dummyRow1);
        dummyRow2 = getDummyRow(UserFactory.createDummyUser2());
        userdb.addUserToDatabase(dummyRow2);
    }

    @After
    public void removeNewUser() {
        userdb.removeUserFromDatabase(dummyRow1);
        userdb.removeUserFromDatabase(dummyRow2);
    }

    @Test
    public void getUserByNicknameTest() {
        UserBlueprint userDBRow = userdb.getUserByNickname(dummyRow1.nickname);
        testUsersAreEqual(dummyRow1, userDBRow);
    }

    @Test
    public void getUserByNameTest() {
        UserBlueprint userDBRow = userdb.getUserByName(dummyRow1.name);
        testUsersAreEqual(dummyRow1, userDBRow);
    }

    @Test
    public void getUsersByNickRegexTest() {
        //Get a set containing only the dummy user above
        Set<UserBlueprint> matches = userdb.getUsersByNicknameMatch(dummyRow2
                .nickname);
        //contains-method in Set uses equals, not ==.
        assertTrue(matches.contains(dummyRow2));
        assertFalse(matches.contains(dummyRow1));
    }

    @Test
    public void getUsersByNameRegexTest() {
        //Get a set containing only the dummy user above
        Set<UserBlueprint> matches = userdb.getUsersByNameMatch(dummyRow2
                .name);
        //contains-method in Set uses equals, not ==.
        assertTrue(matches.contains(dummyRow2));
        assertFalse(matches.contains(dummyRow1));
    }

    @Test
    public void getUsersInELORankRangeTest() {
        double lowest = dummyRow1.ELORanking > dummyRow2.ELORanking ?
                dummyRow2.ELORanking : dummyRow1.ELORanking;
        double highest = dummyRow1.ELORanking < dummyRow2.ELORanking ?
                dummyRow2.ELORanking : dummyRow1.ELORanking;
        Set<UserBlueprint> matches = userdb.getUsersInELORankRange(lowest-10.0,
                highest+10.0);
        assertTrue(matches.contains(dummyRow1));
        assertTrue(matches.contains(dummyRow2));
        matches = userdb.getUsersInELORankRange(lowest-15, lowest-1);
        assertFalse(matches.contains(dummyRow1));
        assertFalse(matches.contains(dummyRow2));
    }

    private void testUsersAreEqual(final UserBlueprint user, final
    UserBlueprint userDB) {
        String standardMessage = "Make sure the stored users fields and the database " +
                "fields contain the same information: ";
        assertEquals(standardMessage + "Nickname ", user.nickname, userDB
                .nickname);
        assertEquals(standardMessage + "Name", user.name, userDB.name);
        assertEquals(standardMessage + "Hashed password", user
                .hashedPassword, userDB
                .hashedPassword);

        //Create a new ChalmersAge from the fields in the database row objects
        ChalmersAge dummyUserChalmersAge = new ChalmersAge(
               LocalDate.of(user.birthdayYear, user.birthdayMonth,
                        user.birthdayDay),
                new Admittance(user.admittanceYear,
                        Admittance.Period.values()[user.admittanceReadingPeriod])
        );
        ChalmersAge userDBChalmersAge = new ChalmersAge(
                LocalDate.of(userDB.birthdayYear, userDB.birthdayMonth,
                        userDB.birthdayDay),
                new Admittance(userDB.admittanceYear,
                        Admittance.Period.values()[userDB
                                .admittanceReadingPeriod])
        );
        assertEquals(standardMessage + "Chalmers age", dummyUserChalmersAge,
                userDBChalmersAge);
        //Since ELORankings are not subject to arithmetical operations,
        // unless a new one is created, this is a safe comparison.
        assertTrue(standardMessage + "ELORanking", user.ELORanking ==
                userDB.ELORanking);
    }

    public UserBlueprint getDummyRow(User dummyUser) {
        LocalDate bd = dummyUser.getChalmersAge().getBirthday();
        Admittance ad = dummyUser.getChalmersAge().getAdmittance();
        ELORanking elo = dummyUser.getRanking();
        return new UserBlueprint(dummyUser.getNickname(), dummyUser.getName(),
                dummyUser.getHashedPassword(),
                bd.getYear(), bd.getMonthValue(), bd.getDayOfMonth(), ad.getYear()
                .getValue(), ad
                .getReadingPeriod().ordinal()+1, elo.getPoints()
                );
    }
}
