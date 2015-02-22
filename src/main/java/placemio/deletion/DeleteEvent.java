package placemio.deletion;


import org.springframework.jdbc.core.JdbcTemplate;
import placemio.DatabaseConnection;

public class DeleteEvent {

    private Integer eventId;
    private JdbcTemplate db = DatabaseConnection.getDatabaseConnection();

    public DeleteEvent(Integer eventId){
        this.eventId = eventId;
    }

    public void deleteEvent(){
        final String sqlInsert = "UPDATE event SET is_deleted = 1 WHERE id = ?";
        JdbcTemplate db = DatabaseConnection.getDatabaseConnection();
        try {
            Object[] values = new Object[]{eventId};
            db.update(sqlInsert, values);
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }
}
