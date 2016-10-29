package controller;

import manager.FoodReportCardManager;
import model.*;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import util.WebAPIUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Mephalay on 10/25/2016.
 */
@Controller
public class FoodController {
    private Logger logger = Logger.getLogger(this.getClass());
    private FoodReportCardManager foodReportCardManager = FoodReportCardManager.getInstance();

    @RequestMapping(value = "/queryFood", method = RequestMethod.GET)
    public Object queryFood(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Showing food querying");
        ModelAndView model = new ModelAndView("foodQuery");
        model.addObject("searchResultFound", Boolean.FALSE); // No search result to show, just initial screen
        return model;
    }

    @RequestMapping(value = "/doQueryFood", method = RequestMethod.POST)
    public Object queryFood(HttpServletRequest request, HttpServletResponse response, @RequestParam String foodName) {
        logger.info("Querying for  food...");
        try {
            String foodListResponseString = WebAPIUtils.queryFood(foodName);
            ObjectMapper om = new ObjectMapper();
            FoodQueryResponse foodQueryResponse = om.readValue(foodListResponseString, FoodQueryResponse.class);
            FoodResponseList foodResponseList = foodQueryResponse.getList();
            if (foodResponseList == null) {
                ModelAndView model = new ModelAndView("foodQuery");
                return model;
            }
            List<BasicFoodResponse> basicFoodResponseList = new ArrayList<>();
            List<FoodResponseItem> foodResponseItemList = foodResponseList.getItem();
            final int limit = 5;
            int count = 0;
            for (FoodResponseItem item : foodResponseItemList) {
                String dbNo = item.getNdbno();
                String name = item.getName();
                BasicFoodResponse basicFoodResponse = new BasicFoodResponse();
                basicFoodResponse.setTitle(name);
                basicFoodResponse.setJson(WebAPIUtils.queryFoodReport(dbNo));
                basicFoodResponse.setHrefId(UUID.randomUUID().toString());
                count++;
                basicFoodResponseList.add(basicFoodResponse);
                if (count == limit)
                    break;
            }
            BasicFoodResponseList searchResultList = new BasicFoodResponseList();
            searchResultList.setBasicFoodResponseList(basicFoodResponseList);
            ModelAndView model = new ModelAndView("foodQuery");
            if (basicFoodResponseList.size() > 0) {
                model.addObject("searchResultFound", Boolean.TRUE);
                model.addObject("foodResults", searchResultList);
            } else {
                model.addObject("searchResultFound", Boolean.FALSE);

            }
            return model;
        } catch (Throwable t) {
            logger.error("Error occured", t);
            ModelAndView model = new ModelAndView("foodQuery");
            return model;
        }

    }
}
