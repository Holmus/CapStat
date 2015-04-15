package capstat.tests;

import capstat.model.CapStat;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UserLedgerTest {

    @Test
    public void testNicknameValidation() {
        CapStat cs = CapStat.getInstance();
        String[] validNicknames = new String[] {"Saser",
                                                "Holmus",
                                                "HJORT",
                                                "Kruken",
                                                "bäski",
                                                "Tandlös",
                                                "Öhrnie",
                                                "Jühan",
                                                "Lam(m)",
                                                "Big Tuna",
                                                "_us"};
        for (String validNickname : validNicknames) {
            assertEquals("Testing valid nickname: " + validNickname,
                         true,
                         cs.isNicknameValid(validNickname));
        }

        String[] invalidNicknames = new String[] { "Saser\nSasybooi", "\n", "\\asdf", "''" };
        for (String invalidNickname : invalidNicknames) {
            assertEquals("Testing invalid nickname: " + invalidNickname, false, cs.isNicknameValid(invalidNickname));
        }
    }
}
