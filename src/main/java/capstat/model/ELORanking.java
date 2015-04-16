package capstat.model;

public class ELORanking {

    private double points;

    public ELORanking(double points) {
        this.points = points;
    }

    public ELORanking(ELORanking ranking) {
        this.points = ranking.getPoints();
    }

    public static ELORanking defaultRanking() {
        return new ELORanking(1500);
    }

    public double getPoints() {
        return this.points;
    }

}
