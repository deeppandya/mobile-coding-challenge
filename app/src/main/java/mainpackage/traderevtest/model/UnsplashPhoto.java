package mainpackage.traderevtest.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.JsonObject;

import org.json.JSONObject;

/**
 * Created by deepp on 2017-11-28.
 */

public class UnsplashPhoto implements Parcelable {
    private String id;
    private String created_at;
    private User user;
    private Urls urls;


    private UnsplashPhoto(Parcel in) {
        id = in.readString();
        created_at = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
        urls = in.readParcelable(Urls.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(created_at);
        dest.writeParcelable(user, flags);
        dest.writeParcelable(urls, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UnsplashPhoto> CREATOR = new Creator<UnsplashPhoto>() {
        @Override
        public UnsplashPhoto createFromParcel(Parcel in) {
            return new UnsplashPhoto(in);
        }

        @Override
        public UnsplashPhoto[] newArray(int size) {
            return new UnsplashPhoto[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public User getUser() {
        return user;
    }

    public Urls getUrls() {
        return urls;
    }
}
