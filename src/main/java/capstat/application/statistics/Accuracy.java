package capstat.application.statistics;

import capstat.model.statistics.MatchResult;
import capstat.model.statistics.Plottable;
import capstat.model.statistics.SingleMatchCalculator;
import capstat.model.user.User;

import java.util.LinkedList;
import java.util.List;

/**
 * A plottable class representing hit accuracy.
 */
public class Accuracy implements Statistic {

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
