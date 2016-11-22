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
import util.SecurityUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
            SecurityUtils.setLoggedInUserSessionParameters(healthTrackerUser, userSession);
            return "redirect:/";
        } catch (RegistrationException e) {
            ModelAndView modelAndView = new ModelAndView("loginOrRegister");
            modelAndView.addObject("errorPrompt", e.getExceptionPrompt());
            return modelAndView;
        }
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
            SecurityUtils.setLoggedInUserSessionParameters(loggedInUser, userSession);
            return "redirect:/";
        } catch (LoginException e) {
            //TODO handle Exceptions
        }

        return "redirect:/loginOrRegister";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Object logoutUser(HttpServletRequest request) {
        UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");
        userSession.setLogin(Boolean.FALSE);
        request.getSession().invalidate();
        return "redirect:/";
    }
}
