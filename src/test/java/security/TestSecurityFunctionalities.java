package security;

import mockit.integration.junit4.JMockit;
import org.apache.commons.codec.binary.Hex;
import org.junit.Test;
import org.junit.runner.RunWith;
import util.SecurityUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by Mephalay on 10/2/2016.
 */
@RunWith(JMockit.class)
public class TestSecurityFunctionalities {

    @Test
    public void testHashingPwWothPBKDF2() {
        try {
            String storedPrivateString = "A string"; //test 2
            String password = "VerySecret";
            String hashValue = getHashValue(storedPrivateString, password);
            System.out.println();
            System.out.println(hashValue);
            String hashValue2 = getHashValue(storedPrivateString, password);
            assertTrue(hashValue2.equals(hashValue));
            password = "VerySecret ";
            String hashValue3 = getHashValue(storedPrivateString, password);
            System.out.println(hashValue2);
            System.out.println(hashValue);
            System.out.println(hashValue3);
        } catch (Throwable t) {
            t.printStackTrace();
            fail();
        }
    }

    @Test
    public void testHashingPasswordWithSecurityUtils() {
        try {
            String pw = "gokhanTest";
            String hashed = SecurityUtils.generateHashWithHMACSHA256(pw);
            System.out.println();
            System.out.println(hashed);
        } catch (Throwable t) {
            t.printStackTrace();
            fail();
        }
    }

    private String getHashValue(String storedPrivateString, String password) throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        Mac mac = Mac.getInstance("HMACSHA256");
        mac.init(new SecretKeySpec(password.getBytes("UTF-8"), ""));
        byte[] ret = mac.doFinal(storedPrivateString.getBytes("UTF-8"));
        String hashed = Hex.encodeHexString(ret);
        return hashed;
    }


    @Test
    public void testBase64Decoding() {
        try {
            String basicAuth = "Basic Z29raGFuOnRlc3Q=";
            String[] credentials = SecurityUtils.decodeBasicAuthHeaders(basicAuth);
            System.out.println();
            System.out.println("Username:" + credentials[0]);
            System.out.println("Password:" + credentials[1]);
        } catch (Throwable t) {
            t.printStackTrace();
            fail();
        }
    }


}
