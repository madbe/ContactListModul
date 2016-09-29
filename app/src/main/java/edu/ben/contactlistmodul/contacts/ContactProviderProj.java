package edu.ben.contactlistmodul.contacts;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.ContactsContract;

import java.util.ArrayList;

import edu.ben.contactlistmodul.models.Contact;

/**
 * Created by Ben on 29-Sep-16.
 */

public class ContactProviderProj {

    private static final String[] PROJECTION = new String[] {
            ContactsContract.CommonDataKinds.Email.CONTACT_ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Email.DATA,
            ContactsContract.Contacts.Photo.DISPLAY_PHOTO
    };
    private final Context context;
    private ArrayList<Contact> contacts = new ArrayList<>();

    public ContactProviderProj(Context context) {
        this.context = context;
//        this.contacts = readContacts();
    }

    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(ArrayList<Contact> contacts) {
        this.contacts = contacts;
    }

    private ContentResolver getContentResolver() {
        return context.getContentResolver();
    }

    /*private ArrayList<Contact> readContacts() {
        ArrayList<Contact> contacts = new ArrayList<>();

        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, PROJECTION, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

        if (cursor != null) {
            try {
                final int contactIdIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.CONTACT_ID);
                final int displayNameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                final int emailIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);
                long contactId;
                String displayName, address;
                while (cursor.moveToNext()) {
                    contactId = cursor.getLong(contactIdIndex);
                    displayName = cursor.getString(displayNameIndex);
                    address = cursor.getString(emailIndex);
//                    ...
                }
            } finally {
                cursor.close();
            }
        }
    }*/



}
