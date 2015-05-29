package capstat.application.statistics;

import capstat.model.statistics.MatchResult;
import capstat.model.statistics.Plottable;
import capstat.model.user.User;

import java.util.List;

/**
 * @author hjorthjort
 */
public interface Statistic {
    List<Plottable> getStatistics(List<MatchResult> matches, User user);
}
