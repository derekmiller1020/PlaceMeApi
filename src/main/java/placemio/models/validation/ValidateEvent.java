package placemio.models.validation;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import placemio.models.submodels.Address;
import placemio.models.submodels.EventContent;
import placemio.token.Authenticator;

import javax.servlet.http.Cookie;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidateEvent {

    private final String url = "http://maps.googleapis.com/maps/api/geocode/json?address=";
    private List<String> errorMessages = new ArrayList<String>();
    private Map<String, Object> coordinates = new HashMap<String, Object>();
    private Authenticator authenticator = new Authenticator();
    private String userId = "";

    public void validateAddress(Address address){
        if (address == null){
            errorMessages.add("There was no address information provided");
        } else {
            if (!containsContent(address.getStreetAddress())){
                errorMessages.add("street Address is empty");
            }
            if (!containsContent(address.getCity())){
                errorMessages.add("City Field is Empty");
            }
            if (!containsContent(address.getState())){
                errorMessages.add("State field is missing");
            }
            if (address.getZipCode() == null){
                errorMessages.add("Zip Code field is missing");
            }
            setLongitudeLatitude(address);
        }
    }

    public void validateEventContent(EventContent eventContent){
        if (eventContent == null){
            errorMessages.add("no Location content provided");
        } else {
            if (!containsContent(eventContent.getMessage())){
                errorMessages.add("Please add a message to the location");
            }
            if (!containsContent(eventContent.getType())){
                errorMessages.add("Please add a location type");
            }
            if (!containsContent(eventContent.getTitle())){
                errorMessages.add("Please add a title to the location");
            }
        }
    }

    public List<String> getErrorMessages(){
        return errorMessages;
    }

    private void setLongitudeLatitude(Address address){

        try {
            String addr = address.getStreetAddress() + " " + address.getCity() + ", " + address.getState()+ " " + String.valueOf(address.getZipCode());
            InputStream input = new URL(url + URLEncoder.encode(addr, "UTF-8")).openStream();
            Map<String, Object> map = new Gson().fromJson(new InputStreamReader(input, "UTF-8"), new TypeToken<Map<String, Object>>() {}.getType());
            List results = (ArrayList) map.get("results");

            if (results != null && !results.isEmpty()){
                Map result = (Map)((Map)((Map)results.get(0)).get("geometry")).get("location");
                coordinates.put("longitude", result.get("lng"));
                coordinates.put("latitude", result.get("lat"));
            } else {
                errorMessages.add("Please enter a valid address");
            }

        } catch (Exception e){
            errorMessages.add("There was an error validating your address");
        }
    }

    public Map<String, Object> getLongitudeLatitude(){
        return coordinates;
    }

    public String getUserId(){
        return userId;
    }

    private boolean containsContent(Object content){
        if (content == null){
            return false;
        } else if (content instanceof String && content.equals("")){
            return false;
        }
        return true;
    }

}