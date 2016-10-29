package util;

import model.FoodQueryResponse;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mephalay on 10/25/2016.
 */
public class WebAPIUtils {
    private static String FOOD_QUERY_API_KEY = "mGKWC6iiQy5XABlrCzmJp5Qpyw6HUPByfnu2AT1I";
    private static Logger logger = Logger.getLogger(WebAPIUtils.class);

    public static String queryFood(String foodName) throws URISyntaxException, IOException, HttpException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet request = new HttpGet("http://api.nal.usda.gov/ndb/search/?format=json&q=" + foodName + "&sort=n&max=10000&offset=0&api_key=" + FOOD_QUERY_API_KEY);
        request.addHeader("Content-Type", "application/json; charset=utf-8");
        HttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity);
        return responseString;
    }

    public static String queryFoodReport(String ndbno) throws URISyntaxException, IOException, HttpException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet request = new HttpGet("http://api.nal.usda.gov/ndb/reports/?ndbno=" + ndbno + "&type=b&format=json&api_key=" + FOOD_QUERY_API_KEY);
        request.addHeader("Content-Type", "application/json; charset=utf-8");
        HttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity);
        return responseString;
    }

    public static List<FoodQueryResponse> queryAllFoods() {
        List<FoodQueryResponse> foodQueryResponses = new ArrayList<>();
        Integer offset = 0;
        try {
            String resp = queryForOffset(offset);
            ObjectMapper om = new ObjectMapper();
            FoodQueryResponse foodQueryResponse = om.readValue(resp, FoodQueryResponse.class);
            foodQueryResponses.add(foodQueryResponse);
            Integer total = foodQueryResponse.getList().getTotal();
            Integer end = foodQueryResponse.getList().getEnd();
            offset += end;
            while (total > offset) {
                try {
                    if (offset >= 1000)
                        break; //TODO Remove this section.
                    logger.info("Requesting food query for offset:" + offset);
                    resp = queryForOffset(offset);
                    foodQueryResponse = om.readValue(resp, FoodQueryResponse.class);
                    foodQueryResponses.add(foodQueryResponse);
                    total = foodQueryResponse.getList().getTotal();
                    end = foodQueryResponse.getList().getEnd();
                    Thread.sleep(1000L); // Waiting to prevent overwhelming food dba server.
                } catch (Throwable t) {
                    logger.error("Failed to retrieve list for current offset:" + offset + ", trying next offset");
                }
                offset += end;
            }
        } catch (Throwable t) {
            logger.fatal("Failed to fetch food query response...Needs inspection", t);
        }
        return foodQueryResponses;
    }


    private static String queryForOffset(Integer offset) throws URISyntaxException, IOException, HttpException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet request = new HttpGet("http://api.nal.usda.gov/ndb/search/?format=json&sort=n&max=10000&offset=" + offset + "&api_key=" + FOOD_QUERY_API_KEY);
        request.addHeader("Content-Type", "application/json; charset=utf-8");
        HttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity);
        return responseString;
    }


}
