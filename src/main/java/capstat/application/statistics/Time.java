package capstat.application.statistics;

import capstat.model.statistics.MatchResult;
import capstat.model.statistics.Plottable;
import capstat.model.user.User;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.LinkedList;
import java.util.List;

/**
 * A class for creating plottables representing end times for matches.
 */
public class Time implements Statistic {

    @Override
    public List<Plottable> getStatistics(final List<MatchResult> matches, final User user) {
        List<Plottable> ret = new LinkedList<>();
        for (MatchResult matchResult : matches) {
            ret.add(new Plottable() {
                @Override
                public double getValue() {
                    return matchResult.getEndTime().getEpochSecond();
                }

                @Override
                public String getLabel() {
                    ZonedDateTime dateTime = matchResult.getEndTime()
                            .atZone(ZoneId.systemDefault());
                    return dateTime.format(DateTimeFormatter
                            .ISO_LOCAL_DATE) + System.lineSeparator() +
                            dateTime.format(DateTimeFormatter
                                    .ofLocalizedTime(FormatStyle.SHORT));
                }
            });
        }
        return ret;
    }
}
