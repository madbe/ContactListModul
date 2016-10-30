package edu.ben.contactlistmodul.contactAPI.objects.models;

/*Class to hold the phone records.
The phone class stores the phone number and phone type (work, home, etc)*/

public class Phone {
    private String number;
    private String type;

    public Phone(String number, String type) {
        this.number = number;
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
