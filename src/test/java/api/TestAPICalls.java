package api;

import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;
import util.CommonUtils;
import util.WebAPIUtils;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by Mephalay on 10/25/2016.
 */
@RunWith(JMockit.class)
public class TestAPICalls {

    @Test
    public void testFoodQueryApiCall() {
        try {
            String resp = WebAPIUtils.queryFood("kebab");
            assertTrue(CommonUtils.notEmpty(resp));
            System.out.println();
            System.out.println(resp);
        } catch (Throwable t) {
            t.printStackTrace();
            fail();
        }
    }
}
