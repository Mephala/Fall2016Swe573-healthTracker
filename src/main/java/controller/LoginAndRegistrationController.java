package controller;

import exception.LoginException;
import exception.RegistrationException;
import manager.HealthTrackerUserManager;
import model.AjaxLoginStatusResponse;
import model.RegisterForm;
import model.UserSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import persistance.HealthTrackerUser;
import util.CalculationUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

/**
 * Created by Mephalay on 10/2/2016.
 */
@Controller
public class LoginAndRegistrationController {

    private HealthTrackerUserManager userManager = HealthTrackerUserManager.getInstance();

    @RequestMapping(value = "/loginOrRegister")
    public Object showRegisterAndLoginPage() {
        ModelAndView modelAndView = new ModelAndView("loginOrRegister");
        return modelAndView;
    }

    @RequestMapping(value = "/doRegister", method = RequestMethod.POST)
    public Object registerUser(HttpServletRequest request, @ModelAttribute RegisterForm registerForm) {
        try {
            HealthTrackerUser healthTrackerUser = userManager.registerUser(registerForm);
            UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");
            setUserParameters(healthTrackerUser, userSession);
            return "redirect:/";
        } catch (RegistrationException e) {
            ModelAndView modelAndView = new ModelAndView("loginOrRegister");
            modelAndView.addObject("errorPrompt", e.getExceptionPrompt());
            return modelAndView;
        }
    }

    private void setUserParameters(HealthTrackerUser healthTrackerUser, UserSession userSession) {
        userSession.setUsername(healthTrackerUser.getUsername());
        userSession.setHeightUnit(healthTrackerUser.getHeightUnit());
        userSession.setWeightUnit(healthTrackerUser.getWeightUnit());
        userSession.setWeight(healthTrackerUser.getWeight());
        userSession.setHeigth(healthTrackerUser.getHeight());
        userSession.setAge(healthTrackerUser.getAge());
        userSession.setActivityLevel(healthTrackerUser.getActivityLevel());
        BigDecimal dailyCalorieNeed = CalculationUtils.calculateDailyCalorieNeedMetric(healthTrackerUser.getWeight(), healthTrackerUser.getHeight(), healthTrackerUser.getAge(), healthTrackerUser.getActivityLevel(), healthTrackerUser.getGender());
        userSession.setDailyCalorieNeed(dailyCalorieNeed.setScale(2, BigDecimal.ROUND_HALF_UP));
        userSession.setLogin(Boolean.TRUE);
    }

    @RequestMapping(value = "/ajax/isLogin", consumes = "application/json; charset=utf8", produces = "application/json; charset=utf8", method = RequestMethod.GET)
    @ResponseBody
    public Object isUserLoggedIn(HttpServletRequest request, HttpServletResponse response) {
        UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");
        AjaxLoginStatusResponse loginStatusResponse = new AjaxLoginStatusResponse();
        loginStatusResponse.setLogin(userSession.getLogin());
        return loginStatusResponse;
    }

    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    public Object loginUser(HttpServletRequest request, @RequestParam String username, @RequestParam String password) {
        try {
            HealthTrackerUser loggedInUser = userManager.loginUser(username, password);
            UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");
            setUserParameters(loggedInUser, userSession);
            return "redirect:/";
        } catch (LoginException e) {
            //TODO handle Exceptions
        }

        return "redirect:/loginOrRegister";
    }
}
