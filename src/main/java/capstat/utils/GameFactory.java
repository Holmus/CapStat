package capstat.utils;

import capstat.model.Match;
import capstat.model.RankedMatch;

/**
 * @author hjorthjort
 */
public class GameFactory {
    public Match createDefaultMatch() {
        return new RankedMatch();
    }
}
