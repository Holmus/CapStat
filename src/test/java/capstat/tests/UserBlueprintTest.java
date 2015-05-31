package capstat.tests;

import capstat.infrastructure.database.UserBlueprint;
import capstat.model.user.ChalmersAge;
import capstat.model.user.User;
import capstat.model.user.UserFactory;
import capstat.model.user.UserLedger;
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