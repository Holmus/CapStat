package capstat.model;

public class UserLedger {

    private static UserLedger instance;

    private UserLedger() {

    }

    public static UserLedger getInstance() {
        if (instance == null) {
            instance = new UserLedger();
        }
        return instance;
    }

    public boolean isNicknameValid(String nickname) {
        return nickname.matches("^[A-Za-zÅÄÖåäöü0-9 \\(\\)\\.]+$");
    }
}
