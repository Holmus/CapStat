package capstat.model;

public class CapStat {

    private static CapStat instance;
    private UserLedger userLedger = UserLedger.getInstance();

    private CapStat() {

    }

    public static CapStat getInstance() {
        if (instance == null) {
            instance = new CapStat();
        }
        return instance;
    }

    public boolean isNicknameValid(String nickname) {
        return this.userLedger.isNicknameValid(nickname);
    }

    public void registerUser(String nickname, String name, String password, Birthday birthday, Admittance admittance) {
        this.userLedger.registerUser(nickname, name, password, birthday, admittance);
    }
}
