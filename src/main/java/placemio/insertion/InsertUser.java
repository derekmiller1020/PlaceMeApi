package placemio.insertion;


import org.springframework.jdbc.core.JdbcTemplate;
import placemio.DatabaseConnection;

public class InsertUser {

    private String username;
    private String password;

    public InsertUser(String username, String password){
        this.username = username;
        this.password = password;
    }

    public void insert(){
        String sql = "INSERT INTO users(username, password) VALUES (?,?)";
        JdbcTemplate db = DatabaseConnection.getDatabaseConnection();
        db.update(sql, new String[]{username, password});
    }
}
