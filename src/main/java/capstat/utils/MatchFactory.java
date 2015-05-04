package capstat.utils;

import capstat.model.Match;

/**
 * @author hjorthjort
 */
public class MatchFactory {
    public Match createDefaultMatch() {
        return new Match(7,2);
    }
}
