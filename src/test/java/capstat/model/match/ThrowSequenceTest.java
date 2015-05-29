package capstat.model.match;

import capstat.model.match.Match;
import capstat.model.match.ThrowSequence;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author hjorthjort
 */
public class ThrowSequenceTest {

    Match match;
    ThrowSequence sequence;

    @Before
    public void initiate() {
        match = new Match(9, 3);
        match.startMatch();
    }

    @Test
    public void registerHitRegular() {
        match.recordHit();
        sequence = match.getThrowSequence();
        assertTrue("Last throw was hit after normal recording", sequence
                .lastThrowWasHit());
    }

    @Test
    public void registerHitManualUpdate() {
        match.recordMiss();
        sequence = match.getThrowSequence();
        sequence.updateRecordState(match.getGlasses(), Match
                .Player.TWO, true);
        assertTrue("Last throw was hit after manually updating state", sequence
                .lastThrowWasHit());
    }

    @Test
    public void registerMissRegular() {
        match.recordMiss();
        sequence = match.getThrowSequence();
        assertFalse("Last throw was miss after normal recording", sequence
                .lastThrowWasHit());
    }

    @Test
    public void registerMissManualUpdate() {
        match.recordHit();
        sequence = match.getThrowSequence();
        sequence.updateRecordState(match.getGlasses(), Match.Player.TWO, false);
        assertFalse("Last throw was miss after manually updating state",
                sequence.lastThrowWasHit());
    }

    @Test
    public void canRewind() {
        assertFalse("New match can't be rewound", match.getThrowSequence()
                .canRewind());
        match.recordMiss();
        assertTrue("Match with one throw can be rewound", match
                .getThrowSequence().canRewind());
        match.manuallyChangeGameState(match.getGlasses(), Match.Player.ONE,
                false);
        assertFalse("Match with newly updated state can't be rewound", match
                .getThrowSequence().canRewind());
        match.recordMiss();
        sequence = match.getThrowSequence();
        assertTrue("Match with one throw can be rewound", match
                .getThrowSequence().canRewind());
        sequence.rewind();
        assertFalse("Match rewound to end of current can't be rewound more",
                sequence.canRewind());

    }

    @Test
    public void canForward() {
        assertFalse("New match can be forwarded", match.getThrowSequence()
                .canForward());
        match.recordMiss();
        assertFalse("Match with hits but unrewound can't be forwarded", match
                .getThrowSequence().canForward());
        sequence = match.getThrowSequence();
        sequence.rewind();
        assertTrue("Match rewound one step can be forwarded", sequence
                .canForward());
        sequence.forward();
        assertFalse("Match forwarded to end of forward possibilities can't be" +
                " forwarded", sequence.canForward());
    }
}
