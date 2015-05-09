package capstat.infrastructure;

/**
 * @author hjorthjort
 * Immutable value object class representing a database row for a single user.
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

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name.hashCode();
        result = 31 * result + nickname.hashCode();
        result = 31 * result + hashedPassword.hashCode();
        result = 31 * result + birthdayYear;
        result = 31 * result + birthdayMonth;
        result = 31 * result + birthdayDay;
        result = 31 * result + admittanceYear;
        result = 31 * result + admittanceReadingPeriod;
        temp = Double.doubleToLongBits(ELORanking);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof UserDatabaseRow)) return false;
        UserDatabaseRow udbr = (UserDatabaseRow)o;
        return (udbr.name.equals(this.name)
                && udbr.nickname.equals(this.nickname))
                && udbr.hashedPassword.equals(this.hashedPassword)
                && udbr.birthdayYear == this.birthdayYear
                && udbr.birthdayMonth == this.birthdayMonth
                && udbr.birthdayDay == this.birthdayDay
                && udbr.admittanceYear == this.admittanceYear
                && udbr.admittanceReadingPeriod == this.admittanceReadingPeriod
                && udbr.ELORanking == this.ELORanking;
    }
}
