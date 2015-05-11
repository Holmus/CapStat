package capstat.model;

import capstat.model.Match;

/**
 * @author hjorthjort
 */
public abstract class MatchFactory {
    public static Match createDefaultMatch() {
        Match match = new Match(7, 2);
        match.setSpectator(CapStat.getInstance().getLoggedInUser());
        return match;
    }
}
