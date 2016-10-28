package controller;

import model.BWATRegisterForm;
import model.HelloWorld;
import model.RegisteredBWATUser;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import util.CommonUtils;
import util.SecurityUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mephalay on 10/27/2016.
 */
@Controller
public class AuthenticationController {

    private Logger logger = Logger.getLogger(this.getClass());
    private Map<String, RegisteredBWATUser> tempUserList = new HashMap<>();

    @RequestMapping(value = "/auth", method = RequestMethod.POST, produces = "application/json; charset=utf8")
    @ResponseBody
    public Object doAuth(HttpServletRequest request, HttpServletResponse response, @RequestHeader(value = "Authorization", required = false) String basicAuthToken) throws UnsupportedEncodingException {

        if (CommonUtils.isEmpty(basicAuthToken)) {
            response.setHeader("WWW-Authenticate", "Basic realm=\"Health Tracker Application\"");
            response.setStatus(401);
            logger.info("Request made without authorization header.");
            return "forward:/unauthorized";
        } else {
            logger.info(basicAuthToken);
            String[] credentials = SecurityUtils.decodeBasicAuthHeaders(basicAuthToken);
            logger.info("Username:" + credentials[0]);
            logger.info("Password:" + credentials[1]);
            return new HelloWorld();
        }
    }

    @RequestMapping(value = "/unauthorized")
    public void unauthorized(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        logger.info("User forwarded to unauthorized handler");
        response.setHeader("WWW-Authenticate", "Basic realm=\"User Visible Realm\"");
        response.setStatus(401);
    }

    @RequestMapping(value = "/registerAuth", method = RequestMethod.POST, produces = "application/json; charset=utf8")
    @ResponseBody
    public Object registerUser(HttpServletRequest request, HttpServletResponse response, @RequestBody BWATRegisterForm registerForm) throws UnsupportedEncodingException {
        logger.info(registerForm);
        return new HelloWorld();
    }
}
