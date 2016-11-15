package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Mephalay on 11/15/2016.
 */
@Controller
public class BMIController {

    @RequestMapping(value = "/calculateBMI")
    public Object calculateBMI(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("bmiCalculator");
        return modelAndView;
    }
}
