package capstat.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;

/**
 * @author Christian Persson
 */
public abstract class UserFactory {
    public static User createDummyUser1() {
        String nickname = "DummyOne";
        String name = "Dummy Player 1";
        String password = "foobar";
        String hashedPassword = Security.hashPassword(password);

        Birthday birthday = new Birthday(1992, 1, 13);
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

        Birthday birthday = new Birthday(1995, 8, 9);
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
        Birthday birthday = new Birthday(now.getYear(), now.getMonthValue(), now
                .getDayOfMonth());
        Admittance admittance = new Admittance(Year.now(), Admittance
                .Period.ONE);
        ChalmersAge chalmersAge = new ChalmersAge(birthday, admittance);

        ELORanking ranking = ELORanking.defaultRanking();

        return new User(nickname, name, hashedPassword, chalmersAge,
                ranking);
    }
}
