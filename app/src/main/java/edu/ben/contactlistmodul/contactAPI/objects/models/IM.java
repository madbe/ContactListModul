package edu.ben.contactlistmodul.contactAPI.objects.models;

import android.os.Parcel;
import android.os.Parcelable;

/*Class to hold instant messenger data.*/
public class IM implements Parcelable {
    private String name;
    private String type;

    public IM(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    //Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.type);
    }

    protected IM(Parcel in) {
        this.name = in.readString();
        this.type = in.readString();
    }

    public static final Parcelable.Creator<IM> CREATOR = new Parcelable.Creator<IM>() {
        @Override
        public IM createFromParcel(Parcel source) {
            return new IM(source);
        }

        @Override
        public IM[] newArray(int size) {
            return new IM[size];
        }
    };
}
