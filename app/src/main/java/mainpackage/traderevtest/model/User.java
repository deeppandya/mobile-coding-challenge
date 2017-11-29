package mainpackage.traderevtest.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

/**
 * Created by deepp on 2017-11-28.
 */

public class User implements Parcelable {
    private String id;
    private String name;
    @SerializedName("profile_image")
    private ProfileImage profileImage;

    private User(Parcel in) {
        id = in.readString();
        name = in.readString();
        profileImage = in.readParcelable(ProfileImage.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeParcelable(profileImage, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public ProfileImage getProfileImage() {
        return profileImage;
    }
}
