package capstat.model;

public class Admittance implements Comparable<Admittance> {

    private int year, readingPeriod;

    public Admittance(int year, int readingPeriod) {
        this.year = year;
        this.readingPeriod = readingPeriod;
    }

    public Admittance(Admittance admittance) {
        this.year = admittance.getYear();
        this.readingPeriod = admittance.getReadingPeriod();
    }

    public int getYear() {
        return this.year;
    }

    public int getReadingPeriod() {
        return this.readingPeriod;
    }

    @Override
    public int compareTo(Admittance other) {
        int otherYear = other.getYear();
        if (this.year > otherYear) return 1;
        else if (this.year < otherYear) return -1;

        int otherReadingPeriod = other.getReadingPeriod();
        if (this.readingPeriod > otherReadingPeriod) return 1;
        else if (this.readingPeriod < otherReadingPeriod) return -1;

        return 0;
    }

}
