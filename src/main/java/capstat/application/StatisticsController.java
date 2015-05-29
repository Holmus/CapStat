package capstat.application;

import capstat.model.statistics.MatchResult;
import capstat.model.statistics.Plottable;
import capstat.model.statistics.ResultLedger;
import capstat.model.statistics.SingleMatchCalculator;
import capstat.model.user.User;


import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Jakob on 21/05/15.
 */
public class StatisticsController {
    public static final Statistic ACCURACY = new Accuracy();
    public final Statistic TIME = null;
    public final Statistic MATCH_COUNT = null;

    public List<Plottable> getData(Statistic dataType, User user) {
        Set<MatchResult> matches = ResultLedger.getInstance()
                .getMatchesForUser(user);
        List<MatchResult> newList = new LinkedList<>(matches);
        newList.sort((match1, match2) -> match1.getEndTime().compareTo(match2
                .getEndTime()));
        return dataType.getStatistics(newList, user);
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
