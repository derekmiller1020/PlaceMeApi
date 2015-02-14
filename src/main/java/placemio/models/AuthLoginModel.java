package placemio.models;


import placemio.models.validation.ValidateAuth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthLoginModel {

    private String apiKey;
    private String apiSecret;
    private ValidateAuth validateAuth = new ValidateAuth();
    private Map<String, List<String>> errorMessages = new HashMap<String, List<String>>();

    public void setApiKey(String apiKey){
        this.apiKey = apiKey;
    }

    public void setApiSecret(String apiSecret){
        this.apiSecret = apiSecret;
    }

    public boolean validate(){
        return validateAuth.validCredentials(apiKey, apiSecret);
    }

    public void status(){
        if (validate()){
            validateAuth.setToken(apiKey);
        } else {

        }
    }

}
