package capstat.infrastructure;

/**
 * @author hjorthjort
 * Immutable value object representing a database row for a single user.
 */
public class UserDatabaseRow {
    public final String name;
    public final String nickname;
    public final String hashedPassword;
    public final int birthdayYear, birthdayMonth, birthdayDay;
    public final int admittanceYear, admittanceReadingPeriod;
    public final double ELORanking;

    public UserDatabaseRow(String name, String nickname, String hashedPassword,
                           int birthdayYear,
                           int birthdayDay, int birthdayMonth,
                           int admittanceYear,
                           int admittanceReadingPeriod, double ELORanking) {
        this.name = name;
        this.nickname = nickname;
        this.hashedPassword = hashedPassword;
        this.birthdayYear = birthdayYear;
        this.birthdayMonth = birthdayMonth;
        this.birthdayDay = birthdayDay;
        this.admittanceYear = admittanceYear;
        this.admittanceReadingPeriod = admittanceReadingPeriod;
        this.ELORanking = ELORanking;
    }
}
