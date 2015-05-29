package capstat.model.match;

import capstat.model.CapStat;
import capstat.model.user.User;

/**
 * A class with factory method for creating matches of different kinds.
 * @author hjorthjort
 */
public abstract class MatchFactory {
    public static Match createDefaultMatch() {
        return createMatch(3,2);
    }

    public static Match createMatch(int glassesPerPlayer, int roundsToWin) {
        return createMatch(glassesPerPlayer, roundsToWin, null, null);
    }

    public static Match createMatch(int glassesPerPlayer, int roundsToWin, User
            player1, User player2) {
        Match match = new Match(glassesPerPlayer*2 + 1, roundsToWin);
        match.setPlayer1(player1);
        match.setPlayer2(player2);
        match.setSpectator(CapStat.getInstance().getLoggedInUser());
        return match;
    }
}
