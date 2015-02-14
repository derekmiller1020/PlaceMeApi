package placemio.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import placemio.AuthRequired;
import placemio.LoginRequired;
import placemio.models.EventModel;
import placemio.services.AuthMe;
import placemio.services.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController

public class Event {

    @Autowired
    EventService eventService;

    @RequestMapping(value="api/v1/event", method=RequestMethod.POST, headers = {"Content-type=application/json"})
    @ResponseBody
    @AuthRequired
    @LoginRequired
    public EventModel createLocation(
            @RequestBody EventModel eventModel,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws Throwable {
        eventService.setEvent(eventModel);
        eventService.create(request, response);
        return eventService.getEvent();
    }

    @RequestMapping(value = "api/v1/event", method= RequestMethod.GET)
    @AuthRequired
    @LoginRequired
    public List<EventModel> getEvents() {
        return eventService.retrieveEvents();
    }
}
