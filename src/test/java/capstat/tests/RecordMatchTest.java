package capstat.tests;

import capstat.model.MatchOverObserver;
import capstat.model.Match;
import capstat.model.RankedMatch;
import capstat.utils.GameFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author hjorthjort
 */
public class RecordMatchTest implements MatchOverObserver {

    private Match match;
    private GameFactory gf = new GameFactory();
    private boolean gameIsOver;

    @Before
    public void newMatch( ) {
        match = gf.createDefaultMatch();
        match.startMatch();
        gameIsOver = false;
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

        assertEquals("Make sure duel started: ", true, match.isDuelling());

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
        assertEquals("Player 2's turn: ", match.getPlayer2(), match
                .getPlayerWhoseTurnItIs());

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

    @Test
    public void winGameTest() {
        match.addMatchOverObserver(this);
        /*
        Make player 2 win as fast as possible;
         */
        for (int i = 0; i < 1000 && match.isOngoing(); i++) {
            match.recordMiss();
            match.recordHit();
        }
        assertFalse("Match is over after 1 thousand duels: ", match.isOngoing
                ());
        try {
            assertEquals("Player 2 is winner: ", match.getPlayer2(), match.getWinner
                    ());
        } catch (Match.MatchNotOverException e) {
            System.out.println("Match should have been over, but " +
                    "MatchNotOverException was thrown when getWinner was " +
                    "called");
        }
        assertEquals("Game over method was called: ", true, gameIsOver);
    }

    public void matchOver() {
        gameIsOver = true;
    }

}
