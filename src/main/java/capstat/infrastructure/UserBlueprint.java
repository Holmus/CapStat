package capstat.infrastructure;

import capstat.model.Admittance;

import java.time.Year;

/**
 * @author hjorthjort
 * Immutable value object class representing a database row for a single user.
 */
public class UserBlueprint {
    public final String nickname;
    public final String name;
    public final String hashedPassword;
    public final int birthdayYear, birthdayMonth, birthdayDay;
    public final Year admittanceYear;
    public final int admittanceReadingPeriod;
    public final double ELORanking;

    public UserBlueprint(String nickname, String name, String hashedPassword,
                         int birthdayYear,
                         int birthdayMonth,
                         int birthdayDay,
                         int admittanceYear,
                         int admittanceReadingPeriod, double ELORanking) {
        this.nickname = nickname;
        this.name = name;
        this.hashedPassword = hashedPassword;
        this.birthdayYear = birthdayYear;
        this.birthdayMonth = birthdayMonth;
        this.birthdayDay = birthdayDay;
        this.admittanceYear = Year.of(admittanceYear);
        this.admittanceReadingPeriod = admittanceReadingPeriod;
        this.ELORanking = ELORanking;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = nickname.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + hashedPassword.hashCode();
        result = 31 * result + birthdayYear;
        result = 31 * result + birthdayMonth;
        result = 31 * result + birthdayDay;
        result = 31 * result + admittanceYear.hashCode();
        result = 31 * result + admittanceReadingPeriod;
        temp = Double.doubleToLongBits(ELORanking);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {

        if (o == null) return false;
        if (!(o instanceof UserBlueprint)) return false;
        UserBlueprint udbr = (UserBlueprint)o;
        return (udbr.nickname.equals(this.nickname)
                && udbr.name.equals(this.name))
                && udbr.hashedPassword.equals(this.hashedPassword)
                && udbr.birthdayYear == this.birthdayYear
                && udbr.birthdayMonth == this.birthdayMonth
                && udbr.birthdayDay == this.birthdayDay
                && udbr.admittanceYear.equals(this.admittanceYear)
                && udbr.admittanceReadingPeriod == this.admittanceReadingPeriod
                && udbr.ELORanking == this.ELORanking;
    }
}
