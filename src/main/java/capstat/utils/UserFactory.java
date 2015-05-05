package capstat.utils;

import capstat.model.Admittance;
import capstat.model.Birthday;
import capstat.model.ChalmersAge;
import capstat.model.ELORanking;
import capstat.model.User;
import capstat.utils.Security;

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
        Admittance admittance = new Admittance(2013, 1);
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
        Admittance admittance = new Admittance(2014, 2);
        ChalmersAge chalmersAge = new ChalmersAge(birthday, admittance);

        ELORanking ranking = ELORanking.defaultRanking();

        User user = new User(nickname, name, hashedPassword, chalmersAge, ranking);
        return user;
    }
}
