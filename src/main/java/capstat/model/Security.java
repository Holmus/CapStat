package capstat.model;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * A service for getting hashed versions of passwords
 */

public abstract class Security {

    public static String hashPassword(String password) {
        return DigestUtils.sha256Hex(password);
    }
}
