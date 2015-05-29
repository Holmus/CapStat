package capstat.model.statistics;

import capstat.model.user.User;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

/**
 * @author hjorthjort
 * A class creating plottables representing the duration of a match.
 */
public class MatchDuration implements Statistic {

    @Override
    public List<Plottable> getStatistics(final List<MatchResult> matches, final User user) {
        List<Plottable> ret = new LinkedList<>();
        for (MatchResult matchResult : matches) {
            SingleMatchCalculator calculator = new SingleMatchCalculator
                    (matchResult);
            ret.add(new Plottable() {
                @Override
                public double getValue() {
                    return calculator.getElapsedTime();
                }

                @Override
                public String getLabel() {
                    Duration duration = Duration.ofSeconds(calculator
                            .getElapsedTime());

                    return duration.toMinutes() + " min " +
                            (duration.toMillis()/1000)%60;
                }
            });
        }
        return ret;
    }
}
