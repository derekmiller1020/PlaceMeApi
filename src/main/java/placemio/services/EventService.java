package placemio.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import placemio.models.EventModel;
import placemio.models.insertion.InsertEvent;
import placemio.models.retrieve.RetrieveEvent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Component
public class EventService {

    @Autowired
    UserService userService;

    private EventModel eventModel;
    private List<String> errors = new ArrayList<String>();
    private Integer eventId;

    public void setEvent(EventModel eventModel){
        this.eventModel = eventModel;
    }

    public EventModel getEvent(){
        return eventModel;
    }

    public void create(HttpServletRequest request, HttpServletResponse response) throws Exception{
        if(errors.size() == 0){
            try{
                userService.setUserFromRequest(request);
                InsertEvent insertEvent = new InsertEvent();
                insertEvent.insertContent(userService.getUser(), eventModel.getAddress(), eventModel.getEventContent());
                eventId = insertEvent.getEventId();
            }catch (Exception e){
                response.sendError(400, "There was an error sending your data");
            }
        } else {
            response.sendError(400, "You sent over invalid data");
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

    public void addError(String error){
        errors.add(error);
    }

}
