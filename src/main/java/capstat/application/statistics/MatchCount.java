package capstat.application.statistics;

import capstat.model.statistics.MatchResult;
import capstat.model.statistics.Plottable;
import capstat.model.user.User;

import java.util.LinkedList;
import java.util.List;

/**
 * @author hjorthjort
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
