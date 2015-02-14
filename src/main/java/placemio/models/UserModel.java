package placemio.models;


public class UserModel {

    private String username;
    private String password;
    private String userId;
    private String apiToken;
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

    public void setApiToken(String apiToken){
        this.apiToken = apiToken;
    }

    public String getApiToken(){
        return apiToken;
    }

    public void setApiSecret(String apiSecret){
        this.apiSecret = apiSecret;
    }

    public String getApiSecret(){
        return apiSecret;
    }

}
