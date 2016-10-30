package api;

import mockit.Deencapsulation;
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

    @Test
    public void testFoodReportQueryApiCall() {
        try {
            String resp = WebAPIUtils.queryFoodReport("45101889");
            assertTrue(CommonUtils.notEmpty(resp));
            System.out.println();
            System.out.println(resp);
        } catch (Throwable t) {
            t.printStackTrace();
            fail();
        }
    }

    @Test
    public void testApiWaiting() {
        try {
            long start = System.currentTimeMillis();
            for (int i = 0; i < 6; i++) {
                Deencapsulation.invoke(WebAPIUtils.class, "waitApiLimit");
            }
            long end = System.currentTimeMillis();
            assertTrue(end - start > 15000L); // Requests must be regulated such as no concurrent requests can be executed in 3.6 seconds.
        } catch (Throwable t) {
            t.printStackTrace();
            fail();
        }
    }

    @Test
    public void testFoodNumberCounting() {
        try {
            int foodNumberInUSDb = WebAPIUtils.getFoodNum();
            System.out.println();
            System.out.println(foodNumberInUSDb);
        } catch (Throwable t) {
            t.printStackTrace();
            fail();
        }
    }
}
