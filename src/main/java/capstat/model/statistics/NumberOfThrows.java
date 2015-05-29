package capstat.model.statistics;

import capstat.model.user.User;

import java.util.LinkedList;
import java.util.List;

/**
 * @author hjorthjort
 */
public class NumberOfThrows implements Statistic {
    @Override
    public List<Plottable> getStatistics(final List<MatchResult> matches, final User user) {
        List<Plottable> ret = new LinkedList<>();
        for (MatchResult result : matches) {
            SingleMatchCalculator calc = new SingleMatchCalculator(result);
            ret.add(new Plottable() {
                @Override
                public double getValue() {
                    return calc.getTotalNumberOfThrows(user);
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
