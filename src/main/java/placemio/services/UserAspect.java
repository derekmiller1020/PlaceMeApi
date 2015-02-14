package placemio.services;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

@Aspect
@Component
public class UserAspect {


    @Before("@annotation(placemio.services.AuthMe))")
    public void advice(){
        System.out.println();
        System.out.println("asdfasdfsd");
    }
}
