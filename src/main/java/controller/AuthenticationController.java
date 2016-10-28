package controller;

import model.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import util.CommonUtils;
import util.SecurityUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Created by Mephalay on 10/27/2016.
 */
@Controller
public class AuthenticationController {

    private Logger logger = Logger.getLogger(this.getClass());
    private Map<String, RegisteredBWATUser> tempUserList = new HashMap<>();
    private Map<String, IdentifiedAnnotationObject> simpleAnnotationMap = new HashMap<>();

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

    private RefererAndToken stripRefererAndToken(HttpServletRequest request) {
        String refererPage = null, authToken = null;
        Enumeration headers = request.getHeaderNames();
        while (headers.hasMoreElements()) {
            String headerName = headers.nextElement().toString();
            logger.info(headerName + " -> " + request.getHeader(headerName));
            if (headerName.equals("referer")) {
                refererPage = request.getHeader(headerName);
            } else if (headerName.equals("Authorization")) {
                authToken = request.getHeader(headerName);
            }
        }
        RefererAndToken refererAndToken = new RefererAndToken();
        refererAndToken.referer = refererPage;
        refererAndToken.token = authToken;
        return refererAndToken;
    }

    @RequestMapping(value = "/annotations", method = RequestMethod.POST)
    public Object createAnnotation(HttpServletRequest request, HttpServletResponse response, @RequestBody SimpleAnnotationObject annotation) {
        RefererAndToken refererAndToken = stripRefererAndToken(request);
        logger.info(request.getMethod());
        logger.info(annotation);
        IdentifiedAnnotationObject identifiedAnnotationObject = new IdentifiedAnnotationObject();
        String id = UUID.randomUUID().toString();
        identifiedAnnotationObject.setId(id);
        simpleAnnotationMap.put(id, identifiedAnnotationObject);
        identifiedAnnotationObject.setQuote(annotation.getQuote());
        identifiedAnnotationObject.setRanges(annotation.getRanges());
        identifiedAnnotationObject.setText(annotation.getText());
        AnnotationTarget target = new AnnotationTarget();
        target.setId(refererAndToken.referer);
        identifiedAnnotationObject.setTarget(target);
        response.setStatus(HttpServletResponse.SC_SEE_OTHER);
        return "redirect:/annotations/" + id;
    }

    @RequestMapping(value = "/annotations", method = RequestMethod.GET)
    @ResponseBody
    public Object getAllAnnotations(HttpServletRequest request, HttpServletResponse response) {
        return searchForAnnotations(request);
    }

    private Object searchForAnnotations(HttpServletRequest request) {
        logger.info("Retrieving all annotations for a page...");
        RefererAndToken refererAndToken = stripRefererAndToken(request);
        logger.info("Referer:" + refererAndToken.referer);
        logger.info("Auth-Token:" + refererAndToken.token);
        Collection<IdentifiedAnnotationObject> identifiedAnnotations = simpleAnnotationMap.values();
        List<IdentifiedAnnotationObject> refererAnnotations = new ArrayList<>();
        for (IdentifiedAnnotationObject identifiedAnnotation : identifiedAnnotations) {
            if (identifiedAnnotation.getTarget().getId().equals(refererAndToken.referer)) {
                refererAnnotations.add(identifiedAnnotation);
            }
        }
        AnnotationSearchResult result = new AnnotationSearchResult();
        result.setTotal(refererAnnotations.size());
        result.setRows(refererAnnotations);
        return result;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public Object searchAnnotations(HttpServletRequest request, HttpServletResponse response) {
        return searchForAnnotations(request);
    }

    @RequestMapping(value = "/annotations/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object showAnnotation(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) {
        logger.info("Showing annotation");
        return simpleAnnotationMap.get(id);
    }

    @RequestMapping(value = "/annotations/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Object updateAnnotation(HttpServletRequest request, HttpServletResponse response, @PathVariable String id, @RequestBody SimpleAnnotationObject annotation) {
        logger.info("Updating annotation");
        IdentifiedAnnotationObject identifiedAnnotationObject = simpleAnnotationMap.get(id);
        identifiedAnnotationObject.setQuote(annotation.getQuote());
        identifiedAnnotationObject.setRanges(annotation.getRanges());
        identifiedAnnotationObject.setText(annotation.getText());
        return identifiedAnnotationObject;
    }


    class RefererAndToken {
        String referer;
        String token;
    }

}
