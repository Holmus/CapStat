package capstat.model;

/**
 * @author Christian Persson
 */
public class ELORanking {

    private double points;

    /**
     * Creates a new ELORanking instance representing the given points.
     * @param points the ELO points of this ELORanking
     */
    public ELORanking(double points) {
        this.points = points;
    }

    /**
     * Creates a new ELORanking instance, as a copy of the given ELORanking instance.
     * @param ranking the ELORanking instance to create a copy of
     */
    public ELORanking(ELORanking ranking) {
        this.points = ranking.getPoints();
    }

    /**
     * Gives the default ranking, which every new player starts with.
     * @return an ELORanking instance representing the default ranking
     */
    public static ELORanking defaultRanking() {
        return new ELORanking(1500);
    }

    /**
     * Returns the number of points this ELORanking represents.
     * @return the number of points this ELORanking represents
     */
    public double getPoints() {
        return this.points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        ELORanking ranking = (ELORanking) o;

        final double EPS = 0.0001;
        if (!(Math.abs(this.points - ranking.getPoints()) < EPS)) return false;

        return true;
    }

}
