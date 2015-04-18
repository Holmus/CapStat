package capstat.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import capstat.utils.Security;

public class UserLedger {

    private static UserLedger instance;
    private List<User> users;

    private UserLedger() {
        this.users = new ArrayList<>();
    }

    public static UserLedger getInstance() {
        if (instance == null) {
            instance = new UserLedger();
        }
        return instance;
    }

    public boolean isNicknameValid(String nickname) {
        Stream<Boolean> conflicts = this.users.stream().map(u -> u.getNickname().equals(nickname));
        Predicate<Boolean> noConflict = conflict -> conflict == false;
        return nickname.matches("^[A-Za-zÅÄÖåäöü0-9 \\(\\)\\._\\-]+$") && conflicts.allMatch(noConflict);
    }

    public void registerUser(String nickname, String name, String password, Birthday birthday, Admittance admittance) {
        ChalmersAge chalmersAge = new ChalmersAge(birthday, admittance);
        String hashedPassword = Security.hashPassword(password);
        ELORanking ranking = ELORanking.defaultRanking();
        this.createUserInDatabase(nickname, name, hashedPassword, chalmersAge, ranking);
    }

    private void createUserInDatabase(String nickname, String name, String hashedPassword, ChalmersAge chalmersAge, ELORanking ranking) {
        User user = new User(nickname, name, hashedPassword, chalmersAge, ranking);
        users.add(user);
    }
}
