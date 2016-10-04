package controller;

import model.HomeModel;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Handles requests for the application home page..
 */
@Controller
public class HomeController {

    private Logger logger = Logger.getLogger(this.getClass());


    @RequestMapping(value = "/")
    public Object home(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("home");
        logger.info("Home-page requested.");
        HomeModel model = new HomeModel();
        modelAndView.addObject("model", model);
        return modelAndView;
    }
}