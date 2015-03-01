package placemio.models.validation;


import org.springframework.jdbc.core.JdbcTemplate;
import placemio.dao.DatabaseConnection;
import java.util.List;
import java.util.Map;

public class ValidateRegistration {

    private String username;
    private String password;

    public ValidateRegistration(String username, String password){
        this.username = username;
        this.password = password;
    }

    public boolean validate(){
        if (username == null || password == null || username.equals("") || password.equals("")){
            return false;
        }
        JdbcTemplate db = DatabaseConnection.getDatabaseConnection();
        List<Map<String, Object>> users = db.queryForList("SELECT username, password FROM users WHERE username = ?", new String[]{username});
        return users.size() == 0;
    }
}
