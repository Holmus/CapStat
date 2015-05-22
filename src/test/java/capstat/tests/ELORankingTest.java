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

}
