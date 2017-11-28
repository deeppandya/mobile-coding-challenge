package mainpackage.traderevtest.model;

import org.json.JSONObject;

/**
 * Created by deepp on 2017-11-28.
 */

public class User {
    private String id;
    private String name;
    private JSONObject profile_image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public JSONObject getProfileImage() {
        return profile_image;
    }

    public void setProfileImage(JSONObject profileImage) {
        this.profile_image = profileImage;
    }
}
