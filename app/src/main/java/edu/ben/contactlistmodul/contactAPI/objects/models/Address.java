package edu.ben.contactlistmodul.contactAPI.objects.models;

/*The Address class is the only class in the ContactList framework that actually does any work.
Android 2.0 has individual data columns for PO-Box, street, city, region, postal code, country.*/

import android.os.Parcel;
import android.os.Parcelable;

public class Address implements Parcelable {
    private String poBox;
    private String street;
    private String city;
    private String state;
    private String postalCode;
    private String country;
    private String type;

    public Address(String poBox, String street, String city, String state, String postalCode, String country, String type) {
        this.poBox = poBox;
        this.street = street;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
        this.type = type;
    }

    public String getPoBox() {
        return poBox;
    }

    public void setPoBox(String poBox) {
        this.poBox = poBox;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
        dest.writeString(this.poBox);
        dest.writeString(this.street);
        dest.writeString(this.city);
        dest.writeString(this.state);
        dest.writeString(this.postalCode);
        dest.writeString(this.country);
        dest.writeString(this.type);
    }

    protected Address(Parcel in) {
        this.poBox = in.readString();
        this.street = in.readString();
        this.city = in.readString();
        this.state = in.readString();
        this.postalCode = in.readString();
        this.country = in.readString();
        this.type = in.readString();
    }

    public static final Parcelable.Creator<Address> CREATOR = new Parcelable.Creator<Address>() {
        @Override
        public Address createFromParcel(Parcel source) {
            return new Address(source);
        }

        @Override
        public Address[] newArray(int size) {
            return new Address[size];
        }
    };
}
