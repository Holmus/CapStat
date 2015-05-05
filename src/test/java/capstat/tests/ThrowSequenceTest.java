package capstat.tests;

import capstat.model.*;
import capstat.utils.*;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author hjorthjort
 */
public class ThrowSequenceTest {

    Match match;
    ThrowSequence sequence;

    @BeforeClass
    void initiate() {
        match = new Match(9, 3);
    }
}
