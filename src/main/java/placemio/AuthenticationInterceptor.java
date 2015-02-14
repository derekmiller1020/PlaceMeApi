package placemio;


import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import placemio.token.Authenticator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        LoginRequired loginRequired = handlerMethod.getMethod().getAnnotation(LoginRequired.class);
        if (loginRequired == null || !loginRequired.required()) {
            return true;
        }

        String token = httpServletRequest.getHeader("Authorization");

        String cookieToken = "";
        for (Cookie cookie : httpServletRequest.getCookies()){
            if (cookie != null && cookie.getName().equals("token")){
                if (new Authenticator().checkToken(cookie.getValue())){
                    cookieToken = cookie.getValue();
                }
            }
        }

        if ((token == null || token.equals("")) && cookieToken.equals("")) {
            httpServletResponse.sendError(400, "Put that token in there!");
            return true;
        }

        if (!new Authenticator().checkToken(token) && cookieToken.equals("")){
            httpServletResponse.sendError(400, "That token isn't correct!");
            return true;
        }

        return super.preHandle(httpServletRequest, httpServletResponse, handler);
    }
}
