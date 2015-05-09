package capstat.model;

/**
 * Immutable value object.
 *
 * @author Christian Persson
 */
public class Admittance implements Comparable<Admittance> {

    private final int year, readingPeriod;

    /**
     * Creates a new Admittance instance.
     * @param year the admittance year
     * @param readingPeriod a number from 1 to 4, denoting which reading period the person was admitted to/started studying at Chalmers
     */
    public Admittance(int year, int readingPeriod) {
        this.year = year;
        this.readingPeriod = readingPeriod;
    }

    /**
     * Creates a new Admittance instance, as a copy of the given Admittance instance.
     * @param admittance the Admittance instance to create a copy of
     */
    public Admittance(Admittance admittance) {
        this.year = admittance.getYear();
        this.readingPeriod = admittance.getReadingPeriod();
    }

    /**
     * Returns the admittance year.
     * @return the admittance year
     */
    public int getYear() {
        return this.year;
    }

    /**
     * Returns the admittance reading period.
     * @return the admittance reading period
     */
    public int getReadingPeriod() {
        return this.readingPeriod;
    }

    @Override
    public int compareTo(Admittance other) {
        int otherYear = other.getYear();
        if (this.year < otherYear) return 1;
        else if (this.year > otherYear) return -1;

        int otherReadingPeriod = other.getReadingPeriod();
        if (this.readingPeriod < otherReadingPeriod) return 1;
        else if (this.readingPeriod > otherReadingPeriod) return -1;

        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return false;
        if (o == null || this.getClass() != o.getClass()) return false;

        Admittance bd = (Admittance) o;

        if (!(this.year == bd.getYear())) return false;
        if (!(this.readingPeriod == bd.getReadingPeriod())) return false;

        return true;
    }

}
