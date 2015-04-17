package capstat.model;

public class Birthday implements Comparable<Birthday> {

    private int year, month, day;

    public Birthday(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public Birthday(Birthday birthday) {
        this.year = birthday.getYear();
        this.month = birthday.getMonth();
        this.day = birthday.getDay();
    }

    public int getYear() {
        return this.year;
    }

    public int getMonth() {
        return this.month;
    }

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
