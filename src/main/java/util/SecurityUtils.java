package util;

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
}
