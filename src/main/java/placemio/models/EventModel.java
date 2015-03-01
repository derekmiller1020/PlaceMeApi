package placemio.models;


import placemio.models.submodels.Address;
import placemio.models.submodels.EventContent;
import placemio.models.submodels.EventDetails;

import java.util.Map;

public class EventModel {

    private Address address;
    private EventContent eventContent;
    private EventDetails eventDetails;

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

    public void setEventDetails(EventDetails eventDetails){
        this.eventDetails = eventDetails;
    }

    public EventDetails getEventDetails(){
        return this.eventDetails;
    }

}