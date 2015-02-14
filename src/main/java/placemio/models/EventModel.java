package placemio.models;


import placemio.models.submodels.Address;
import placemio.models.submodels.EventContent;
import java.util.Map;

public class EventModel {

    private Address address;
    private EventContent eventContent;
    private Map<String, Object> eventDetails;

    public EventModel(){}

    public void setAddress(Address address){
        this.address = address;
    }

    public Address getAddress(){
        return this.address;
    }

    public void setEventContent(EventContent eventContent){
        this.eventContent = eventContent;
    }

    public EventContent getEventContent(){
        return eventContent;
    }

    public void setEventDetails(Map<String, Object> eventDetails){
        this.eventDetails = eventDetails;
    }

    public Map<String, Object> getEventDetails(){
        return this.eventDetails;
    }

}