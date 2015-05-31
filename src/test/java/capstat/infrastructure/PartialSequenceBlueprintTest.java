package capstat.infrastructure;

import capstat.infrastructure.database.PartialSequenceBlueprint;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author hjorthjort
 */
public class PartialSequenceBlueprintTest {

    private PartialSequenceBlueprint blueprint;
    private PartialSequenceBlueprint sameBlueprint;
    private PartialSequenceBlueprint otherBlueprint1;
    private PartialSequenceBlueprint otherBlueprint2;
    private PartialSequenceBlueprint otherBlueprint3;
    private PartialSequenceBlueprint otherBlueprint4;

    @Before
    public void setUp() {
        //The blueprint we'll be comparing with
        this.blueprint = new PartialSequenceBlueprint(new boolean[] {true,
                true, true,
                true, false, false, false}, 2, true, new boolean[] {true,
                false, false, false, true, true, false, true});
        //Identical
        this.sameBlueprint = new PartialSequenceBlueprint(new boolean[] {true,
                true, true,
                true, false, false, false}, 2, true, new boolean[] {true,
                false, false, false, true, true, false, true});
        //Different glasses setup
        this.otherBlueprint1 = new PartialSequenceBlueprint(new boolean[] {true,
                true, true,
                true, true, false, false}, 2, true, new boolean[] {true,
                false, false, false, true, true, false, true});
        //Different starting player
        this.otherBlueprint2 = new PartialSequenceBlueprint(new boolean[] {true,
                true, true,
                true, false, false, false}, 1, true, new boolean[] {true,
                false, false, false, true, true, false, true});
        //Different throwBeforeWasHit
        this.otherBlueprint3 = new PartialSequenceBlueprint(new boolean[] {true,
                true, true,
                true, false, false, false}, 2, false, new boolean[] {true,
                false, false, false, true, true, false, true});
        //Different sequence
        this.otherBlueprint4 = new PartialSequenceBlueprint(new boolean[] {true,
                true, true,
                true, false, false, false}, 2, true, new boolean[] {true,
                false, false, false, true, true, false, false});
    }

    @Test
    public void testEquals() {
        assertFalse(this.blueprint.equals(null));
        assertFalse(this.blueprint.equals(new Object()));
        assertFalse(this.blueprint.equals(this.otherBlueprint1));
        assertFalse(this.blueprint.equals(this.otherBlueprint2));
        assertFalse(this.blueprint.equals(this.otherBlueprint3));
        assertFalse(this.blueprint.equals(this.otherBlueprint4));
        assertTrue(this.blueprint.equals(this.sameBlueprint));
    }
}