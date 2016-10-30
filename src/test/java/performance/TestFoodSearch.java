package performance;

import manager.FoodReportCardManager;
import mockit.integration.junit4.JMockit;
import model.FoodQueryResponse;
import model.FoodResponseItem;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import persistance.USFoodInfoCard;
import util.WebAPIUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by Mephalay on 10/29/2016.
 */
@RunWith(JMockit.class)
public class TestFoodSearch {

    @Test
    public void testIndexingFoodNames() {
        try {
            final String searchKeyword = "Apple";
            String apiResponse = WebAPIUtils.queryFood(searchKeyword);
            ObjectMapper om = new ObjectMapper();
            FoodQueryResponse foodQueryResp = om.readValue(apiResponse, FoodQueryResponse.class);
            assertTrue(foodQueryResp != null);
            List<FoodResponseItem> foodResponseItems = foodQueryResp.getList().getItem();
            List<USFoodInfoCard> dummyInfoCards = new ArrayList<>();
            for (FoodResponseItem foodResponseItem : foodResponseItems) {
                String name = foodResponseItem.getName();
                USFoodInfoCard dummyCard = new USFoodInfoCard();
                dummyCard.setFoodName(name);
                dummyCard.setNdbno(UUID.randomUUID().toString());
                dummyInfoCards.add(dummyCard);
            }
            FoodReportCardManager dummyReportCardManager = FoodReportCardManager.getInstance(false, false); // Avoid DB connection and further construction
            dummyReportCardManager.feedFoodInfoCards(dummyInfoCards);
            String q = "apple";
            List<USFoodInfoCard> searchResponse = dummyReportCardManager.smartSearch(q);
            assertTrue(searchResponse.size() == foodResponseItems.size());
            q = "app";
            searchResponse = dummyReportCardManager.smartSearch(q);
            assertTrue(searchResponse.size() == foodResponseItems.size());
            q = "ap";
            searchResponse = dummyReportCardManager.smartSearch(q);
            assertTrue(searchResponse.size() == foodResponseItems.size());
            q = "appl";
            searchResponse = dummyReportCardManager.smartSearch(q);
            assertTrue(searchResponse.size() == foodResponseItems.size());
        } catch (Throwable t) {
            t.printStackTrace();
            fail();
        }
    }


}
