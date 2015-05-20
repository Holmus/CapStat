package capstat.application;

import capstat.model.Match;
import capstat.model.MatchFactory;

/**
 * Created by Jakob on 20/05/15.
 */
public class MatchController {
    Match match;

    public static Match createNewMatch(){
        return MatchFactory.createDefaultMatch();
    }

    public MatchController(Match match){
        this.match = match;
    }


    public void startMatch() {
        match.startMatch();
    }

    public void recordMiss() {
        match.recordMiss();
    }

    public void recordHit() {
        match.recordHit();
    }
}
