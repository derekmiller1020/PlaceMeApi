package placemio.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import placemio.AuthRequired;
import placemio.LoginRequired;
import placemio.models.EventModel;
import placemio.services.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
public class Event {

    @Autowired
    EventService eventService;

    /**
     * Creates Event
     * @param eventModel data coming in should match event Model
     * @param request http request
     * @param response response, typically used for errors
     * @return EventModel returns an event model to json
     * @throws Throwable
     */
    @RequestMapping(value="api/v1/event", method=RequestMethod.POST, headers = {"Content-type=application/json"})
    @ResponseBody
    @AuthRequired
    @LoginRequired
    public EventModel createEvent(
            @RequestBody EventModel eventModel,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws Throwable {
        eventService.setEvent(eventModel);
        eventService.create(request.getCookies(), response);
        return eventService.getEvent();
    }

    /**
     * Gets all of the events - login not required but auth is
     * @return EventModel
     */
    @RequestMapping(value = "api/v1/event", method= RequestMethod.GET)
    @AuthRequired
    public List<EventModel> getEvents() {
        return eventService.retrieveEvents();
    }

    /**
     * gets one event
     * @param eventId event id that is specified on endpoint
     * @return EventModel
     */
    @RequestMapping(value = "api/v1/event/{eventId}", method=RequestMethod.GET)
    @AuthRequired
    public EventModel getEvent(@PathVariable Integer eventId){
        return eventService.retrieveEvent(eventId);
    }

    /**
     * updates one event
     * @param eventId
     * @param eventModel
     * @param response
     * @return EventModel
     * @throws Throwable
     */
    @RequestMapping(value = "api/v1/event/{eventId}", method=RequestMethod.PUT)
    @ResponseBody
    @AuthRequired
    @LoginRequired
    public EventModel updateEvent(@PathVariable Integer eventId,
                                  @RequestBody EventModel eventModel,
                                  HttpServletResponse response) throws Throwable {
        eventService.setEvent(eventModel);
        eventService.updateEvent(eventId, response);
        return eventService.retrieveEvent(eventId);
    }

    @RequestMapping(value = "api/v1/event/{eventId}", method=RequestMethod.DELETE)
    @ResponseBody
    @AuthRequired
    @LoginRequired
    public EventModel deleteEvent(@PathVariable Integer eventId, HttpServletResponse response) throws Throwable {
        eventService.deleteEvent(eventId, response);
        return null; //temporary
    }

}