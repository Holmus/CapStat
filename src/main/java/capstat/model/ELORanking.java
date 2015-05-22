package capstat.model;

/**
 * @author Christian Persson, reviewed by hjorthjort
 * Immutable value object. A ranking is always kept to two decimal places,
 * even if it is created more precisely.
 */
public class ELORanking {

    private final double points;

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

    @Override
    public int hashCode() {
        long hashing = Double.doubleToLongBits(points);
        return (int) (hashing ^ (hashing >>> 32));
    }

    /**
     * @return an array with exactly two double values: The first
     * representing the winners new score, and the second representing the
     * losers.
     */
    public static double[] calculateNewRanking(final User winner, final User
            loser, int winnerRoundsWon, int loserRoundsWon) {

        //Current point + 32 * (score - expectedScore)
        double winnerNewRanking = winner.getRanking().getPoints() + 32 *
                        ((double) winnerRoundsWon / (double)
                        loserRoundsWon -
                                calculateExpectedScore(winner.getRanking().getPoints(),
                                loser.getRanking().getPoints()));
        double loserNewRanking =  loser.getRanking().getPoints() + 32 *
                ((double) loserRoundsWon / (double)
                        winnerRoundsWon -
                        calculateExpectedScore(loser.getRanking().getPoints(),
                                winner.getRanking().getPoints()));

        winnerNewRanking = winnerNewRanking % 0.01;
        loserNewRanking = loserNewRanking % 0.01;
        double[] ret = {winnerNewRanking, loserNewRanking};
        return ret;
    }

    private static double calculateExpectedScore(double player, double
            opponent) {
        double denominator = 1 + Math.pow(10, (player-opponent)/400);
        return 1/denominator;
    }


}
