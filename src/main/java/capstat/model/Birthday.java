package capstat.model;

/**
 * @author Christian Persson
 */
public class Birthday implements Comparable<Birthday> {

    private int year, month, day;

    /**
     * Creates a new Birthday instance.
     * @param year year of birth
     * @param month month of birth
     * @param day day of birth
     */
    public Birthday(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * Creates a new Birthday instance, as a copy of the given Birthday instance.
     * @param birthday the Birthday instance to create a copy of
     */
    public Birthday(Birthday birthday) {
        this.year = birthday.getYear();
        this.month = birthday.getMonth();
        this.day = birthday.getDay();
    }

    /**
     * Returns the year of birth.
     * @return the year of birth
     */
    public int getYear() {
        return this.year;
    }

    /**
     * Returns the month of birth.
     * @return the month of birth
     */
    public int getMonth() {
        return this.month;
    }

    /**
     * Returns the day of birth.
     * @return the day of birth
     */
    public int getDay() {
        return this.day;
    }

    @Override
    public int compareTo(Birthday other) {
        int otherYear = other.getYear();
        if (this.year > otherYear) return 1;
        else if (this.year < otherYear) return -1;

        int otherMonth = other.getMonth();
        if (this.month > otherMonth) return 1;
        else if (this.month < otherMonth) return -1;

        int otherDay = other.getDay();
        if (this.day > otherDay) return 1;
        else if (this.day < otherDay) return -1;

        return 0;
    }
}
