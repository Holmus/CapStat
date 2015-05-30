package capstat.application;

import capstat.model.statistics.*;
import capstat.model.user.User;


import java.util.*;
import java.util.function.Predicate;
import java.util.logging.Filter;
import java.util.stream.Stream;

/**
 * @author hjorthjort
 *
 * A class for getting a selection of statistics for all matches of a user.
 *
 * This class has a number of constants, representing implementations of
 * Statistic for some common statistic. A client may also use their own
 * implementation of Statistic to obtain a list of Plottables for a set of
 * Matches.
 *
 */
public class StatisticsController {
    public static final Statistic ACCURACY = new Accuracy();
    public static final Statistic TIME = new Time();
    public static final Statistic MATCH_COUNT = new MatchCount();
    public static final Statistic LONGEST_DUEL = new LongestDuel();
    public static final Statistic NUMBER_OF_THROWS = new NumberOfThrows();
    public static final Statistic ELAPSED_TIME = new MatchDuration();

    /**
     * This method uses the full getData method, sorting the match
     * results by their end time and filtering them with a predicate
     * that is always true, which means the full unfiltered list of a users
     * matches is used.
     */
    public List<Plottable> getData(Statistic dataType, User user) {
        Comparator<MatchResult> comparator = (m1, m2) -> m1.getEndTime()
                .compareTo(m2.getEndTime());
        return getData(dataType, user, comparator);
    }

    /**
     * This method uses the full getData method with a predicate that is
     * always true, which means the full unfiltered list of a users matches
     * is returned.
     */
    public List<Plottable> getData(Statistic dataType, User user,
                                   Comparator<? super MatchResult>
                                           sortingComparator) {
        return getData(dataType, user, sortingComparator, (a) -> true);
    }

    /**
     * This method requests Plottables from the Statistic it gets passed as a
     * parameter with a list of all the given users matches, sorted by the
     * order defined by the comparator passed as argument and filterd using
     * the predicate passed as argument
     * @param dataType the statstic used for creating plottables
     * @param user the user for which to fetch statistics
     * @param sortingComparator comparator determining the order of the List
     *                          that is passed to the Statistic.
     * @param filter a predicate by which to filter the set of matches to be
     *               used.
     * @return a list of plottables as specified by the concrete
     * implementation of dataType.
     */
    public List<Plottable> getData(Statistic dataType, User user,
                                   Comparator<? super MatchResult>
                                           sortingComparator,
                                   Predicate<? super MatchResult> filter) {
        Set<MatchResult> matches = ResultLedger.getInstance()
                .getMatchesForUser(user);
        Stream<MatchResult> stream = matches.stream().filter(filter).sorted
                (sortingComparator);
        List<MatchResult> newList = new LinkedList<>();
        Iterator<MatchResult> iterator = stream.iterator();
        while (iterator.hasNext()) {
            newList.add(iterator.next());
        }
        return dataType.getStatistics(newList, user);
    }
}
