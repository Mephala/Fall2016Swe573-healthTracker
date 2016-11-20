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
    private Map<String, IdentifiedAnnotationObject> simpleAnnotationMap = new HashMap<>();
    private Map<String, User> tokenToUserMap = new HashMap<>();

    @RequestMapping(value = "/auth", method = RequestMethod.POST, produces = "application/json; charset=utf8")
    @ResponseBody
    public Object doAuth(HttpServletRequest request, HttpServletResponse response, @RequestHeader(value = "Authorization", required = false) String basicAuthToken) throws UnsupportedEncodingException {

        if (CommonUtils.isEmpty(basicAuthToken)) {
            return redirectToUnauthArea(response);
        } else {
            logger.info(basicAuthToken);
            String[] credentials = SecurityUtils.decodeBasicAuthHeaders(basicAuthToken);
            logger.info("Username:" + credentials[0]);
            logger.info("Password:" + credentials[1]);
            User user = tokenToUserMap.get(basicAuthToken);
            if (user == null) {
                return redirectToUnauthArea(response);
            }
            AnnotationUser annotationUser = createAnnotationUserBasedOnSavedOne(user);
            return annotationUser;
        }
    }

    private Object redirectToUnauthArea(HttpServletResponse response) {
        response.setHeader("WWW-Authenticate", "Basic realm=\"Health Tracker Application\"");
        response.setStatus(401);
        logger.info("Request made without authorization header.");
        return "forward:/unauthorized";
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
        User user = new User();
        user.email = registerForm.getEmail();
        user.lastName = registerForm.getLastName();
        user.pw = registerForm.getPassword();
        user.firstName = registerForm.getFirstName();
        user.token = SecurityUtils.tokenizeUsernamePassword(user.email, user.pw);
        tokenToUserMap.put(user.token, user);
        AnnotationUser annotationUser = createAnnotationUserBasedOnSavedOne(user);
        return annotationUser;
    }

    private AnnotationUser createAnnotationUserBasedOnSavedOne(User user) {
        AnnotationUser annotationUser = new AnnotationUser();
        annotationUser.setEmail(user.email);
        annotationUser.setFirstName(user.firstName);
        annotationUser.setLastName(user.lastName);
        return annotationUser;
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
    public Object createAnnotation(HttpServletRequest request, HttpServletResponse response, @RequestBody SimpleAnnotationObject annotation, @RequestHeader(value = "Authorization", required = false) String token) {
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
        if (!CommonUtils.isEmpty(token)) {
            User user = tokenToUserMap.get(token);
            user.userAnnotations.add(identifiedAnnotationObject);
        }
        response.setStatus(HttpServletResponse.SC_SEE_OTHER);
        return "redirect:/annotations/" + id;
    }

    @RequestMapping(value = "/simple", method = RequestMethod.GET)
    @ResponseBody
    public Object returnSimple(HttpServletRequest request, HttpServletResponse response) {
        try {
            Thread.sleep(10000L);
        } catch (Throwable t) {

        }
        List<String> pageNames = new ArrayList<>();
        pageNames.add("Page 1");
        pageNames.add("Page 2");
        pageNames.add("Page 3");
        pageNames.add("Page 4");
        pageNames.add("Page 5");
        pageNames.add("Page 6");
        return pageNames;
    }

    @RequestMapping(value = "/annotations", method = RequestMethod.GET)
    @ResponseBody
    public Object getAllAnnotations(HttpServletRequest request, HttpServletResponse response, @RequestHeader(value = "Authorization", required = false) String token) {
        return searchForAnnotations(request, token);
    }

    private Object searchForAnnotations(HttpServletRequest request, String token) {
        User user = tokenToUserMap.get(token);
        Set<IdentifiedAnnotationObject> privateAnnotations = new HashSet<>();
        Set<String> tokens = tokenToUserMap.keySet();
        for (String tok : tokens) {
            User regUser = tokenToUserMap.get(tok);
            privateAnnotations.addAll(regUser.userAnnotations);
        }
        if (user != null) {
            privateAnnotations.removeAll(user.userAnnotations);
        }
        logger.info("Retrieving all annotations for a page...");
        RefererAndToken refererAndToken = stripRefererAndToken(request);
        logger.info("Referer:" + refererAndToken.referer);
        logger.info("Auth-Token:" + refererAndToken.token);
        Collection<IdentifiedAnnotationObject> identifiedAnnotations = simpleAnnotationMap.values();
        List<IdentifiedAnnotationObject> refererAnnotations = new ArrayList<>();
        for (IdentifiedAnnotationObject identifiedAnnotation : identifiedAnnotations) {
            if (identifiedAnnotation.getTarget().getId().equals(refererAndToken.referer)) {
                if (!privateAnnotations.contains(identifiedAnnotation)) {
                    refererAnnotations.add(identifiedAnnotation);
                }
            }
        }

        AnnotationSearchResult result = new AnnotationSearchResult();
        result.setTotal(refererAnnotations.size());
        result.setRows(refererAnnotations);
        return result;
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public Object searchAnnotations(HttpServletRequest request, HttpServletResponse response, @RequestHeader(value = "Authorization", required = false) String token) {
        return searchForAnnotations(request, token);
    }

    @RequestMapping(value = "/annotations/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Object showAnnotation(HttpServletRequest request, HttpServletResponse response, @PathVariable String id) {
        logger.info("Showing annotation");
        return simpleAnnotationMap.get(id);
    }

    @RequestMapping(value = "/annotations/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Object updateAnnotation(HttpServletRequest request, HttpServletResponse response, @PathVariable String id,
                                   @RequestBody SimpleAnnotationObject annotation, @RequestHeader(value = "Authorization", required = false) String token) {
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

    class User {
        String firstName;
        String pw;
        String email;
        String lastName;
        String token;
        List<IdentifiedAnnotationObject> userAnnotations = new ArrayList<>();

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            User user = (User) o;

            return token.equals(user.token);

        }

        @Override
        public int hashCode() {
            return token.hashCode();
        }
    }

}
