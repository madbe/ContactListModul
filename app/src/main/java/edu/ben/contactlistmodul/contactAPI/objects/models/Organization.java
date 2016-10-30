package edu.ben.contactlistmodul.contactAPI.objects.models;

/*Class to hold the contacts organizational data.*/
public class Organization {
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
}
