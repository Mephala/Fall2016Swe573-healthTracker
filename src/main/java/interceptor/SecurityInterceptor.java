package interceptor;

import model.UserSession;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Mephalay on 10/2/2016.
 */
public class SecurityInterceptor implements HandlerInterceptor {

    private static final long BUILD_STAMP = System.currentTimeMillis();


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //TODO Implement db configurations
        String reqUrl = request.getRequestURL().toString();
        reqUrl = reqUrl.replaceAll("http://", "");
        String[] reqDivs = reqUrl.split("/");
        String serverPath = reqDivs[0];
        String[] serverPathDivs = serverPath.split(":");
        String serverContextPathPrefix = "";
        if (serverPathDivs.length == 2) {
            serverContextPathPrefix = ":" + serverPathDivs[1];
        }
        String serverBase = serverPathDivs[0];
        request.getSession().setAttribute("serverBase", serverBase);
        request.getSession().setAttribute("serverContext", serverContextPathPrefix + "/healthTracker");
        request.getSession().setAttribute("servletRoot", "/healthTracker");
        request.getSession().setAttribute("buildStamp", Long.valueOf(BUILD_STAMP));
        if (request.getSession().getAttribute("userSession") == null) {
            UserSession userSession = new UserSession();
            request.getSession().setAttribute("userSession", userSession);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }


}