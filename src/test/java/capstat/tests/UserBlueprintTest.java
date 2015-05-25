package capstat.tests;

import capstat.infrastructure.UserBlueprint;
import capstat.model.ChalmersAge;
import capstat.model.User;
import capstat.model.UserFactory;
import capstat.model.UserLedger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * @author hjorthjort
 */
public class UserBlueprintTest {

    UserBlueprint blueprint1;
    UserBlueprint blueprint2;

    @Before
    public void init() {
        User u = UserFactory.createDummyUser1();
        ChalmersAge age = u.getChalmersAge();
        blueprint1 = UserLedger.getInstance().createBlueprint(u);
        u = UserFactory.createDummyUser2();
        blueprint2 = UserLedger.getInstance().createBlueprint(u);

    }

    @Test
    public void equalsTest() throws Exception {
        assertFalse(this.blueprint1.equals(null));
        assertFalse(this.blueprint1.equals(new Object()));
        assertFalse(this.blueprint1.equals(blueprint2));
    }
}