package capstat.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.security.*;

public abstract class Security {

    public static String hashPassword(String password) {
        return DigestUtils.sha256Hex(password);
    }
}
