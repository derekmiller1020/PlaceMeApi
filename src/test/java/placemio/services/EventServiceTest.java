package placemio.services;


import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import placemio.models.EventModel;
import placemio.models.UserModel;
import placemio.models.submodels.Address;
import placemio.models.submodels.EventContent;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

public class EventServiceTest {

    @Mock
    private UserService userService;

    private HttpServletResponse mockServletResponse;
    private MockHttpServletRequest mockServletRequest;
    private List<EventModel> events = new ArrayList<>();

    @InjectMocks
    EventService eventService;

    @Before
    public void setUp(){
        mockServletResponse = new MockHttpServletResponse();
        mockServletRequest = new MockHttpServletRequest();
        mockServletRequest.setCookies(getCookie());
        //eventService.setEvent(getValidEventModel());
        //when(userService.getUser()).thenReturn(getValidUser());
        //when(userService.getUserFromCookie(new Cookie[]{getCookie()})).thenReturn("4");

    }

    private Cookie getCookie(){
        Cookie cookie = new Cookie("token", "1234");
        cookie.setSecure(false);  // determines whether the cookie should only be sent using a secure protocol, such as HTTPS or SSL
        cookie.setMaxAge(60 * 60 * 24);  // A negative value means that the cookie is not stored persistently and will be deleted when the Web browser exits. A zero value causes the cookie to be deleted.
        cookie.setPath("/");  // The cookie is visible to all the pages in the directory you specify, and all the pages in that directory's subdirectories
        return cookie;
    }

    private EventModel getValidEventModel(){
        EventModel eventModel = new EventModel();
        eventModel.setAddress(getValidAddress());
        eventModel.setEventContent(getValidEvent());
        return eventModel;
    }

    private UserModel getValidUser(){
        UserModel user = new UserModel();
        user.setUserId("4");
        return user;
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

    private EventContent getValidEvent(){
        EventContent eventContent = new EventContent();
        eventContent.setMessage("A generic message");
        eventContent.setAgeGroup("18-25");
        eventContent.setTitle("A generic title");
        eventContent.setType("Garage Sale");
        return eventContent;
    }

}
