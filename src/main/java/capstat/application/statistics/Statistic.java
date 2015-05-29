package capstat.application.statistics;

import capstat.model.statistics.MatchResult;
import capstat.model.statistics.Plottable;
import capstat.model.user.User;

import java.util.List;

/**
 * An interface for getting plottable data points from a list of games. The
 * values of the plottables depends on the implementing class.
 * @author hjorthjort
 */
public interface Statistic {

    /**
     *
     * @param matches the matches for which plottables will be returned.
     * @param user the user for which to retrieve statistics, if applicable
     * @return a list representing each match in the input list as a plottable. The
     *         first plottable in the returned list will represent the
     *         first match in the input list, and so on.
     */
    List<Plottable> getStatistics(List<MatchResult> matches, User user);
}
