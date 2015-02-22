package placemio.insertion;


import org.springframework.jdbc.core.JdbcTemplate;
import placemio.DatabaseConnection;
import placemio.models.UserModel;
import placemio.models.submodels.Address;
import placemio.models.submodels.EventContent;

public class InsertEvent {

    private UserModel user = new UserModel();
    private Address address;
    private EventContent eventContent;
    private Integer eventId;

    public void insertContent(UserModel user, Address address, EventContent eventContent){
        this.user = user;
        this.address = address;
        this.eventContent = eventContent;

        insertEvent();
        insertAddress();
        insertEventContent();
    }

    /**
     * Event Table table structure
     * id int(11) NOT NULL,
     * user_id int(11),
     * creation_date DATE,
     * event_date DATE,
     * is_deleted int(2)
     */
    private void insertEvent(){
        final String sqlInsert = "INSERT INTO event(user_id, creation_date) VALUES (?, NOW())";
        JdbcTemplate db = DatabaseConnection.getDatabaseConnection();
        try {
            Object[] values = new Object[]{user.getUserId() };
            db.update(sqlInsert, values);
            eventId = db.queryForInt("SELECT max(ID) FROM Event"); //temporary
        } catch (Exception e){
            System.out.println(e.toString());
        }
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
    private void insertAddress(){
        try{
            final String sqlInsert = "INSERT INTO event_address (street_address, city, state, zip_code, country, longitude, latitude, event_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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
     * event_date,
     * event_time
     * location_id int(11)
     */
    private void insertEventContent(){
        try{
            final String sqlInsert = "INSERT INTO event_content (message, title, type, event_id, event_date) " +
                    "VALUES (?,?,?,?,?,?)";
            JdbcTemplate db = DatabaseConnection.getDatabaseConnection();
            Object[] values = new Object[]{
                    eventContent.getMessage(), eventContent.getTitle(), eventContent.getType(), eventId, eventContent.getDate()
            };
            db.update(sqlInsert, values);
            System.out.println("asdf");
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }

    public Integer getEventId(){
        return eventId;
    }

}
