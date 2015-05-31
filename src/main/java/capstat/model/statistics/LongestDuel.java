package capstat.model.statistics;

import capstat.model.user.User;

import java.util.LinkedList;
import java.util.List;

/**
 * @author hjorthjort
 */
public class LongestDuel implements Statistic {

    @Override
    public List<Plottable> getStatistics(final List<MatchResult> matches, final User user) {
        List<Plottable> ret = new LinkedList<>();
        for (MatchResult matchResult : matches) {
            SingleMatchCalculator calc = new SingleMatchCalculator(matchResult);
            ret.add(new Plottable() {
                @Override
                public double getValue() {
                    return calc.getLongestDuelLength();
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
