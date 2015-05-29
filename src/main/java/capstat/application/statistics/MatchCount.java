package capstat.application.statistics;

import capstat.model.statistics.MatchResult;
import capstat.model.statistics.Plottable;
import capstat.model.user.User;

import java.util.LinkedList;
import java.util.List;

/**
 * @author hjorthjort
 * A class for getting plottables which gives the number of games played as a
 * value. The first match in the list will get value 1, the second 2, etc.
 */
public class MatchCount implements Statistic {

    @Override
    public List<Plottable> getStatistics(final List<MatchResult> matches, final User user) {
        List<Plottable> ret = new LinkedList<>();
        for (MatchResult matchResult : matches) {
            ret.add(new Plottable() {
                @Override
                public double getValue() {
                    return matches.indexOf(matchResult);
                }

                @Override
                public String getLabel() {
                    return Double.toString(getValue());
                }
            });
        }
        return ret;
    }
}
