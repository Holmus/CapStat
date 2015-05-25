package capstat.infrastructure;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 * @author hjorthjort
 */
public class MatchResultBlueprintTest {

    private MatchResultBlueprint blueprint;
    private MatchResultBlueprint sameBlueprint;
    private MatchResultBlueprint otherBlueprint1;
    private MatchResultBlueprint otherBlueprint2;
    private MatchResultBlueprint otherBlueprint3;
    private MatchResultBlueprint otherBlueprint4;
    private MatchResultBlueprint otherBlueprint5;


    @Before
    public void setUp() {
        //The blueprint we'll be comparing with
        this.blueprint = new MatchResultBlueprint(1337, "Player 1", "Player " +
                "2", "spectator", 4, 2, 21341235143l, 123412341214l, new
                LinkedList<>());
        //Identical
        this.sameBlueprint = new MatchResultBlueprint(1337, "Player 1", "Player " +
                "2", "spectator", 4, 2, 21341235143l, 123412341214l, new
                LinkedList<>());
        //Different id
        this.otherBlueprint1 = new MatchResultBlueprint(1300, "Player 1",
                "Player " +
                "2", "spectator", 4, 2, 21341235143l, 123412341214l, new
                LinkedList<>());
        //Different Player 1
        this.otherBlueprint2 = new MatchResultBlueprint(1337, "Playaaaa 1",
                "Player " +
                "2", "spectator", 4, 2, 21341235143l, 123412341214l, new
                LinkedList<>());
        //Different Player 2
        this.otherBlueprint3 = new MatchResultBlueprint(1337, "Player 1",
                "Playaaaaa2", "spectator", 4, 2, 21341235143l, 123412341214l, new
                LinkedList<>());
        //Different spectator
        this.otherBlueprint4 = new MatchResultBlueprint(1337, "Player 1",
                "Player 1", "spectatentacoooool", 4, 2, 21341235143l,
                123412341214l, new LinkedList<>());
        //Other list
        this.otherBlueprint5 = new MatchResultBlueprint(1337, "Player 1",
                "Player " +
                "1", "spectator", 4, 2, 21341235143l, 123412341214l, new
                ArrayList<>());
    }

    @Test
    public void equalsTest() {
        assertFalse(this.blueprint.equals(null));
        assertFalse(this.blueprint.equals(new Object()));
        assertFalse(this.blueprint.equals(otherBlueprint1));
        assertFalse(this.blueprint.equals(otherBlueprint2));
        assertFalse(this.blueprint.equals(otherBlueprint3));
        assertFalse(this.blueprint.equals(otherBlueprint4));
        assertFalse(this.blueprint.equals(otherBlueprint5));
        assertTrue(this.blueprint.equals(this.sameBlueprint));
    }

}