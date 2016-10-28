package util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Mephalay on 10/4/2016.
 */
public class SecurityUtils {
    //TODO secret key is hardcoded
    private static final String SECRET_KEY = "Ssajkf1!!!_1291247qasjk!!!sdamKAKKM(&*(Q#OL:!!!:_)__AS_)!NJFNKJA@)(@F()_KASP)AF@J*@)*(!(F";
    private static Logger logger = Logger.getLogger(SecurityUtils.class);


    public static String generateHashWithHMACSHA256(String stringToBeHashed) {
        try {
            Mac mac = Mac.getInstance("HMACSHA256");
            mac.init(new SecretKeySpec(stringToBeHashed.getBytes("UTF-8"), ""));
            byte[] ret = mac.doFinal(SECRET_KEY.getBytes("UTF-8"));
            String hashed = Hex.encodeHexString(ret);
            return hashed;
        } catch (Throwable t) {
            logger.error("Failed to generate hash key", t);
            return null;
        }
    }

    /**
     * Returns a string array, 1. element is username, 2. element is password. Returns null if encounters an exception and logs exception.
     *
     * @param header
     * @return
     */
    public static String[] decodeBasicAuthHeaders(String header) {
        try {
            String basicAuth = header;
            String[] tokens = basicAuth.split(" ");
            String key = tokens[1];
            byte[] decoded = Base64.decodeBase64(key.getBytes("UTF-8"));
            String decodedString = new String(decoded, "UTF-8");
            String[] retval = decodedString.split(":");
            return retval;
        } catch (Throwable t) {
            logger.error("Failed deconstructing basic auth header", t);
            return null;
        }
    }

    public static String tokenizeUsernamePassword(String username, String pw) {
        try {
            String duple = username + ":" + pw;
            String totalString = "Basic " + duple;
            byte[] encoded = Base64.encodeBase64(totalString.getBytes("UTF-8"));
            return new String(encoded, "UTF-8");
        } catch (Throwable t) {
            logger.error("Failed to create token", t);
            return null;
        }

    }
}
