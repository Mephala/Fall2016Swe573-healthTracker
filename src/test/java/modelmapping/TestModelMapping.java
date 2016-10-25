package modelmapping;

import mockit.integration.junit4.JMockit;
import model.FoodQueryResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import util.WebAPIUtils;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by Mephalay on 10/25/2016.
 */
@RunWith(JMockit.class)
public class TestModelMapping {

    @Test
    public void testFoodSearchListModelMapping() {
        try {
            String sampleResponse = "{\n" +
                    "    \"list\": {\n" +
                    "        \"q\": \"butter\",\n" +
                    "        \"sr\": \"28\",\n" +
                    "        \"ds\": \"any\",\n" +
                    "        \"start\": 0,\n" +
                    "        \"end\": 5,\n" +
                    "        \"total\": 2167,\n" +
                    "        \"group\": \"\",\n" +
                    "        \"sort\": \"n\",\n" +
                    "        \"item\": [\n" +
                    "            {\n" +
                    "                \"offset\": 0,\n" +
                    "                \"group\": \"Branded Food Products Database\",\n" +
                    "                \"name\": \"ABC, CREAMY PEANUT BUTTER, UPC: 837991211289\",\n" +
                    "                \"ndbno\": \"45049278\",\n" +
                    "                \"ds\": \"BL\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"offset\": 1,\n" +
                    "                \"group\": \"Branded Food Products Database\",\n" +
                    "                \"name\": \"AC2 Butter Lovers, UNPREPARED, GTIN: 00076150090208\",\n" +
                    "                \"ndbno\": \"45130519\",\n" +
                    "                \"ds\": \"BL\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"offset\": 2,\n" +
                    "                \"group\": \"Branded Food Products Database\",\n" +
                    "                \"name\": \"AC2 Butter Lovers, UNPREPARED, GTIN: 00076150011159\",\n" +
                    "                \"ndbno\": \"45130738\",\n" +
                    "                \"ds\": \"BL\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"offset\": 3,\n" +
                    "                \"group\": \"Branded Food Products Database\",\n" +
                    "                \"name\": \"AC2 Butter Lovers, UNPREPARED, GTIN: 00076150232585\",\n" +
                    "                \"ndbno\": \"45130770\",\n" +
                    "                \"ds\": \"BL\"\n" +
                    "            },\n" +
                    "            {\n" +
                    "                \"offset\": 4,\n" +
                    "                \"group\": \"Branded Food Products Database\",\n" +
                    "                \"name\": \"ACT 2 94FF Butter, UNPREPARED, GTIN: 00076150232028\",\n" +
                    "                \"ndbno\": \"45135014\",\n" +
                    "                \"ds\": \"BL\"\n" +
                    "            }\n" +
                    "        ]\n" +
                    "    }\n" +
                    "}";
            ObjectMapper om = new ObjectMapper();
            FoodQueryResponse fqr = om.readValue(sampleResponse, FoodQueryResponse.class);
            System.out.println();
            System.out.println(fqr);
        } catch (Throwable t) {
            t.printStackTrace();
            fail();
        }
    }

    @Test
    public void testMappingAPIResponseToObject() {
        try {
            String resp = WebAPIUtils.queryFood("kebab");
            ObjectMapper om = new ObjectMapper();
            FoodQueryResponse foodQueryResponse = om.readValue(resp, FoodQueryResponse.class);
            assertTrue(foodQueryResponse != null);
            System.out.println();
            System.out.println(foodQueryResponse);
        } catch (Throwable t) {
            t.printStackTrace();
            fail();
        }
    }
}
