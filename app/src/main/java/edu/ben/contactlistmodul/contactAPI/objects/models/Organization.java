package edu.ben.contactlistmodul.contactAPI.objects.models;

import android.os.Parcel;
import android.os.Parcelable;

/*Class to hold the contacts organizational data.*/
public class Organization implements Parcelable {
    private String organization = "";
    private String title = "";

    public Organization() {
    }

    public Organization(String organization, String title) {
        this.organization = organization;
        this.title = title;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    //Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.organization);
        dest.writeString(this.title);
    }

    protected Organization(Parcel in) {
        this.organization = in.readString();
        this.title = in.readString();
    }

    public static final Parcelable.Creator<Organization> CREATOR = new Parcelable.Creator<Organization>() {
        @Override
        public Organization createFromParcel(Parcel source) {
            return new Organization(source);
        }

        @Override
        public Organization[] newArray(int size) {
            return new Organization[size];
        }
    };
}
