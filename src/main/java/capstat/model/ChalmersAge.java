package capstat.model;

import java.time.LocalDate;

/**
 * Immutable value object.
 *
 * @author Christian Persson
 */
public class ChalmersAge implements Comparable<ChalmersAge> {

    private final LocalDate birthday;
    private final Admittance admittance;

    /**
     * Creates a ChalmersAge instance.
     * @param birthday the birthday instance for this ChalmersAge
     * @param admittance the Admittance instance for this ChalmersAge
     */
    public ChalmersAge(LocalDate birthday, Admittance admittance) {
        this.birthday = birthday;
        this.admittance = admittance;
    }

    /**
     * Creates a new ChalmersAge instance, as a copy of the given ChalmersAge instance.
     * @param chalmersAge the ChalmersAge instance to create a copy of
     */
    public ChalmersAge(ChalmersAge chalmersAge) {
        this.birthday = chalmersAge.birthday;
        this.admittance = chalmersAge.admittance;
    }

    /**
     * Returns the birthday of the ChalmersAge.
     * @return the birthday of the ChalmersAge
     */
    public LocalDate getBirthday() {
        return this.birthday;
    }

    /**
     * Returns the admittance of the ChalmersAge.
     * @return the admittance of the ChalmersAge
     */
    public Admittance getAdmittance() {
        return new Admittance(this.admittance);
    }

    /**
     * @param other
     * @return a number less than 0 if this player is older than other
     * player, in Chalmers age. If they are equal in Chalmers age, return 0.
     * Otherwise, return a number larger than 0.
     */
    @Override
    public int compareTo(ChalmersAge other) {
        //First, compare by time of admittance. Youngest first.
        Admittance otherAdmittance = other.getAdmittance();
        int admittanceCompare = this.admittance.compareTo(otherAdmittance);
        if (admittanceCompare != 0) return admittanceCompare;

        //Second, compare by birthday. Youngest first.
        LocalDate otherBirthday = other.getBirthday();
        return this.birthday.compareTo(otherBirthday);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChalmersAge age = (ChalmersAge) o;

        if (!this.birthday.equals(age.getBirthday())) return false;
        if (!this.admittance.equals(age.getAdmittance())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = birthday.hashCode();
        result = 31 * result + admittance.hashCode();
        return result;
    }
}
