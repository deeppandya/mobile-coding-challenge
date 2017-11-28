package mainpackage.traderevtest.model;

import com.google.gson.JsonObject;

import org.json.JSONObject;

/**
 * Created by deepp on 2017-11-28.
 */

public class UnsplashPhoto {
    private String id;
    private String created_at;
    private User user;
    private Urls urls;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(String createdAt) {
        this.created_at = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Urls getUrls() {
        return urls;
    }

    public void setUrls(Urls urls) {
        this.urls = urls;
    }
}
