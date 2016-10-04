package controller;

import exception.RegistrationException;
import manager.HealthTrackerUserManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

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
}
