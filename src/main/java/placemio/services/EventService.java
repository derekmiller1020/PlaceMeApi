package placemio.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import placemio.deletion.DeleteEvent;
import placemio.models.EventModel;
import placemio.insertion.InsertEvent;
import placemio.retrieve.RetrieveEvent;
import placemio.update.UpdateEvent;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

    public void deleteEvent(Integer eventId, HttpServletResponse response) throws Exception{
        if (eventId != null){
            new DeleteEvent(eventId).deleteEvent();
        } else {
            response.sendError(400, "You have sent over an invalid id");
        }
    }

}
