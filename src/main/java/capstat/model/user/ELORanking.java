package capstat.model.user;

/**
 * @author Christian Persson, reviewed by hjorthjort
 * Immutable value object. A ranking is always kept to two decimal places,
 * even if it is created more precisely.
 */
public class ELORanking {

    private final double points;
    private static final double EPS = 0.01;


    /**
     * Creates a new ELORanking instance representing the given points.
     * @param points the ELO points of this ELORanking.
     * @throws IllegalArgumentException if points < 0.
     */
    public ELORanking(double points) {
        if (points < 0) throw new IllegalArgumentException("Only positive EO " +
                "rankings are allowed");
        this.points = round(points);
    }

    /**
     * Rounds the ranking to two decimal places.
     * @param points
     * @return points rounded to two decimal places
     */
    private static double round(double points) {
        points = points * (1/EPS);
        points = Math.round(points);
        points = points / (1/EPS);
        return points;
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

        if (!(Math.abs(this.points - ranking.getPoints()) < EPS)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        long hashing = Double.doubleToLongBits(round(points));
        return (int) (hashing ^ (hashing >>> 32));
    }

    /**
     * @return an array with exactly two double values: The first
     * representing the winners new score, and the second representing the
     * losers.
     */
    public static double[] calculateNewRanking(final ELORanking winner, final
    ELORanking loser, int winnerRoundsWon, int loserRoundsWon) {
        int totalRounds = winnerRoundsWon + loserRoundsWon;
        //Current point + 32 * (score - expectedScore)
        double winnerNewRanking = winner.getPoints() + 32 * (winnerRoundsWon -
                calculateExpectedScore(winner.getPoints(), loser.getPoints(),
                        totalRounds));
        double loserNewRanking =  loser.getPoints() + 32 * (loserRoundsWon -
                calculateExpectedScore(loser.getPoints(), winner.getPoints(),
                        totalRounds));

        winnerNewRanking = round(winnerNewRanking);
        loserNewRanking = round(loserNewRanking);
        double[] ret = {winnerNewRanking, loserNewRanking};
        return ret;
    }

    /**
     * Calculates expected score of a player in a number of matches, based on
     * their rankings
     * @param player the player to calculate expected score for
     * @param opponent the opponent
     * @param totalRounds the total number of rounds played
     * @return
     */
    private static double calculateExpectedScore(double player, double
            opponent, int totalRounds) {
        double denominator = 1 + Math.pow(10, (opponent-player)/400);
        return totalRounds/denominator;
    }


}
