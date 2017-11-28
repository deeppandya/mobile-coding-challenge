package mainpackage.traderevtest.model;

/**
 * Created by deepp on 2017-11-28.
 */

public class UnsplashPhoto {
    private String id;
    private String created_at;
    private User user;
    private Object urls;


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

    public Object getUrls() {
        return urls;
    }

    public void setUrls(Object urls) {
        this.urls = urls;
    }
}
