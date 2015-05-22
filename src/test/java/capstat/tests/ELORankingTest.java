package capstat.tests;

import capstat.model.ELORanking;
import capstat.model.User;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author hjorthjort
 */
public class ELORankingTest {

    @Test
    public void constructionTest() {
        ELORanking ranking = new ELORanking(1000);
        assertTrue(ranking.getPoints() == 1000.00);
    }

    @Test(expected = IllegalArgumentException.class)
    public void constructionWithExceptionTest() {
        ELORanking ranking = new ELORanking(-1000);
    }

    @Test
    public void equalsTest() {
        ELORanking ranking = new ELORanking(1000);
        assertFalse("Compared to null", ranking.equals(null));
        assertFalse("Compared to object", ranking.equals(new Object()));
        assertFalse("Compared to different ELORanking", ranking.equals(new
                ELORanking(1500)));
        assertFalse("Compared to subclass", ranking.equals(new ELORanking
                (1000) {
            //Subclass
        }));

        assertTrue("Compared to itself", ranking.equals(ranking));
        assertTrue("Compared to new object, same ranking", ranking.equals(new
                ELORanking(1000)));
        assertTrue("Compared to new object, similar ranking", ranking.equals
                (new ELORanking(1000.000999)));
        assertTrue("Compared to new object, similar ranking", ranking.equals
                (new ELORanking(999.99500001)));
    }

    @Test
    public void hashCodeTest() {
        ELORanking ranking = new ELORanking(1000);
        assertTrue("Same ranking", ranking.hashCode() == new ELORanking(1000)
                .hashCode());
        assertTrue("Very similar ranking", ranking.hashCode() == new
                ELORanking(1000.0001392).hashCode());
        assertTrue("Very similar ranking", ranking.hashCode() == new
                ELORanking(999.9951392).hashCode());

        assertFalse("Different ranking", ranking.hashCode() == new ELORanking
                (999).hashCode());
        assertFalse("Different ranking", ranking.hashCode() == new ELORanking
                (1001).hashCode());
    }

    @Test
    public void calculateNewRankingTest() {
        ELORanking player1Ranking = new ELORanking(1613);
        ELORanking player2Ranking = new ELORanking(1450);
        double[] newScores = ELORanking.calculateNewRanking(player1Ranking,
                player2Ranking, 2, 1);
        assertTrue("Array is the right length", newScores.length == 2);
        //Calculation used: http://www.wolframalpha.com/input/?i=1613+%2B+32*%282+-+3%2F%281%2B+10%5E%28%281450-1613%29%2F400%29%29+
        assertTrue("Expected score: " + 1608.00 + "\nResult: "+
                newScores[1], newScores[0] == 1608.00);
        //Calculation used: http://www.wolframalpha.com/input/?i=1450+%2B+32*%281+-+3%2F%281%2B+10%5E%28%281613-1450%29%2F400%29%29+
        assertTrue(newScores[1] == 1455.00);

        //New scores, with very surprising outcome
        newScores = ELORanking.calculateNewRanking(player2Ranking,
                player1Ranking, 5, 0);
        //Calculation used: http://www.wolframalpha.com/input/?i=1450+%2B+32*%285+-+5%2F%281%2B+10%5E%28%281613-1450%29%2F400%29%29+
        assertTrue(newScores.length == 2);
        assertTrue(newScores[0] == 1565.00);
        //Calculation used: http://www.wolframalpha.com/input/?i=1613+%2B+32*%280+-+5%2F%281%2B+10%5E%28%281450-1613%29%2F400%29%29+
        assertTrue("Expected score: " + 1498.00 + "\nResult: "+
                newScores[1], newScores[1] == 1498.00);
    }
}
