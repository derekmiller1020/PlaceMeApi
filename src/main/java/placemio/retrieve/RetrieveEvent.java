package placemio.retrieve;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import placemio.DatabaseConnection;
import placemio.models.EventModel;
import placemio.models.submodels.Address;
import placemio.models.submodels.EventContent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RetrieveEvent {

    private List<EventModel> results;
    private JdbcTemplate db = DatabaseConnection.getDatabaseConnection();

    public List<EventModel> getEvents(){
        retrieveData();
        return results;
    }

    public EventModel getEvent(Integer eventId){
        return retrieveEvent(eventId);
    }

    public EventModel getEventByType(String type){
        results = db.query(
                "SELECT * from event e " +
                        "INNER JOIN event_address ea ON ea.event_id = e.id " +
                        "INNER JOIN event_content ec ON ec.event_id = ea.event_id " +
                        "WHERE ec.type = ?",
                new Object[]{type},
                new RowMapper<EventModel>() {
                    @Override
                    public EventModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return translateEventData(rs);
                    }
                });
        return results.isEmpty() ? null : results.get(0);
    }

    private List<EventModel> retrieveData(){
        results = db.query(
                "SELECT * from event e " +
                        "INNER JOIN event_address ea ON ea.event_id = e.id " +
                        "INNER JOIN event_content ec ON ec.event_id = ea.event_id ",
                new RowMapper<EventModel>() {
                    @Override
                    public EventModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return translateEventData(rs);
                    }
                });
        return results;
    }

    private EventModel retrieveEvent(Integer eventId){
        results = db.query(
                "SELECT * from event e " +
                        "INNER JOIN event_address ea ON ea.event_id = e.id " +
                        "INNER JOIN event_content ec ON ec.event_id = ea.event_id " +
                        "WHERE e.id = ?",
                new Object[]{eventId},
                new RowMapper<EventModel>() {
                    @Override
                    public EventModel mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return translateEventData(rs);
                    }
                });
        return results.isEmpty() ? null : results.get(0);
    }

    private EventModel translateEventData(ResultSet rs) throws SQLException{
        EventModel eventModel = new EventModel();
        //set event details information
        Map<String, Object> eventDetails = new HashMap<String, Object>();
        eventDetails.put("user_id", rs.getInt("USER_ID"));
        eventDetails.put("creation_date", rs.getDate("CREATION_DATE"));
        eventDetails.put("event_date", rs.getDate("EVENT_DATE"));
        eventDetails.put("event_id", rs.getInt("ID"));
        eventModel.setEventDetails(eventDetails);

        //set address information
        Address address = new Address();
        address.setStreetAddress(rs.getString("STREET_ADDRESS"));
        address.setStreetAddress2(rs.getString("STREET_ADDRESS2"));
        address.setCity(rs.getString("CITY"));
        address.setState(rs.getString("STATE"));
        address.setZipCode(rs.getInt("ZIP_CODE"));
        address.setCountry(rs.getString("COUNTRY"));
        address.setLongitude(rs.getDouble("LONGITUDE"));
        address.setLatitude(rs.getDouble("LATITUDE"));
        eventModel.setAddress(address);

        //set event content
        EventContent eventContent = new EventContent();
        eventContent.setMessage(rs.getString("MESSAGE"));
        eventContent.setTitle(rs.getString("TITLE"));
        eventContent.setType(rs.getString("TYPE"));
        eventContent.setAgeGroup(rs.getString("AGE_GROUP"));
        eventModel.setEventContent(eventContent);

        return eventModel;
    }
}
