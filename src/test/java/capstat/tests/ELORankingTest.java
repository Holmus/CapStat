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

}
