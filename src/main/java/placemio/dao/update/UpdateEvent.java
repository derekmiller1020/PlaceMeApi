package placemio.dao.update;

import org.springframework.jdbc.core.JdbcTemplate;
import placemio.dao.DatabaseConnection;
import placemio.models.EventModel;
import placemio.models.submodels.Address;
import placemio.models.submodels.EventContent;


public class UpdateEvent {
    private Address address;
    private EventContent eventContent;
    private Integer eventId;

    public void update(Integer eventId, EventModel eventModel){
        this.eventId = eventId;
        this.address = eventModel.getAddress();
        this.eventContent = eventModel.getEventContent();

        updateAddress();
        updateEventContent();
    }

    /**
     * event_address table structure
     * id int(11) NOT NULL,
     * street_address varchar(255),
     * street_address2 varchar(255),
     * city varchar(255),
     * state varchar(255),
     * zip_code int(11),
     * country varchar(255),
     * longitude float,
     * latitude float,
     * location_id int(11)
     */
    private void updateAddress(){
        try{
            final String sqlInsert = "UPDATE event_address SET street_address = ?, city = ?, state = ?, zip_code = ?, " +
                    "country = ?, longitude = ?, latitude = ? " +
                    "WHERE event_id = ?";
            JdbcTemplate db = DatabaseConnection.getDatabaseConnection();
            Object[] values = new Object[]{
                    address.getStreetAddress(), address.getCity(), address.getState(), address.getZipCode(),
                    address.getCountry(), address.getLongitude(), address.getLatitude(), eventId
            };
            db.update(sqlInsert, values);
        } catch (Exception e){
            System.out.println(e.toString());
        }

    }

    /**
     * event_content table structure
     * "id int(11) NOT NULL,
     * message varchar(255),
     * title varchar(255),
     * type varchar(255),
     * age_group varchar(255),
     * location_id int(11)
     */
    private void updateEventContent(){
        try{
            final String sqlInsert = "UPDATE event_content SET message = ?, title = ?, type = ? " +
                    "WHERE event_id = ?";
            JdbcTemplate db = DatabaseConnection.getDatabaseConnection();
            Object[] values = new Object[]{
                    eventContent.getMessage(), eventContent.getTitle(), eventContent.getType(), eventId
            };
            db.update(sqlInsert, values);
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }

}
