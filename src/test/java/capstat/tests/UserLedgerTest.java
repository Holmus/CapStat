package capstat.tests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import capstat.model.LoggedInUser;

public class UserLedgerTest {

    @Test
    public void testNicknameValidation() {
        LoggedInUser cs = LoggedInUser.getInstance();
        String[] validNicknames = new String[] {"Saser2",
                                                "Holmus2",
                                                "HJORT2",
                                                "Kruken2",
                                                "bäski2",
                                                "Tandlös2",
                                                "Öhrnie2",
                                                "Jühan2",
                                                "Lam(m)2",
                                                "Big Tuna2",
                                                "_us2"};
        for (String validNickname : validNicknames) {
            assertEquals("Testing valid nickname: " + validNickname,
                         true,
                         cs.isNicknameValid(validNickname));
        }

        String[] invalidNicknames = new String[] { "", "Saser\nSasybooi", "\n", "\\asdf", "''" };
        for (String invalidNickname : invalidNicknames) {
            assertEquals("Testing invalid nickname: " + invalidNickname, false, cs.isNicknameValid(invalidNickname));
        }
    }
}
