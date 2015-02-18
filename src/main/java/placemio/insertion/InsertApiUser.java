package placemio.insertion;


import org.springframework.jdbc.core.JdbcTemplate;
import placemio.DatabaseConnection;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class InsertApiUser {

    private Integer userId;
    private JdbcTemplate db = DatabaseConnection.getDatabaseConnection();

    public InsertApiUser(Integer userId){
        this.userId = userId;
    }

    /**
     * id int(11) NOT NULL AUTO_INCREMENT,
     * api_key varchar(255),
     * api_secret varchar(255),
     * user_id varchar(255)
     */
    public void insert(){
        if (isFirstRegistration()){
            String api_key = UUID.randomUUID().toString();
            String api_secret = UUID.randomUUID().toString();
            String sql = "INSERT INTO user_api(api_key, api_secret, user_id) VALUES (?,?,?)";
            db.update(sql, new Object[]{api_key, api_secret, userId});
        }
    }

    public boolean isFirstRegistration(){
        String sql = "SELECT api_key FROM user_api where user_id = ?";
        List<Map<String, Object>> api = db.queryForList(sql, new Integer[]{userId});
        return api.size() < 1;
    }

    public Map<String, Object> getCredentials(){
        String sql = "SELECT api_key, api_secret FROM user_api where user_id = ?";
        return db.queryForMap(sql, new Integer[]{userId});
    }
}
