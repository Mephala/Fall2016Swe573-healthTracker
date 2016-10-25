package util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Created by Mephalay on 10/25/2016.
 */
public class WebAPIUtils {
    private static String FOOD_QUERY_API_KEY = "mGKWC6iiQy5XABlrCzmJp5Qpyw6HUPByfnu2AT1I";

    public static String queryFood(String foodName) throws URISyntaxException, IOException, HttpException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet request = new HttpGet("http://api.nal.usda.gov/ndb/search/?format=json&q=" + foodName + "&sort=n&max=10000&offset=0&api_key=" + FOOD_QUERY_API_KEY);
        ObjectMapper om = new ObjectMapper();
        request.addHeader("Content-Type", "application/json; charset=utf-8");
        HttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity);
        return responseString;
    }

    public static String queryFoodReport(String ndbno) throws URISyntaxException, IOException, HttpException {
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet request = new HttpGet("http://api.nal.usda.gov/ndb/reports/?ndbno="+ndbno+"&type=b&format=json&api_key=" + FOOD_QUERY_API_KEY);
        ObjectMapper om = new ObjectMapper();
        request.addHeader("Content-Type", "application/json; charset=utf-8");
        HttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();
        String responseString = EntityUtils.toString(entity);
        return responseString;
    }
}
