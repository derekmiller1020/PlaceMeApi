package placemio;


import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import placemio.token.Authenticator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    private HandlerMethod handlerMethod;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        handlerMethod = (HandlerMethod) handler;
        LoginRequired loginRequired = handlerMethod.getMethod().getAnnotation(LoginRequired.class);
        AuthRequired authRequired = handlerMethod.getMethod().getAnnotation(AuthRequired.class);

        Boolean validUserCredentials = checkUserCredentials(loginRequired, httpServletRequest);
        Boolean validAuthCredentials = checkAuthCredentials(authRequired, httpServletRequest);

        if (!validAuthCredentials){
            httpServletResponse.sendError(400, "You are not authorized!");
        } else if (!validUserCredentials){
            httpServletResponse.sendError(400, "You are not logged in!");
        }

        return super.preHandle(httpServletRequest, httpServletResponse, handler);
    }

    private boolean checkUserCredentials(LoginRequired loginRequired, HttpServletRequest httpServletRequest) throws Exception{
        if (loginRequired == null || !loginRequired.required()) {
            return true;
        } else {
            String cookieToken = "";
            for (Cookie cookie : httpServletRequest.getCookies()){
                if (cookie != null && cookie.getName().equals("token")){
                    if (new Authenticator().checkToken(cookie.getValue())){
                        cookieToken = cookie.getValue();
                    }
                }
            }
            return cookieToken != null && !cookieToken.equals("");
        }
    }

    private boolean checkAuthCredentials(AuthRequired authRequired, HttpServletRequest httpServletRequest) throws Exception{
        if (authRequired == null || !authRequired.required()){
            return true;
        } else {
            String token = httpServletRequest.getHeader("Authorization");
            return !(token == null || token.equals("")) && !new Authenticator().checkToken(token);
        }
    }
}
