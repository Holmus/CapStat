package capstat.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;

/**
 * A class for creating Users. The methods return User objects which are the
 * root of aggregates. Users should be instantiated through this class, not
 * by calling the constructor in User
 * @author Christian Persson
 */
public abstract class UserFactory {
    public static User createDummyUser1() {
        String nickname = "DummyOne";
        String name = "Dummy Player 1";
        String password = "foobar";
        String hashedPassword = Security.hashPassword(password);

        LocalDate birthday = LocalDate.of(1992, 1, 13);
        Admittance admittance = new Admittance(Year.of(2013), Admittance
                .Period.ONE);
        ChalmersAge chalmersAge = new ChalmersAge(birthday, admittance);

        ELORanking ranking = ELORanking.defaultRanking();

        User user = new User(nickname, name, hashedPassword, chalmersAge, ranking);
        return user;
    }

    public static User createDummyUser2() {
        String nickname = "DummyTwo";
        String name = "Dummy Player 2";
        String password = "boofaz";
        String hashedPassword = Security.hashPassword(password);

        LocalDate birthday = LocalDate.of(1995, 8, 9);
        Admittance admittance = new Admittance(Year.of(2014), Admittance
                .Period.TWO);
        ChalmersAge chalmersAge = new ChalmersAge(birthday, admittance);

        ELORanking ranking = ELORanking.defaultRanking();

        User user = new User(nickname, name, hashedPassword, chalmersAge, ranking);
        return user;
    }

    public static User createGuestUser() {
        String nickname = "Guest";
        String name = "Guest";
        String password = "";
        String hashedPassword = Security.hashPassword(password);

        LocalDateTime now = LocalDateTime.now();
        LocalDate birthday = LocalDate.now();
        Admittance admittance = new Admittance(Year.now(), Admittance
                .Period.ONE);
        ChalmersAge chalmersAge = new ChalmersAge(birthday, admittance);

        ELORanking ranking = ELORanking.defaultRanking();

        return new User(nickname, name, hashedPassword, chalmersAge,
                ranking);
    }

    /**
     * Instantiate a new aggregate for a User and return the User object that
     * is the root of the aggregate.
     * @param nickname
     * @param name
     * @param password
     * @param birthday
     * @param admittanceYear
     * @param readingPeriod
     * @return
     */
    public static User createNewUser(String nickname, String name, String
            password, LocalDate birthday, Year admittanceYear, int readingPeriod) {
        if (readingPeriod < 1 || readingPeriod > 4) throw new
                IllegalArgumentException("Wrong admittance, must be between 1" +
                " and 4");
        String hashedPassword = Security.hashPassword(password);
        ELORanking eloRanking = ELORanking.defaultRanking();

        return createOldUser(nickname, name, hashedPassword, birthday,
                admittanceYear, readingPeriod, eloRanking.getPoints());
    }

    /**
     * Instantiate a user aggreagate of an old user, one already in the system.
     * @param nickname
     * @param name
     * @param hashedPassword
     * @param birthday
     * @param admittanceYear
     * @param readingPeriod
     * @param ranking
     * @return
     */
    static User createOldUser (String nickname, String name, String
            hashedPassword, LocalDate birthday, Year admittanceYear, int
            readingPeriod, double ranking) {
        ChalmersAge chalmersAge = new ChalmersAge(birthday, new Admittance
                (admittanceYear, Admittance.Period.values()[readingPeriod-1]));
        User user = new User(nickname, name, hashedPassword, chalmersAge,
                new ELORanking(ranking));
        return user;
    }
}
