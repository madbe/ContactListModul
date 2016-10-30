package edu.ben.contactlistmodul.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Contact implements Parcelable {

    /*private Bitmap thumb;
    private Bitmap photo;*/
    private String photoUri;
    private String id;
    private String name;
    private ArrayList<String> emails;
    private ArrayList<String> phones;

    //Constructor:
    public Contact(String photoUri, /*Bitmap thumb, Bitmap photo,*/ String id, String name, ArrayList<String> emails, ArrayList<String> phones) {
        /*this.thumb = thumb;
        this.photo = photo;*/
        this.photoUri = photoUri;
        this.id = id;
        this.name = name;
        this.emails = emails;
        this.phones = phones;
    }

    //Getters and Setters:

   /* public Bitmap getThumb() {
        return thumb;
    }

    public void setThumb(Bitmap thumb) {
        this.thumb = thumb;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }*/

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public ArrayList<String> getEmails() {
        return emails;
    }

    public void setEmails(ArrayList<String> emails) {
        this.emails = emails;
    }

    public ArrayList<String> getPhones() {
        return phones;
    }

    public void setPhones(ArrayList<String> phones) {
        this.phones = phones;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        /*dest.writeParcelable(this.thumb, flags);
        dest.writeParcelable(this.photo, flags);*/
        dest.writeString(this.photoUri);
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeStringList(this.emails);
        dest.writeStringList(this.phones);
    }

    protected Contact(Parcel in) {
        /*this.thumb = in.readParcelable(Bitmap.class.getClassLoader());
        this.photo = in.readParcelable(Bitmap.class.getClassLoader());*/
        this.photoUri = in.readString();
        this.id = in.readString();
        this.name = in.readString();
        this.emails = in.createStringArrayList();
        this.phones = in.createStringArrayList();
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel source) {
            return new Contact(source);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    @Override
    public String toString() {
        return "Contact{" +
                /*"thumb=" + thumb +
                ", photo=" + photo +*/
                ", photoUri=" + photoUri +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", emails=" + emails +
                ", phones=" + phones +
                '}';
    }
}