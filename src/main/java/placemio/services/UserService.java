package placemio.services;

import org.springframework.stereotype.Component;
import placemio.models.UserModel;
import placemio.models.insertion.InsertApiUser;
import placemio.models.insertion.InsertUser;
import placemio.models.validation.ValidateAuth;
import placemio.models.validation.ValidateRegistration;
import placemio.models.validation.ValidateUser;
import placemio.token.Authenticator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserService {

    private UserModel user = new UserModel();
    private Authenticator authenticator = new Authenticator();
    private List<String> errorMessages = new ArrayList<String>();
    private ValidateUser validateUser = new ValidateUser();

    public void setUser(UserModel user){
        this.user = user;
    }

    public UserModel getUser(){
        return user;
    }

    public Map<String, Object> authRegistration(HttpServletResponse response) throws Exception{
        Map<String, Object> registerResponse = new HashMap<String, Object>();
        if (validateUser.validCredentials(user.getUsername(), user.getPassword())) {
            try {
                InsertApiUser insertApiUser = new InsertApiUser(validateUser.getUserId());
                insertApiUser.insert();
                registerResponse.putAll(insertApiUser.getCredentials());
            } catch (Exception e) {
                response.sendError(400, "There was an unexpected error");
            }
        } else {
            response.sendError(400, "Credentials were not correct");
        }
        return registerResponse;
    }

    public Map<String, String> authLogin(HttpServletResponse response) throws Exception {
        Map<String, String> authResponse = new HashMap<String, String>();
        ValidateAuth validateAuth = new ValidateAuth();
        if (validateAuth.validCredentials(user.getApiKey(), user.getApiSecret())){
            validateAuth.setToken(user.getApiKey());
            authResponse.put("token", validateAuth.getToken());
        } else {
            response.sendError(400, "You did not send over valid credentials");
        }

        return authResponse;
    }

    public Map<String, String> register(HttpServletResponse response) throws Exception{
        Map<String, String> statusResponse = new HashMap<String, String>();
        Boolean isValid = new ValidateRegistration(user.getUsername(), user.getPassword()).validate();
        if (isValid){
            InsertUser insertUser = new InsertUser(user.getUsername(), user.getPassword());
            insertUser.insert();
            statusResponse.put("Success", "You have successfully registered");
        } else{
            response.sendError(400, "username is already in use");
        }

        return statusResponse;
    }

    public void setUserFromRequest(HttpServletRequest request){
        user.setUserId(getUserFromCookie(request.getCookies()));
    }

    public String getUserFromCookie(Cookie[] users){
        String userId = null;
        if (users == null){
            errorMessages.add("There was no user information added");
        } else {
            try {
                for (Cookie user: users){
                    if (user.getName().equals("token")){
                        if (authenticator.checkToken(user.getValue())){
                            userId = authenticator.getTokenUserId();
                        }
                    }
                }
                if (userId == null || userId.equals("")){
                    errorMessages.add("You do not have a valid token");
                }
            } catch (Exception e){
                errorMessages.add(e.toString());
            }
        }
        return userId;
    }

    public Map<String, Object> getCredentials(HttpServletResponse response) throws Exception{
        Map<String, Object> credentials = new HashMap<String, Object>();
        if (validateUser.validCredentials(user.getUsername(), user.getPassword())){
            validateUser.setToken(user.getUsername());
            credentials.put("token", validateUser.getToken());
            credentials.put("user_id", validateUser.getUserId());
            credentials.put("success", "Login is successful");
            response.addCookie(getCookie());
        } else {
            response.sendError(400, "Invalid Credentials");
        }
        return credentials;
    }

    private Cookie getCookie(){
        Cookie cookie = new Cookie("token", validateUser.getToken());
        cookie.setSecure(false);  // determines whether the cookie should only be sent using a secure protocol, such as HTTPS or SSL
        cookie.setMaxAge(60 * 60 * 24);  // A negative value means that the cookie is not stored persistently and will be deleted when the Web browser exits. A zero value causes the cookie to be deleted.
        cookie.setPath("/");  // The cookie is visible to all the pages in the directory you specify, and all the pages in that directory's subdirectories
        return cookie;
    }
}