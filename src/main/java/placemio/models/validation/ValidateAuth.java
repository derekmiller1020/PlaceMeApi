package placemio.models.validation;


import org.springframework.jdbc.core.JdbcTemplate;
import placemio.dao.DatabaseConnection;

import java.util.*;

public class ValidateAuth {

    private JdbcTemplate db = DatabaseConnection.getDatabaseConnection();
    private Integer userId;
    private String token;

    public boolean validCredentials(String apiKey, String apiSecret) {
        if (apiKey == null || apiKey.equals("") || apiSecret == null || apiSecret.equals("")){
            return false;
        }
        String query = "Select id, api_key, api_secret from user_api where api_key = ? and api_secret = ?";
        String[] usernamePassword = new String[]{apiKey, apiSecret};
        List<Map<String, Object>> results = db.queryForList(query, usernamePassword);

        if (results.size() > 0){
            userId = (Integer)results.get(0).get("id");
        }
        return results.size() > 0;
    }

    /**
     * token db information
     * id int(11) NOT NULL AUTO_INCREMENT,
     * username varchar(255),
     * token varchar(255),
     * expire_date DATE
     */
    public void setToken(String apiKey){
        token = UUID.randomUUID().toString();
        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); // Now use today date.
        c.add(Calendar.DATE, 2);

        String query = "INSERT INTO user_token(user_id, api_id, token, expire_date) VALUES (?, ?, ?, ?)";
        Object[] values = new Object[]{userId, apiKey, token, c.getTime()};
        db.update(query, values);

    }

    public String getToken(){
        return token;
    }

    public Integer getUserId(){
        return userId;
    }

}
