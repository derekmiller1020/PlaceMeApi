package placemio.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import placemio.dao.EventDao;
import placemio.models.EventModel;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Component
public class EventService {

    @Autowired
    private UserService userService;
    @Autowired
    private EventDao eventDao;

    private EventModel eventModel;
    private Integer eventId;

    public void setEvent(EventModel eventModel){
        this.eventModel = eventModel;
    }

    public EventModel getEvent(){
        return eventModel;
    }

    @EventValidation
    public void create(Cookie[] cookies, HttpServletResponse response) throws Exception{
        try{
            userService.setUserFromRequest(cookies);
            eventId = eventDao.insert(userService.getUser(), eventModel);
        }catch (Exception e){
            response.sendError(400, "There was an error sending your data");
        }
    }

    public List<EventModel> retrieveEvents(){
        return eventDao.getEvents();
    }

    public EventModel retrieveEvent(Integer eventId){
        if (eventId != null){
            return eventDao.getEvent(eventId);
        } else {
            return null;
        }
    }

    @EventValidation
    public void updateEvent(Integer eventId, HttpServletResponse response) throws Exception{
        if (eventId != null){
            eventDao.update(eventId, getEvent());
        } else {
            response.sendError(400, "You have sent over invalid data");
        }
    }

    public void deleteEvent(Integer eventId, HttpServletResponse response) throws Exception{
        if (eventId != null){
            eventDao.delete(eventId);
        } else {
            response.sendError(400, "You have sent over an invalid id");
        }
    }

}
