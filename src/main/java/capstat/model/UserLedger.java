package capstat.model;

import java.util.List;

import capstat.utils.Security;

public class UserLedger {

    private static UserLedger instance;
    private List<User> users;

    private UserLedger() {

    }

    public static UserLedger getInstance() {
        if (instance == null) {
            instance = new UserLedger();
        }
        return instance;
    }

    public boolean isNicknameValid(String nickname) {
        return nickname.matches("^[A-Za-zÅÄÖåäöü0-9 \\(\\)\\._\\-]+$");
    }

    public void registerUser(String nickname, String name, String password, Birthday birthday, Admittance admittance) {
        ChalmersAge age = new ChalmersAge(birthday, admittance);
        String hashedPassword = Security.hashPassword(password);
        ELORanking ranking = ELORanking.defaultRanking();
        this.createUserInDatabase(nickname, name, hashedPassword, age, ranking);
    }

    public void createUserInDatabase(String nickname, String name, String hashedPassword, ChalmersAge age, ELORanking ranking) {
        User user = new User(nickname, name, hashedPassword, age, ranking);
        users.add(user);
    }
}
