package capstat.model;

import capstat.model.Match;

/**
 * @author hjorthjort
 */
public abstract class MatchFactory {
    public static Match createDefaultMatch() {
        return new Match(7, 2);
    }
}
