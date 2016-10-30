package controller;

import manager.FoodReportCardManager;
import model.*;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import persistance.USFoodInfoCard;
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
    private FoodReportCardManager foodReportCardManager = FoodReportCardManager.getInstance(true, true);

    @RequestMapping(value = "/queryFood", method = RequestMethod.GET)
    public Object queryFood(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Showing food querying");
        ModelAndView model = new ModelAndView("foodQuery");
        model.addObject("searchResultFound", Boolean.FALSE); // No search result to show, just initial screen
        return model;
    }

    @RequestMapping(value = "/ajax/queryFoodName", method = RequestMethod.POST, produces = "application/json; charset=utf8", consumes = "application/json; charset=utf8")
    @ResponseBody
    public Object ajaxFoodSearch(HttpServletRequest request, HttpServletResponse response, @RequestBody AjaxSearchRequest ajaxSearchRequest) {
        logger.info("Received ajax search request for food names...");
        List<USFoodInfoCard> foodInfoCards = foodReportCardManager.smartSearch(ajaxSearchRequest.getSearchKeyword());
        List<String> searchResponse = new ArrayList<>();
        for (USFoodInfoCard foodInfoCard : foodInfoCards) {
            searchResponse.add(foodInfoCard.getFoodName());
        }
        AjaxSearchResponse ajaxSearchResponse = new AjaxSearchResponse();
        ajaxSearchResponse.setAvailableKeywords(searchResponse);
        return ajaxSearchResponse;
    }

    @RequestMapping(value = "/ajax/addFood")
    public Object loadActivities(HttpServletRequest request, HttpServletResponse response, @RequestBody AjaxAddFoodRequest addFoodRequest) {
        logger.info("Adding food with name:" + addFoodRequest.getAddedFood());
        ModelAndView modelAndView = new ModelAndView("foodAndActivityUpdate");
        return modelAndView;
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
