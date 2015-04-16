package capstat.model;

public class ChalmersAge implements Comparable<ChalmersAge> {

    private Birthday birthday;
    private Admittance admittance;

    public ChalmersAge(Birthday birthday, Admittance admittance) {
        this.birthday = birthday;
        this.admittance = admittance;
    }

    public ChalmersAge(ChalmersAge age) {
        this.birthday = age.getBirthday();
        this.admittance = age.getAdmittance();
    }

    public Birthday getBirthday() {
        return new Birthday(this.birthday);
    }

    public Admittance getAdmittance() {
        return new Admittance(this.admittance);
    }

    public int compareTo(ChalmersAge other) {
        Birthday otherBirthday = other.getBirthday();
        int birthdayCompare = this.birthday.compareTo(otherBirthday);
        if (birthdayCompare != 0) return birthdayCompare;

        Admittance otherAdmittance = other.getAdmittance();
        int admittanceCompare = this.admittance.compareTo(otherAdmittance);
        if (admittanceCompare != 0) return admittanceCompare;

        return 0;
    }
}
