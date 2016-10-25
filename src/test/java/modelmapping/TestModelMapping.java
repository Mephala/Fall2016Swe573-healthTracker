package modelmapping;

import mockit.integration.junit4.JMockit;
import model.FoodNutritionResponse;
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

    @Test
    public void testFoodNutritionReportMapping() {
        try {
            String sampleResponse = "{\n" +
                    "    \"report\": {\n" +
                    "        \"sr\": \"September, 2016\",\n" +
                    "        \"type\": \"Basic\",\n" +
                    "        \"food\": {\n" +
                    "            \"ndbno\": \"45101889\",\n" +
                    "            \"name\": \"SWEETPEOPLE, ASSORTED MARSHMALLOWS AND GUMMIES COLORS KEBAB, UPC: 8436538780695\",\n" +
                    "            \"ds\": \"Branded Food Products\",\n" +
                    "            \"nutrients\": [\n" +
                    "                {\n" +
                    "                    \"nutrient_id\": \"208\",\n" +
                    "                    \"name\": \"Energy\",\n" +
                    "                    \"group\": \"Proximates\",\n" +
                    "                    \"unit\": \"kcal\",\n" +
                    "                    \"value\": \"333\",\n" +
                    "                    \"measures\": [\n" +
                    "                        {\n" +
                    "                            \"label\": \"g\",\n" +
                    "                            \"eqv\": 30,\n" +
                    "                            \"qty\": 30,\n" +
                    "                            \"value\": \"100\"\n" +
                    "                        }\n" +
                    "                    ]\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"nutrient_id\": \"203\",\n" +
                    "                    \"name\": \"Protein\",\n" +
                    "                    \"group\": \"Proximates\",\n" +
                    "                    \"unit\": \"g\",\n" +
                    "                    \"value\": \"6.67\",\n" +
                    "                    \"measures\": [\n" +
                    "                        {\n" +
                    "                            \"label\": \"g\",\n" +
                    "                            \"eqv\": 30,\n" +
                    "                            \"qty\": 30,\n" +
                    "                            \"value\": \"2.00\"\n" +
                    "                        }\n" +
                    "                    ]\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"nutrient_id\": \"204\",\n" +
                    "                    \"name\": \"Total lipid (fat)\",\n" +
                    "                    \"group\": \"Proximates\",\n" +
                    "                    \"unit\": \"g\",\n" +
                    "                    \"value\": \"0.00\",\n" +
                    "                    \"measures\": [\n" +
                    "                        {\n" +
                    "                            \"label\": \"g\",\n" +
                    "                            \"eqv\": 30,\n" +
                    "                            \"qty\": 30,\n" +
                    "                            \"value\": \"0.00\"\n" +
                    "                        }\n" +
                    "                    ]\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"nutrient_id\": \"205\",\n" +
                    "                    \"name\": \"Carbohydrate, by difference\",\n" +
                    "                    \"group\": \"Proximates\",\n" +
                    "                    \"unit\": \"g\",\n" +
                    "                    \"value\": \"83.33\",\n" +
                    "                    \"measures\": [\n" +
                    "                        {\n" +
                    "                            \"label\": \"g\",\n" +
                    "                            \"eqv\": 30,\n" +
                    "                            \"qty\": 30,\n" +
                    "                            \"value\": \"25.00\"\n" +
                    "                        }\n" +
                    "                    ]\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"nutrient_id\": \"291\",\n" +
                    "                    \"name\": \"Fiber, total dietary\",\n" +
                    "                    \"group\": \"Proximates\",\n" +
                    "                    \"unit\": \"g\",\n" +
                    "                    \"value\": \"0.0\",\n" +
                    "                    \"measures\": [\n" +
                    "                        {\n" +
                    "                            \"label\": \"g\",\n" +
                    "                            \"eqv\": 30,\n" +
                    "                            \"qty\": 30,\n" +
                    "                            \"value\": \"0.0\"\n" +
                    "                        }\n" +
                    "                    ]\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"nutrient_id\": \"269\",\n" +
                    "                    \"name\": \"Sugars, total\",\n" +
                    "                    \"group\": \"Proximates\",\n" +
                    "                    \"unit\": \"g\",\n" +
                    "                    \"value\": \"66.67\",\n" +
                    "                    \"measures\": [\n" +
                    "                        {\n" +
                    "                            \"label\": \"g\",\n" +
                    "                            \"eqv\": 30,\n" +
                    "                            \"qty\": 30,\n" +
                    "                            \"value\": \"20.00\"\n" +
                    "                        }\n" +
                    "                    ]\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"nutrient_id\": \"301\",\n" +
                    "                    \"name\": \"Calcium, Ca\",\n" +
                    "                    \"group\": \"Minerals\",\n" +
                    "                    \"unit\": \"mg\",\n" +
                    "                    \"value\": \"33\",\n" +
                    "                    \"measures\": [\n" +
                    "                        {\n" +
                    "                            \"label\": \"g\",\n" +
                    "                            \"eqv\": 30,\n" +
                    "                            \"qty\": 30,\n" +
                    "                            \"value\": \"10\"\n" +
                    "                        }\n" +
                    "                    ]\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"nutrient_id\": \"303\",\n" +
                    "                    \"name\": \"Iron, Fe\",\n" +
                    "                    \"group\": \"Minerals\",\n" +
                    "                    \"unit\": \"mg\",\n" +
                    "                    \"value\": \"0.00\",\n" +
                    "                    \"measures\": [\n" +
                    "                        {\n" +
                    "                            \"label\": \"g\",\n" +
                    "                            \"eqv\": 30,\n" +
                    "                            \"qty\": 30,\n" +
                    "                            \"value\": \"0.00\"\n" +
                    "                        }\n" +
                    "                    ]\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"nutrient_id\": \"307\",\n" +
                    "                    \"name\": \"Sodium, Na\",\n" +
                    "                    \"group\": \"Minerals\",\n" +
                    "                    \"unit\": \"mg\",\n" +
                    "                    \"value\": \"50\",\n" +
                    "                    \"measures\": [\n" +
                    "                        {\n" +
                    "                            \"label\": \"g\",\n" +
                    "                            \"eqv\": 30,\n" +
                    "                            \"qty\": 30,\n" +
                    "                            \"value\": \"15\"\n" +
                    "                        }\n" +
                    "                    ]\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"nutrient_id\": \"401\",\n" +
                    "                    \"name\": \"Vitamin C, total ascorbic acid\",\n" +
                    "                    \"group\": \"Vitamins\",\n" +
                    "                    \"unit\": \"mg\",\n" +
                    "                    \"value\": \"0.0\",\n" +
                    "                    \"measures\": [\n" +
                    "                        {\n" +
                    "                            \"label\": \"g\",\n" +
                    "                            \"eqv\": 30,\n" +
                    "                            \"qty\": 30,\n" +
                    "                            \"value\": \"0.0\"\n" +
                    "                        }\n" +
                    "                    ]\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"nutrient_id\": \"318\",\n" +
                    "                    \"name\": \"Vitamin A, IU\",\n" +
                    "                    \"group\": \"Vitamins\",\n" +
                    "                    \"unit\": \"IU\",\n" +
                    "                    \"value\": \"0\",\n" +
                    "                    \"measures\": [\n" +
                    "                        {\n" +
                    "                            \"label\": \"g\",\n" +
                    "                            \"eqv\": 30,\n" +
                    "                            \"qty\": 30,\n" +
                    "                            \"value\": \"0\"\n" +
                    "                        }\n" +
                    "                    ]\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"nutrient_id\": \"606\",\n" +
                    "                    \"name\": \"Fatty acids, total saturated\",\n" +
                    "                    \"group\": \"Lipids\",\n" +
                    "                    \"unit\": \"g\",\n" +
                    "                    \"value\": \"0.00\",\n" +
                    "                    \"measures\": [\n" +
                    "                        {\n" +
                    "                            \"label\": \"g\",\n" +
                    "                            \"eqv\": 30,\n" +
                    "                            \"qty\": 30,\n" +
                    "                            \"value\": \"0.00\"\n" +
                    "                        }\n" +
                    "                    ]\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"nutrient_id\": \"605\",\n" +
                    "                    \"name\": \"Fatty acids, total trans\",\n" +
                    "                    \"group\": \"Lipids\",\n" +
                    "                    \"unit\": \"g\",\n" +
                    "                    \"value\": \"0.00\",\n" +
                    "                    \"measures\": [\n" +
                    "                        {\n" +
                    "                            \"label\": \"g\",\n" +
                    "                            \"eqv\": 30,\n" +
                    "                            \"qty\": 30,\n" +
                    "                            \"value\": \"0.00\"\n" +
                    "                        }\n" +
                    "                    ]\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"nutrient_id\": \"601\",\n" +
                    "                    \"name\": \"Cholesterol\",\n" +
                    "                    \"group\": \"Lipids\",\n" +
                    "                    \"unit\": \"mg\",\n" +
                    "                    \"value\": \"0\",\n" +
                    "                    \"measures\": [\n" +
                    "                        {\n" +
                    "                            \"label\": \"g\",\n" +
                    "                            \"eqv\": 30,\n" +
                    "                            \"qty\": 30,\n" +
                    "                            \"value\": \"0\"\n" +
                    "                        }\n" +
                    "                    ]\n" +
                    "                }\n" +
                    "            ]\n" +
                    "        },\n" +
                    "        \"footnotes\": []\n" +
                    "    }\n" +
                    "}";
            ObjectMapper om = new ObjectMapper();
            FoodNutritionResponse foodNutritionResponse = om.readValue(sampleResponse, FoodNutritionResponse.class);
            assertTrue(foodNutritionResponse != null);

        } catch (Throwable t) {
            t.printStackTrace();
            fail();
        }
    }

    @Test
    public void testFoodNutritionReportToObjectMapping() {
        try {
            String resp = WebAPIUtils.queryFoodReport("45101889");
            ObjectMapper objectMapper = new ObjectMapper();
            FoodNutritionResponse foodNutritionResponse = objectMapper.readValue(resp, FoodNutritionResponse.class);
            assertTrue(foodNutritionResponse != null);
            System.out.println();
            System.out.println(foodNutritionResponse);
        } catch (Throwable t) {
            t.printStackTrace();
            fail();
        }
    }
}
