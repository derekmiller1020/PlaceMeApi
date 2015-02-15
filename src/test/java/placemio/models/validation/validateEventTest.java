package placemio.models.validation;


import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

import placemio.controllers.Event;
import placemio.models.submodels.Address;
import placemio.models.submodels.EventContent;

public class ValidateEventTest {

    private ValidateEvent validateEvent;

    @Before
    public void setUp(){
        validateEvent = new ValidateEvent();
    }

    @Test
    public void validateValidAddress(){
        Address address = getValidAddress();
        validateEvent.validateAddress(address);
        assertTrue(validateEvent.getErrorMessages().isEmpty());
    }

    @Test
    public void validateInvalidAddress(){
        Address invalidAddress = getInvalidAddress();
        validateEvent.validateAddress(invalidAddress);
        assertEquals(validateEvent.getErrorMessages().size(), 4);
    }

    @Test
    public void validateValidEventContent(){
        EventContent eventContent = getValidEvent();
        validateEvent.validateEventContent(eventContent);
        assertTrue(validateEvent.getErrorMessages().isEmpty());
    }

    @Test
    public void validateInvalidEventContent(){
        EventContent eventContent = getInvalidEvent();
        validateEvent.validateEventContent(eventContent);
        assertEquals(validateEvent.getErrorMessages().size(), 3);
    }

    private Address getValidAddress(){
        Address address = new Address();
        address.setStreetAddress("4837 Crittenden Ave");
        address.setCity("Indianapolis");
        address.setState("Indiana");
        address.setZipCode(46205);
        address.setCountry("USA");
        return address;
    }

    private Address getInvalidAddress(){
        Address address = new Address();
        address.setStreetAddress("");
        address.setCity("");
        address.setState("");
        return address;
    }

    private EventContent getValidEvent(){
        EventContent eventContent = new EventContent();
        eventContent.setMessage("A generic message");
        eventContent.setAgeGroup("18-25");
        eventContent.setTitle("A generic title");
        eventContent.setType("Garage Sale");
        return eventContent;
    }

    private EventContent getInvalidEvent(){
        EventContent eventContent = new EventContent();
        eventContent.setMessage("");
        eventContent.setAgeGroup("");
        eventContent.setTitle("");
        eventContent.setType("");
        return eventContent;
    }
}
