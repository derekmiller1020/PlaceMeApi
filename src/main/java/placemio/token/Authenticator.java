package placemio.token;


import org.springframework.jdbc.core.JdbcTemplate;
import placemio.DatabaseConnection;

import java.util.List;
import java.util.Map;

public class Authenticator {

    private JdbcTemplate  db = new JdbcTemplate();
    private List<Map<String, Object>> tokens;

    public boolean checkToken(String token) throws Exception{
        db = DatabaseConnection.getDatabaseConnection();
        tokens = db.queryForList("SELECT * FROM user_token WHERE token = ? AND expire_date > NOW()", new String[]{token});
        if (tokens.size() < 1){
            return false;
        }
        return true;
    }

    public String getTokenUserId(){
        return (String)tokens.get(0).get("user_id");
    }
}
