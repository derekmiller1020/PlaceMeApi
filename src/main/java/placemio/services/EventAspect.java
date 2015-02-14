package placemio.services;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import placemio.models.submodels.Address;
import placemio.models.validation.ValidateEvent;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Aspect
@Component
public class EventAspect {

    @Autowired
    private EventService eventService;
    private ValidateEvent validateEvent = new ValidateEvent();

    @Around("execution(* placemio.services.EventService.create(..))")
    public void validate(ProceedingJoinPoint joinPoint) throws Throwable{
        Address address = eventService.getEvent().getAddress();
        //validates address fields
        validateEvent.validateAddress(address);
        //sets the longitude and latitude based on address
        if (address != null){
            Map<String, Object> coordinates = validateEvent.getLongitudeLatitude();
            address.setLatitude((Double) coordinates.get("latitude"));
            address.setLongitude((Double) coordinates.get("longitude"));
        }
        //validates the location content
        validateEvent.validateEventContent(eventService.getEvent().getEventContent());

        if (validateEvent.getErrorMessages().size() > 0){
            for (String error: validateEvent.getErrorMessages()){
                eventService.addError(error);
            }
        }
        joinPoint.proceed();
    }

}
