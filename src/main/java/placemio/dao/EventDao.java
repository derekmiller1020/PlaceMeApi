package placemio.dao;

import org.springframework.stereotype.Component;
import placemio.dao.deletion.DeleteEvent;
import placemio.dao.insertion.InsertEvent;
import placemio.dao.retrieve.RetrieveEvent;
import placemio.dao.update.UpdateEvent;
import placemio.models.EventModel;
import placemio.models.UserModel;

import java.util.List;

@Component
public class EventDao {

    private InsertEvent insertEvent = new InsertEvent();
    private DeleteEvent deleteEvent = new DeleteEvent();
    private UpdateEvent updateEvent = new UpdateEvent();
    private RetrieveEvent retrieveEvent = new RetrieveEvent();

    public Integer insert(UserModel userModel, EventModel eventModel){
        insertEvent.insertContent(userModel, eventModel);
        return insertEvent.getEventId();
    }

    public void update(Integer eventId, EventModel eventModel){
        updateEvent.update(eventId, eventModel);
    }

    public EventModel getEvent(Integer eventId){
        return retrieveEvent.getEvent(eventId);
    }

    public List<EventModel> getEvents(){
        return retrieveEvent.getEvents();
    }

    public void delete(Integer eventId){
        deleteEvent.deleteEvent(eventId);
    }

    public List<EventModel> getEventByType(String type){
        return retrieveEvent.getEventByType(type);
    }
}
