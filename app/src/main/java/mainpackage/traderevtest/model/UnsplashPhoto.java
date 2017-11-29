package mainpackage.traderevtest.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

/**
 * Created by deepp on 2017-11-28.
 */

public class UnsplashPhoto implements Parcelable {
    private String id;
    @SerializedName("created_at")
    private String createdAt;
    private User user;
    private Urls urls;
    private int likes;
    private int width;
    private int height;
    private String description;

    private UnsplashPhoto(Parcel in) {
        id = in.readString();
        createdAt = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
        urls = in.readParcelable(Urls.class.getClassLoader());
        likes=in.readInt();
        width=in.readInt();
        height=in.readInt();
        description=in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(createdAt);
        dest.writeParcelable(user, flags);
        dest.writeParcelable(urls, flags);
        dest.writeInt(likes);
        dest.writeInt(width);
        dest.writeInt(height);
        dest.writeString(description);
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
        return createdAt;
    }

    public User getUser() {
        return user;
    }

    public Urls getUrls() {
        return urls;
    }

    public int getLikes() {
        return likes;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getDescription() {
        return description;
    }
}
