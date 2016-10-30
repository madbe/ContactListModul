package edu.ben.contactlistmodul.contactAPI.objects.models;

/*Another getter/setter and data storage class.
The email class stores the email address and address type (work, home, etc).*/

public class Email {
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
}
