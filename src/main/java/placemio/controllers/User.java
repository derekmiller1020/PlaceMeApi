package placemio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import placemio.models.UserModel;
import placemio.services.UserService;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;


@RestController
public class User {

    @Autowired
    UserService userService;

    @RequestMapping(value = "api/v1/login", method = RequestMethod.POST)
    public Map<String, Object> login(@RequestBody UserModel user, HttpServletResponse response) throws Throwable{
        userService.setUser(user);
        return userService.getCredentials(response);
    }

    @RequestMapping(value = "api/v1/register", method = RequestMethod.POST)
    public Map<String, String> register(@RequestBody UserModel user, HttpServletResponse response) throws Throwable{
        userService.setUser(user);
        return userService.register(response);
    }

    @RequestMapping(value = "api/v1/auth/register")
    public Map<String, String> registerApi(@RequestBody UserModel user, HttpServletResponse response) throws Throwable{
        userService.setUser(user);
        return userService.authRegistration(response);
    }

    @RequestMapping(value = "api/v1/auth/login")
    public String loginApi(@RequestBody UserModel user, HttpServletResponse response){
        userService.setUser(user);
        return "";
    }

}
