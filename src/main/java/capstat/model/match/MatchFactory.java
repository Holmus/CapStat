package capstat.model.match;

import capstat.model.CapStat;
import capstat.model.user.User;

/**
 * A class with factory method for creating matches of different kinds.
 * @author hjorthjort
 */
public abstract class MatchFactory {
    public static Match createDefaultMatch() {
        Match match = new Match(7, 2);
        match.setSpectator(CapStat.getInstance().getLoggedInUser());
        return match;
    }

    public static Match createMatch(int glassesPerPlayer, int roundsToWin) {
        Match match = new Match(glassesPerPlayer*2 + 1, roundsToWin);
        match.setSpectator(CapStat.getInstance().getLoggedInUser());
        return match;
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
