package capstat.model.statistics;

import capstat.infrastructure.database.MatchResultBlueprint;
import capstat.infrastructure.database.PartialSequenceBlueprint;
import capstat.model.match.Match;
import capstat.model.user.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Year;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Jakob
 * Created by Jakob on 31/05/15.
 */
public class MatchResultTest {
    MatchResult mr;
    boolean[] matchSequence;
    UserLedger ul = UserLedger.getInstance();
    List<PartialSequenceBlueprint> list;
    List<PartialSequenceResult> list1;
    @Before
    public void start() {
        String nickname = "DummyOne";
        String name = "Dummy Player 1";
        String password = "foobar";
        String hashedPassword = Security.hashPassword(password);

        LocalDate birthday = LocalDate.of(1992, 1, 13);
        Admittance admittance = new Admittance(Year.of(2013), Admittance
                .Period.ONE);
        ChalmersAge chalmersAge = new ChalmersAge(birthday, admittance);

        ELORanking ranking = ELORanking.defaultRanking();

        ul.registerNewUser(nickname, name, password, birthday, Year.of(2013), 1);
        ul.registerNewUser("DummyTwo", name, password, birthday, Year.of(2014), 1);
        boolean[] glassState = new boolean[] {
                true, true, true, true, true, true, true
        };
        matchSequence = new boolean[] {
                true, true, true, true, true, false, false, true, false, false, true, false, false, true, false, false,
                true, false, false, true, false, false, true, false, false, true, false
        };
        int startingPlayer = 1;
        PartialSequenceBlueprint psbp = new PartialSequenceBlueprint(glassState, startingPlayer, false, matchSequence);
        PartialSequenceResult psbp1 = new PartialSequenceResult(psbp);
        list = new LinkedList<PartialSequenceBlueprint>();
        list.add(psbp);
        list1 = new LinkedList<PartialSequenceResult>();
        list1.add(psbp1);
        MatchResultBlueprint testResult = new MatchResultBlueprint(128128093l, "DummyOne", "DummyTwo", "DummyThree", 2, 0, 1000l,
                2000l, list);
        mr = new MatchResult(testResult);
    }
    @After
    public void end(){
        ul.removeUserFromLedger(UserLedger.getInstance().getUserByNickname("DummyOne"));
        ul.removeUserFromLedger(UserLedger.getInstance().getUserByNickname("DummyTwo"));
    }

    @Test
    public void testGetId() throws Exception {
        assertTrue(mr.getId() == 128128093l);
        assertFalse(mr.getId() == 1281212093l);
    }

    @Test
    public void testGetPlayer1() throws Exception {
        assertTrue(mr.getPlayer1().getNickname().equals("DummyOne"));
    }

    @Test
    public void testGetPlayer2() throws Exception {
        assertTrue(mr.getPlayer2().getNickname().equals("DummyTwo"));
    }

    @Test
    public void testGetPlayer1score() throws Exception {
        assertTrue(mr.getPlayer1score() == 2);
    }

    @Test
    public void testGetPlayer2score() throws Exception {
        assertTrue(mr.getPlayer2score() == 0);
    }

    @Test
    public void testGetPlayer() throws Exception {
        assertTrue(mr.getPlayer(Match.Player.ONE).getNickname().equals("DummyOne"));
        assertTrue(mr.getPlayer(Match.Player.TWO).getNickname().equals("DummyTwo"));

    }

    @Test
    public void testGetSpectator() throws Exception {
        assertTrue(mr.getSpectator().getNickname().equals("DummyThree"));
    }

    @Test
    public void testGetPlayerScore() throws Exception {
        assertTrue(mr.getPlayerScore(Match.Player.ONE) == 2);
    }

    @Test
    public void testGetStartTime() throws Exception {
        assertTrue(mr.getStartTime().equals(Instant.ofEpochSecond(1000)));
    }

    @Test
    public void testGetEndTime() throws Exception {
        assertTrue(mr.getStartTime().toString(), mr.getEndTime().equals(Instant.ofEpochSecond(2000)));
    }

    @Test
    public void testGetSequences() throws Exception {
        for(int i=0; i<list1.size(); i++){
            assertTrue(mr.getSequences().get(i).equals(list1.get(i)));
        }

    }
}