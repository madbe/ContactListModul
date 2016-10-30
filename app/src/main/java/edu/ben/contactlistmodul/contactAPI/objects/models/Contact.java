package edu.ben.contactlistmodul.contactAPI.objects.models;

import java.util.ArrayList;

/*The Contact class is used to store the details about each contact.
There are a series of private class variables to hold this data.
Singular data such as name and database ID are stored as strings.
Complex data is stored either as an instance or ArrayList of data specific classes.
This class is mainly getters and setters with a few methods to add to the internal ArrayLists.*/

public class Contact {
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
}
