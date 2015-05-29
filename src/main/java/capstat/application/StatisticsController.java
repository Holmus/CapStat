package capstat.application;

import capstat.application.statistics.Accuracy;
import capstat.application.statistics.MatchCount;
import capstat.application.statistics.Statistic;
import capstat.application.statistics.Time;
import capstat.model.statistics.MatchResult;
import capstat.model.statistics.Plottable;
import capstat.model.statistics.ResultLedger;
import capstat.model.user.User;


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

}
