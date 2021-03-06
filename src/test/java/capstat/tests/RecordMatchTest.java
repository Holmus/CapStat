package capstat.tests;

import capstat.infrastructure.eventbus.NotifyEventListener;
import capstat.model.match.Match;
import capstat.model.match.MatchFactory;
import capstat.model.user.User;
import capstat.model.user.UserFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author hjorthjort
 */
public class RecordMatchTest implements NotifyEventListener {

    private Match match;
    private boolean gameIsOver;

    @Before
    public void newMatch( ) {
        match = MatchFactory.createDefaultMatch();
        User dummy1 = UserFactory.createDummyUser1();
        User dummy2 = UserFactory.createDummyUser2();
        match.setPlayer1(dummy1);
        match.setPlayer2(dummy2);
        match.startMatch();
        gameIsOver = false;
    }

    @Test
    public void startMatchTest() {
        /*
        make sure a ranked match is created by default
         */
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
        assertEquals("Player 2's turn: ", Match.Player.TWO, match
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
        match.addNotificationEventListener(Match.MATCH_ENDED, this);
        /*
        Make player 2 win as fast as possible;
         */
        for (int i = 0; i < 1000 && match.isOngoing(); i++) {
            match.recordMiss();
            match.recordHit();
        }
        assertFalse("Match is over after 1 thousand duels: ", match.isOngoing());
        try {
            assertEquals("Player 2 is winner:", Match.Player.TWO, match
                    .getWinner());
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

    @Override
    public void notifyEvent(final String event) {
        matchOver();
    }
}
