package placemio.models;


public class UserModel {

    private String username;
    private String password;
    private String userId;
    private String apiKey;
    private String apiSecret;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setApiKey(String apiKey){
        this.apiKey = apiKey;
    }

    public String getApiKey(){
        return apiKey;
    }

    public void setApiSecret(String apiSecret){
        this.apiSecret = apiSecret;
    }

    public String getApiSecret(){
        return apiSecret;
    }

}
