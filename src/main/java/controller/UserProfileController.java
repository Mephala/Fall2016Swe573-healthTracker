package controller;

import manager.HealthTrackerUserManager;
import model.UserProfileModel;
import model.UserSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import persistance.HealthTrackerUser;
import util.CommonUtils;
import util.SecurityUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Mephalay on 11/20/2016.
 */
@Controller
public class UserProfileController {

    private Logger logger = Logger.getLogger(this.getClass());
    private HealthTrackerUserManager userManager = HealthTrackerUserManager.getInstance();

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
            UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");
            if (Boolean.FALSE.equals(userSession.getLogin())) {
                logger.info("User is not logged in, redirecting to login page.");
                //TODO Show Alert
                return "redirect:/loginOrRegister";
            }
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
}
