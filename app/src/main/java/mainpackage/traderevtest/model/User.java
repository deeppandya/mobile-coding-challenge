package mainpackage.traderevtest.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by deepp on 2017-11-28.
 */

public class User {
    private String id;
    private String name;
    @SerializedName("profile_image")
    private ProfileImage profileImage;

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

    public ProfileImage getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(ProfileImage profileImage) {
        this.profileImage = profileImage;
    }
}
