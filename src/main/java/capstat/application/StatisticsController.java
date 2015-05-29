package capstat.application;

import capstat.model.statistics.MatchResult;
import capstat.model.statistics.Plottable;
import capstat.model.statistics.ResultLedger;
import capstat.model.statistics.SingleMatchCalculator;
import capstat.model.user.User;


import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Jakob on 21/05/15.
 */
public class StatisticsController {
    public static final Statistic ACCURACY = new Accuracy();
    public static final Statistic TIME = new Time();
    public static final Statistic MATCH_COUNT = new MatchCount();

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

    /**
     * A class for creating plottables representing end times for matches.
     */
    public static class Time implements Statistic {

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

    public static class MatchCount implements Statistic {

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
}
