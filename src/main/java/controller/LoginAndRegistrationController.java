package controller;

import exception.LoginException;
import exception.RegistrationException;
import manager.HealthTrackerUserManager;
import model.AjaxLoginStatusResponse;
import model.UserSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import persistance.HealthTrackerUser;

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
    public Object registerUser(HttpServletRequest request, @RequestParam String username, @RequestParam String password) {
        try {
            userManager.registerUser(username, password);
        } catch (RegistrationException e) {
            //TODO handle Exceptions
        }
        return "redirect:/loginOrRegister";
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
            userSession.setLogin(Boolean.TRUE);
            userSession.setUsername(loggedInUser.getUsername());
        } catch (LoginException e) {
            //TODO handle Exceptions
        }

        return "redirect:/loginOrRegister";
    }
}
