package capstat.tests;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author hjorthjort
 */
public class RecordMatchTest {

    private Match match;
    private GameFactory gf;

    @BeforeClass
    public void createGameFactory() {
        gf = new GameFactory();
    }

    @Before
    public void newMatch( ) {
        match = gf.createDefaultMatch();
        match.startMatch();
    }

    @Test
    public void startMatchTest() {
        /*
        make sure a ranked match is created by default
         */
        assertEquals("Is ranked match: ", true, match instanceof RankedMatch);
        assertEquals("Is ranked match: ", true, match.isOngoing());
    }

    @Test
    public void duelsStartTest() {
        match.recordHit();

        assertEquals(match.isDuelling());

        match.recordMiss();
        assertEquals("Miss results in no duel: ", false, match.isDuelling());
        match.recordMiss();
        assertEquals("Miss results in no duel: ", false, match.isDuelling());
        match.recordMiss();
        assertEquals("Miss results in no duel: ", false, match.isDuelling());

        match.recordHit();
        assertEquals("Hit results in duel: ", true, match.isDuelling());
        match.recordHit();
        assertEquals("Hit results in duel: ", true, match.isDuelling());
        match.recordHit();
        assertEquals("Hit results in duel: ", true, match.isDuelling());
        match.recordHit();
        assertEquals("Hit results in duel: ", true, match.isDuelling());
        match.recordHit();
        assertEquals("Hit results in duel: ", true, match.isDuelling());

        match.recordMiss();
        assertEquals("Miss results in no duel: ", false, match.isDuelling());
    }

    @Test
    public void scoreTest() {
        match.recordHit();
        match.recordMiss();

        assertEquals("Player 1 score is 1: ", 1, match.getPlayer1Score());
        assertEquals("Player 2 score is 0: ", 0, match.getPlayer2Score());
        assertEquals("Player 2's turn: ", match.getPlayer2(), match.getPlayerNextUp());

        /*
        Player 2 turn.
        Make 10 throws, all hits. After these 10 throws, it's player 2:s turn
         again.
         */
        for (int i = 0; i < 10; i++) {
            match.recordHit();
        }

        assertEquals("Player 1 score is 1: ", 1, match.getPlayer1Score());
        assertEquals("Player 2 score is 0: ", 0, match.getPlayer2Score());

        match.recordMiss();

        assertEquals("Player 1 score is 2: ", 2, match.getPlayer1Score());
        assertEquals("Player 2 score is 0: ", 0, match.getPlayer2Score());
    }

}
