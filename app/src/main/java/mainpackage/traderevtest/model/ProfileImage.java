package mainpackage.traderevtest.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by deepp on 2017-11-28.
 */

public class ProfileImage implements Parcelable {
    private String small;
    private String medium;
    private String large;

    private ProfileImage(Parcel in) {
        small = in.readString();
        medium = in.readString();
        large = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(small);
        dest.writeString(medium);
        dest.writeString(large);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProfileImage> CREATOR = new Creator<ProfileImage>() {
        @Override
        public ProfileImage createFromParcel(Parcel in) {
            return new ProfileImage(in);
        }

        @Override
        public ProfileImage[] newArray(int size) {
            return new ProfileImage[size];
        }
    };
}
