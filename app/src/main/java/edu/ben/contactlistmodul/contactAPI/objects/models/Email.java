package edu.ben.contactlistmodul.contactAPI.objects.models;

/*Another getter/setter and data storage class.
The email class stores the email address and address type (work, home, etc).*/

import android.os.Parcel;
import android.os.Parcelable;

public class Email implements Parcelable {
    private String address;
    private String type;

    public Email(String address, String type) {
        this.address = address;
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        dest.writeString(this.address);
        dest.writeString(this.type);
    }

    protected Email(Parcel in) {
        this.address = in.readString();
        this.type = in.readString();
    }

    public static final Parcelable.Creator<Email> CREATOR = new Parcelable.Creator<Email>() {
        @Override
        public Email createFromParcel(Parcel source) {
            return new Email(source);
        }

        @Override
        public Email[] newArray(int size) {
            return new Email[size];
        }
    };
}
