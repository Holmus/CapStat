package capstat.application;

import capstat.model.Match;
import capstat.model.MatchFactory;

/**
 * Created by Jakob on 20/05/15.
 *
 * Class to control the match in the model-layer
 *
 */
public class MatchController {
    Match match;

    /**
     * Creates a new Match using the MatchFactory.
     * @return default instance of a match, created in MatchFactory.
     */
    public static Match createNewMatch(){
        return MatchFactory.createDefaultMatch();
    }

    /**
     * Creates a new MatchController with a match.
     * @param match
     */
    public MatchController(Match match){
        this.match = match;
    }

    /**
     * Starts the match in the Model-layer
     */
    public void startMatch() {
        match.startMatch();
    }

    /**
     * Registers the latest Throw as a Miss
     */
    public void recordMiss() {
        match.recordMiss();
    }

    /**
     * Registers the latest Throw as a Hit
     */
    public void recordHit() {
        match.recordHit();
    }
}
