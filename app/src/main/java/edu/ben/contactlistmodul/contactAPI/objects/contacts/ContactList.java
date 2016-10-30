package edu.ben.contactlistmodul.contactAPI.objects.contacts;

import java.util.ArrayList;

import edu.ben.contactlistmodul.contactAPI.objects.models.Contact;

/*The ContactList class is a very basic class designed to hold an ArrayList
of instances of the Contact class below.
We've left this class very plain and ready to be expanded to suit your needs.*/

public class ContactList {
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

}
