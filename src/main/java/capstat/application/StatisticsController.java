package capstat.application;

import capstat.model.statistics.*;
import capstat.model.user.User;


import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author hjorthjort
 *
 * A class for getting a selection of statistics for all matches of a user.
 *
 * This class has a number of constants, representing implementations of
 * Statistic for some common statistic. A client may also use their own
 * implementation of Statistic to obtain a list of Plottables for a set of
 * Matches. The getData method requests Plottables from the Statistic it gets
 * passed as parameter with a list of all the given user's matches, sorted by
 * their end time, from the earliest to the latest.
 *
 */
public class StatisticsController {
    public static final Statistic ACCURACY = new Accuracy();
    public static final Statistic TIME = new Time();
    public static final Statistic MATCH_COUNT = new MatchCount();
    public static final Statistic LONGEST_DUEL = new LongestDuel();
    public static final Statistic NUMBER_OF_THROWS = new NumberOfThrows();
    public static final Statistic ELAPSED_TIME = new ElapsedTime();

    public List<Plottable> getData(Statistic dataType, User user) {
        Set<MatchResult> matches = ResultLedger.getInstance()
                .getMatchesForUser(user);
        List<MatchResult> newList = new LinkedList<>(matches);
        newList.sort((match1, match2) -> match1.getEndTime().compareTo(match2
                .getEndTime()));
        return dataType.getStatistics(newList, user);
    }

}
