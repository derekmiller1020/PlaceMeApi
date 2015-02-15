package placemio.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import placemio.models.EventModel;
import placemio.models.insertion.InsertEvent;
import placemio.models.retrieve.RetrieveEvent;
import placemio.models.update.UpdateEvent;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Component
public class EventService {

    @Autowired
    UserService userService;

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
            InsertEvent insertEvent = new InsertEvent();
            insertEvent.insertContent(userService.getUser(), eventModel.getAddress(), eventModel.getEventContent());
            eventId = insertEvent.getEventId();
        }catch (Exception e){
            response.sendError(400, "There was an error sending your data");
        }
    }

    public List<EventModel> retrieveEvents(){
        return new RetrieveEvent().getEvents();
    }

    public EventModel retrieveEvent(){
        if (eventId != null){
            return new RetrieveEvent().getEvent(eventId);
        } else {
            return null;
        }
    }

    public EventModel retrieveEvent(Integer eventId){
        if (eventId != null){
            return new RetrieveEvent().getEvent(eventId);
        } else {
            return null;
        }
    }

    @EventValidation
    public void updateEvent(Integer eventId, HttpServletResponse response) throws Exception{
        if (eventId != null){
            UpdateEvent update = new UpdateEvent();
            update.update(eventId, getEvent().getAddress(), getEvent().getEventContent());
        } else {
            response.sendError(400, "You have sent over invalid data");
        }
    }

}
