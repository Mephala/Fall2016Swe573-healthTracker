package controller;

import manager.HealthTrackerUserManager;
import model.CalorieGraphResponse;
import model.GraphResponse;
import model.UserSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import persistance.HealthTrackerUser;
import persistance.UserDailyActivity;
import util.CalculationUtils;
import util.CommonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mephalay on 11/29/2016.
 */
@Controller
public class ReportingController {

    private Logger logger = Logger.getLogger(this.getClass());
    private HealthTrackerUserManager userManager = HealthTrackerUserManager.getInstance();


    @RequestMapping(value = "/showReporting")
    public Object showReporting(HttpServletRequest request, HttpServletResponse response) {
//        UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");
//        if (Boolean.FALSE.equals(userSession.getLogin())) {
//            //TODO Alert login requirements
//            return "redirect:/loginOrRegister";
//        }
        ModelAndView modelAndView = new ModelAndView("reporting");
        return modelAndView;
    }

    @RequestMapping(value = "/testDateFormatting", method = RequestMethod.POST, consumes = "application/json; charset=utf8", produces = "application/json; charset=utf8")
    @ResponseBody
    public Object testDateFormatting(HttpServletRequest request, HttpServletResponse response, @RequestBody String date) {
        logger.info("Received date:" + date);
        return "{\"data\":\"gokhan\"}";
    }

    @RequestMapping(value = "/getGraphData", method = RequestMethod.GET, produces = "application/json; charset=utf8")
    @ResponseBody
    public Object getGraphData(HttpServletRequest request, HttpServletResponse response, @RequestParam String variable) {
        UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");
        if (Boolean.FALSE.equals(userSession.getLogin())) {
            //TODO Alert login requirements
            return new CalorieGraphResponse();
        }


        logger.info("Received graph data request for variable:" + variable);
        String userId = userSession.getUserId();
        HealthTrackerUser persistedUser = userManager.getUserById(userId);
        List<UserDailyActivity> userActivities = persistedUser.getUserDailyActivities();
        if (CommonUtils.isEmpty(userActivities)) {
            CalorieGraphResponse calorieGraphResponse = new CalorieGraphResponse();
            calorieGraphResponse.setDataFound(Boolean.FALSE);
            return calorieGraphResponse;
        }
        List<GraphResponse> graphResponseList = new ArrayList<>();

        for (UserDailyActivity userActivity : userActivities) {
            String date = userActivity.getActivityDate();
            String[] dateSplits = date.split("-");
            Integer year = Integer.parseInt(dateSplits[0]);
            Integer month = Integer.parseInt(dateSplits[1]);
            Integer day = Integer.parseInt(dateSplits[2]);
            BigDecimal calorieIntake = CalculationUtils.calculateCalorieIntakeForDailyActivity(userActivity);
            calorieIntake = calorieIntake.setScale(0, BigDecimal.ROUND_HALF_UP);
            Integer calorieIntakeInteger = Integer.parseInt(calorieIntake.toPlainString());
            BigDecimal calorieOutput = CalculationUtils.calculateCalorieOutputForDailyActivity(userActivity);
            calorieOutput = calorieOutput.setScale(0, BigDecimal.ROUND_HALF_UP);
            Integer calorieOutputInteger = Integer.parseInt(calorieOutput.toPlainString());
            BigDecimal dailyCalorieNeed = userSession.getDailyCalorieNeed();
            dailyCalorieNeed = dailyCalorieNeed.setScale(0, BigDecimal.ROUND_HALF_UP);
            Integer dailyCalorieNeedInteger = Integer.parseInt(dailyCalorieNeed.toPlainString());
            GraphResponse graphResponse = new GraphResponse();
            graphResponse.setDay(day);
            graphResponse.setMonth(month - 1); // For js, january = 0.
            graphResponse.setYear(year);
            graphResponse.setDailyCalorieNeed(dailyCalorieNeedInteger);
            graphResponse.setCalorieBurned(calorieOutputInteger);
            graphResponse.setCalorieTaken(calorieIntakeInteger);
            graphResponseList.add(graphResponse);
        }
        CalorieGraphResponse calorieGraphResponse = new CalorieGraphResponse();
        calorieGraphResponse.setDataFound(Boolean.TRUE);
        calorieGraphResponse.setLoginSuccess(Boolean.TRUE);
        calorieGraphResponse.setGraphResponseList(graphResponseList);
        return calorieGraphResponse;
    }
}
