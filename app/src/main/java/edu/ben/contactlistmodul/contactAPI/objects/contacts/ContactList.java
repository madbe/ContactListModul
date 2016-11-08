package edu.ben.contactlistmodul.contactAPI.objects.contacts;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import edu.ben.contactlistmodul.contactAPI.objects.models.Contact;

/*The ContactList class is a very basic class designed to hold an ArrayList
of instances of the Contact class below.
We've left this class very plain and ready to be expanded to suit your needs.*/

public class ContactList implements Parcelable {
    private ArrayList<Contact> contacts = new ArrayList<Contact>();

    public ContactList() {
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    public void addContact(Contact contact) {
        this.contacts.add(contact);
    }

    public void removecontact(Contact contact) {
        this.contacts.remove(contact);
    }
    //Parcelable
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.contacts);
    }

    protected ContactList(Parcel in) {
        this.contacts = new ArrayList<Contact>();
        in.readList(this.contacts, Contact.class.getClassLoader());
    }

    public static final Parcelable.Creator<ContactList> CREATOR = new Parcelable.Creator<ContactList>() {
        @Override
        public ContactList createFromParcel(Parcel source) {
            return new ContactList(source);
        }

        @Override
        public ContactList[] newArray(int size) {
            return new ContactList[size];
        }
    };
}
