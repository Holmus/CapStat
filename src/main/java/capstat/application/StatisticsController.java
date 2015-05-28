package capstat.application;

import capstat.model.statistics.MatchResult;
import capstat.model.statistics.Plottable;
import capstat.model.statistics.ResultLedger;
import capstat.model.statistics.SingleMatchCalculator;
import capstat.model.user.User;


import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Jakob on 21/05/15.
 */
public class StatisticsController {
    public final Statistic ACCURACY = new Accuracy();
    public final Statistic TIME = null;
    public final Statistic MATCH_COUNT = null;

    Double[] test = new Double[]{
      4.3, 3.5, 7.8, 6.3, 8.0
    };
    Double[] test2 = new Double[]{
            2.1, 0.3, 8.1, 4.2, 10.8, 9.23, 8.56
    };
    public List<Plottable> getData(String dataType, User user) {
        Set<MatchResult> matches = ResultLedger.getInstance()
                .getMatchesForUser(user);

        //TODO Finish up

        if (dataType.equals("Accuracy")) {
        } else {
        }
        return null;
    }

    public interface Statistic {
        List<Plottable> getStatistics(List<MatchResult> matches, User user);
    }

    /**
     * A plottable class representing hit accuracy.
     */
    public static class Accuracy implements Statistic {

        @Override
        public List<Plottable> getStatistics(final List<MatchResult> matches,
         final User user) {
            //Create a list to be returned.
            List<Plottable> ret = new LinkedList<>();
            //Iterate over the given list of matches
            for (final MatchResult result : matches) {
                //For every match, add a new plottable
                ret.add(new Plottable() {
                    //Calculate accuracy for the user in this match
                    private double accuracy = new SingleMatchCalculator
                            (result).getAccuracy(user);

                    @Override
                    public double getValue() {
                        return accuracy;
                    }

                    @Override
                    public String getLabel() {
                        return String.valueOf(accuracy);
                    }
                });
            }
            return ret;
        }
    }
}
