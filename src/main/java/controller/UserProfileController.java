package controller;

import manager.FoodReportCardManager;
import manager.HealthTrackerUserManager;
import model.TargetNutritionModel;
import model.UserProfileModel;
import model.UserSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import persistance.HealthTrackerUser;
import util.CommonUtils;
import util.ControllerUtils;
import util.SecurityUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Mephalay on 11/20/2016.
 */
@Controller
public class UserProfileController {

    private Logger logger = Logger.getLogger(this.getClass());
    private HealthTrackerUserManager userManager = HealthTrackerUserManager.getInstance();
    private FoodReportCardManager reportCardManager = FoodReportCardManager.getInstance(true, true);

    @RequestMapping(value = "/editProfile")
    public Object editProfile(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Showing user profile");
        UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");
        if (Boolean.FALSE.equals(userSession.getLogin())) {
            logger.info("User is not logged in, redirecting to login page");
            //TODO Show Alert
            return "redirect:/loginOrRegister";
        }
        UserProfileModel userProfileModel = new UserProfileModel(userSession);
        ModelAndView modelAndView = new ModelAndView("editProfile");
        modelAndView.addObject("userProfileModel", userProfileModel);
        return modelAndView;
    }

    @RequestMapping(value = "/updateUserProfile", method = RequestMethod.POST)
    public Object updateProfile(HttpServletRequest request, HttpServletResponse response, @ModelAttribute UserProfileModel userProfileModel) {
        logger.info("Updating user profile...");
        try {
            boolean isLogin = SecurityUtils.isUserLoggedIn(request);
            if (!isLogin) {
                logger.info("User is not logged in, redirecting to login page.");
                //TODO Show Alert
                return "redirect:/loginOrRegister";
            }
            UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");
            String oldPassword = userProfileModel.getOldPassword();
            String newPassword = userProfileModel.getPassword();
            if (CommonUtils.isEmpty(oldPassword) && CommonUtils.notEmpty(newPassword)) {
                //TODO Show alert
                return "redirect:/editProfile";
            }
            if (CommonUtils.notEmpty(oldPassword) && CommonUtils.notEmpty(newPassword)) {
                String userCurrentPasswordHash = userSession.getPasswordHash();
                String userEnteredOldPasswordHash = SecurityUtils.generateHashWithHMACSHA256(oldPassword);
                if (!userCurrentPasswordHash.equals(userEnteredOldPasswordHash)) {
                    //TODO Password mismatch, generate error
                    return "redirect:/editProfile";
                }
            }
            HealthTrackerUser healthTrackerUser = userManager.updateUser(userSession.getUserId(), userProfileModel);
            SecurityUtils.setLoggedInUserSessionParameters(healthTrackerUser, userSession);
        } catch (Throwable t) {
            logger.error("Failed to update user information", t);
            //TODO Show aler

        }
        return "redirect:/editProfile";
    }

    @RequestMapping(value = "/setTargets", method = RequestMethod.GET)
    public Object showUserTargetSelection(HttpServletRequest request, HttpServletResponse response) {
        if (!SecurityUtils.isUserLoggedIn(request)) {
            logger.info("User is not logged in, redirecting to login/register page");
            //TODO Show Alert
            return "redirect:/loginOrRegister";
        }
        ModelAndView modelAndView = new ModelAndView("userTargets");

        List<TargetNutritionModel> targetNutritionModels = reportCardManager.getTargetNutritionModels();
        modelAndView.addObject("targetNutritionModels", targetNutritionModels);
        return modelAndView;
    }

    @RequestMapping(value = "/viewHistory", method = RequestMethod.GET)
    public Object showUserHistory(HttpServletRequest request, HttpServletResponse response) {
        if (!SecurityUtils.isUserLoggedIn(request)) {
            logger.info("User is not logged in, redirecting to login/register page");
            //TODO Show Alert
            return "redirect:/loginOrRegister";
        }
        ModelAndView modelAndView = new ModelAndView("userFoodAndActivityHistory");
        return modelAndView;
    }

    @RequestMapping(value = "/createUserTarget", method = RequestMethod.POST)
    public Object createUserTargets(HttpServletRequest request, HttpServletResponse response, @RequestBody String requestBody) {
        try {
            logger.info("Received create user target request");
            if (!SecurityUtils.isUserLoggedIn(request)) {
                logger.info("User is not logged in");
                return "redirect:/loginOrRegister";
            }
            Map<String, String> targetMap = ControllerUtils.convertRequestBodyToMap(requestBody);
            if (!targetMap.containsKey("targetWeight")) {
                //TODO Show Alert
                logger.info("User shall define targetWeight in order to update targets, redirecting back to target selection page.");
                return "redirect:/setTargets";
            }
            UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");
            String userId = userSession.getUserId();
            HealthTrackerUser persistedUser = userManager.createUserTargets(userId, targetMap);
            SecurityUtils.setLoggedInUserSessionParameters(persistedUser, userSession);
            return "redirect:/setTargets";
        } catch (Throwable t) {
            logger.error("Failed to process create user targets", t);
            //TODO Show Alert
            return "redirect:/loginOrRegister";
        }
    }
}
