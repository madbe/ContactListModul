package edu.ben.contactlistmodul.contactAPI.objects.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/*The Contact class is used to store the details about each contact.
There are a series of private class variables to hold this data.
Singular data such as name and database ID are stored as strings.
Complex data is stored either as an instance or ArrayList of data specific classes.
This class is mainly getters and setters with a few methods to add to the internal ArrayLists.*/

public class Contact implements Parcelable {
    private String id;
    private String displayName;
    private String photoUri;
    //Complex Data
    private ArrayList<Phone> phone;
    private ArrayList<Email> email;
    private ArrayList<String> notes;
    private ArrayList<Address> addresses = new ArrayList<Address>();
    private ArrayList<IM> imAddresses;
    private Organization organization;

    public Contact() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public ArrayList<Phone> getPhone() {
        return phone;
    }

    public void setPhone(ArrayList<Phone> phone) {
        this.phone = phone;
    }

    public ArrayList<Email> getEmail() {
        return email;
    }

    public void setEmail(ArrayList<Email> email) {
        this.email = email;
    }

    public ArrayList<String> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<String> notes) {
        this.notes = notes;
    }

    public ArrayList<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(ArrayList<Address> addresses) {
        this.addresses = addresses;
    }

    public ArrayList<IM> getImAddresses() {
        return imAddresses;
    }

    public void setImAddresses(ArrayList<IM> imAddresses) {
        this.imAddresses = imAddresses;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    //Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.displayName);
        dest.writeString(this.photoUri);
        dest.writeList(this.phone);
        dest.writeList(this.email);
        dest.writeStringList(this.notes);
        dest.writeList(this.addresses);
        dest.writeList(this.imAddresses);
        dest.writeParcelable(this.organization, flags);
    }

    protected Contact(Parcel in) {
        this.id = in.readString();
        this.displayName = in.readString();
        this.photoUri = in.readString();
        this.phone = new ArrayList<Phone>();
        in.readList(this.phone, Phone.class.getClassLoader());
        this.email = new ArrayList<Email>();
        in.readList(this.email, Email.class.getClassLoader());
        this.notes = in.createStringArrayList();
        this.addresses = new ArrayList<Address>();
        in.readList(this.addresses, Address.class.getClassLoader());
        this.imAddresses = new ArrayList<IM>();
        in.readList(this.imAddresses, IM.class.getClassLoader());
        this.organization = in.readParcelable(Organization.class.getClassLoader());
    }

    public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel source) {
            return new Contact(source);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };
}
