package placemio.dao.deletion;


import org.springframework.jdbc.core.JdbcTemplate;
import placemio.dao.DatabaseConnection;

public class DeleteEvent {

    private JdbcTemplate db = DatabaseConnection.getDatabaseConnection();

    public void deleteEvent(Integer eventId){
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
