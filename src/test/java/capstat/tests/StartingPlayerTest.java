package capstat.tests;

import capstat.model.Admittance;
import capstat.model.Birthday;
import capstat.model.ChalmersAge;
import capstat.model.ELORanking;
import capstat.model.Match;
import capstat.model.User;
import capstat.utils.Security;
import capstat.utils.GameFactory;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Christian Persson
 */
public class StartingPlayerTest {

    private Match match;
    private User p1, p2;
    private ChalmersAge age1, age2;
    private Birthday bd1, bd2;
    private Admittance adm1, adm2;
    private ELORanking ranking = ELORanking.defaultRanking();
    private GameFactory gf = new GameFactory();

    @Before
    public void newMatch() {
        this.match = gf.createDefaultMatch();
    }

   @Test
    public void sameAdmittance() {
        this.bd1 = new Birthday(1995, 6, 2);
        this.adm1 = new Admittance(2014, 1);
        this.age1 = new ChalmersAge(this.bd1, this.adm1);
        this.p1 = new User("P1", "Player 1", Security.hashPassword("asdfasdf"), this.age1, this.ranking);

        this.bd2 = new Birthday(1995, 5, 18);
        this.adm2 = new Admittance(2014, 1);
        this.age2 = new ChalmersAge(this.bd2, this.adm2);
        this.p2 = new User("P2", "Player 2", Security.hashPassword("fdsafdsa"), this.age2, this.ranking);

        this.match.setPlayer1(this.p1);
        this.match.setPlayer2(this.p2);
        // Player 1 should be the starting player, since he is younger.
        assertEquals("Same reading period, different birthdays", this.match.getStartingPlayer(), this.p1);
    }

    @Test
    public void sameBirthday() {
        this.bd1 = new Birthday(1995, 6, 2);
        this.adm1 = new Admittance(2014, 1);
        this.age1 = new ChalmersAge(this.bd1, this.adm1);
        this.p1 = new User("P1", "Player 1", Security.hashPassword("asdfasdf"), this.age1, this.ranking);

        this.bd2 = new Birthday(1995, 6, 2);
        this.adm2 = new Admittance(2013, 1);
        this.age2 = new ChalmersAge(this.bd2, this.adm2);
        this.p2 = new User("P2", "Player 2", Security.hashPassword("fdsafdsa"), this.age2, this.ranking);

        this.match.setPlayer1(this.p1);
        this.match.setPlayer2(this.p2);
        // The players have the same birthday, but player 1 should be the
        // starting player, since he was admitted most recently.
        assertEquals("Same birthday, different admittances", this.match.getStartingPlayer(), this.p1);
    }

    @Test
    public void youngerAndEarlierAdmittance() {
        this.bd1 = new Birthday(1995, 6, 2);
        this.adm1 = new Admittance(2012, 4);
        this.age1 = new ChalmersAge(this.bd1, this.adm1);
        this.p1 = new User("P1", "Player 1", Security.hashPassword("asdfasdf"), this.age1, this.ranking);

        this.bd2 = new Birthday(1992, 8, 12);
        this.adm2 = new Admittance(2013, 1);
        this.age2 = new ChalmersAge(this.bd2, this.adm2);
        this.p2 = new User("P2", "Player 2", Security.hashPassword("fdsafdsa"), this.age2, this.ranking);

        this.match.setPlayer1(this.p1);
        this.match.setPlayer2(this.p2);
        // Player 1 is younger and should therefore be the starting player, even
        // though he was admitted to Chalmers earlier.
        assertEquals("Same birthday, different admittances", this.match.getStartingPlayer(), this.p1);
    }

    @Test
    public void youngerAndLaterAdmittance() {
        this.bd1 = new Birthday(1995, 6, 2);
        this.adm1 = new Admittance(2014, 1);
        this.age1 = new ChalmersAge(this.bd1, this.adm1);
        this.p1 = new User("P1", "Player 1", Security.hashPassword("asdfasdf"), this.age1, this.ranking);

        this.bd2 = new Birthday(1992, 8, 12);
        this.adm2 = new Admittance(2013, 1);
        this.age2 = new ChalmersAge(this.bd2, this.adm2);
        this.p2 = new User("P2", "Player 2", Security.hashPassword("fdsafdsa"), this.age2, this.ranking);

        this.match.setPlayer1(this.p1);
        this.match.setPlayer2(this.p2);
        // Player 1 is younger and should therefore be the starting player, and
        // since he was also admitted more recently, he should definitely be the
        // starting player.
        assertEquals("Same birthday, different admittances", this.match.getStartingPlayer(), this.p1);
    }

    @Test
    public void sameBirthdayAndAdmittance() {
        this.bd1 = new Birthday(1995, 6, 2);
        this.adm1 = new Admittance(2014, 1);
        this.age1 = new ChalmersAge(this.bd1, this.adm1);
        this.p1 = new User("P1", "Player 1", Security.hashPassword("asdfasdf"), this.age1, this.ranking);

        this.bd2 = new Birthday(1995, 6, 2);
        this.adm2 = new Admittance(2014, 1);
        this.age2 = new ChalmersAge(this.bd2, this.adm2);
        this.p2 = new User("P2", "Player 2", Security.hashPassword("fdsafdsa"), this.age2, this.ranking);

        this.match.setPlayer1(this.p1);
        this.match.setPlayer2(this.p2);
        // The players have the same birthday and admittance, and therefore
        // player 1 should be the starting player, as it's the default.
        assertEquals("Same birthday, different admittances", this.match.getStartingPlayer(), this.p1);
    }

}
