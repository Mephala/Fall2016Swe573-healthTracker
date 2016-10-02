package controller;

import model.HomeModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Handles requests for the application home page..
 */
@Controller
public class HomeController {


    @RequestMapping(value = "/")
    public Object home(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("home");
        HomeModel model = new HomeModel();
        modelAndView.addObject("model",model);
        return modelAndView;
    }
}