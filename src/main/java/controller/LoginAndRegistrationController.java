package controller;

import model.HomeModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Mephalay on 10/2/2016.
 */
@Controller
public class LoginAndRegistrationController {

    @RequestMapping(value = "/loginOrRegister")
    public Object home() {
        ModelAndView modelAndView = new ModelAndView("loginOrRegister");
        return modelAndView;
    }
}
