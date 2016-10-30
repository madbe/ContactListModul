package edu.ben.contactlistmodul.contacts;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;

import edu.ben.contactlistmodul.models.Contact;

public class ContactProviderProj {


    private final Context context;
    private ArrayList<Contact> contacts = new ArrayList<>();

    public ContactProviderProj(Context context) {
        this.context = context;
        this.contacts = getAllContacts();
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

    private ArrayList<Contact> readContacts() {
        ArrayList<Contact> contacts = new ArrayList<>();
        //Set the Uri to the Contacts list Uri
        Uri contentsURI = ContactsContract.Contacts.CONTENT_URI;
        //Set the Projection string for query
        final String[] PROJECTION = new String[] {
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.Photo.DISPLAY_PHOTO,
                ContactsContract.CommonDataKinds.Email.DATA,
                ContactsContract.CommonDataKinds.Phone.NUMBER

        };
        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(contentsURI, PROJECTION, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

        if (cursor != null && cursor.moveToFirst()) {
            try {
                final int contactIdIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                final int displayNameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                final int emailIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);
                final int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                final int photoIndex = cursor.getColumnIndex(ContactsContract.Contacts.Photo.PHOTO_URI);

                long contactId;
                String displayName, photo, emails, phones;

                while (cursor.moveToNext()) {
                    contactId = cursor.getLong(contactIdIndex);
                    displayName = cursor.getString(displayNameIndex);
                    emails = cursor.getString(emailIndex);
                    phones = cursor.getString(phoneIndex);
                    photo = cursor.getString(photoIndex);
                }
            } finally {
                cursor.close();
            }
        }
        return null;
    }
    private ArrayList<Contact> getAllContacts() {
        ArrayList<Contact> contactList = new ArrayList();
        ArrayList<String> emails = new ArrayList<>();
        ArrayList<String> phones = new ArrayList<>();
        Contact contact;

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

        assert cursor != null;
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.valueOf(id));
                    Uri displayPhotoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.DISPLAY_PHOTO);

                    Cursor phoneCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id},
                            null);

                    if (phoneCursor != null && phoneCursor.moveToFirst()) {
                        do {
                            String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            phones.add(phoneNumber);
                        }while (phoneCursor.moveToNext());
                    }

                    phoneCursor.close();

                    Cursor emailCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                            new String[]{id}, null);

                    while (emailCursor != null && emailCursor.moveToFirst()) {
                        do {
                            String emailId = emailCursor.getString(emailCursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                            emails.add(emailId);
                        }while (emailCursor.moveToNext());

                    }
                    emailCursor.close();
                    contact = new Contact(displayPhotoUri.toString(),id,name,emails,phones);
                    contactList.add(contact);
                }
            }
            cursor.close();
        }
        return contactList;
    }

    public void getAllContactsFast() {
        long startnow;
        long endnow;
        ContentResolver ctx = getContentResolver();

        startnow = android.os.SystemClock.uptimeMillis();
        ArrayList arrContacts = new ArrayList();

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String selection = ContactsContract.Contacts.HAS_PHONE_NUMBER;

        Cursor cursor = ctx.query(uri, new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone._ID, ContactsContract.Contacts._ID},
                selection,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        );

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String contactNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                int phoneContactID = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
                int contactID = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                Log.d("con ", "name " + contactName + " " + " PhoeContactID " + phoneContactID + "  ContactID " + contactID);
            } while (cursor.moveToNext());

            cursor.close();

            endnow = android.os.SystemClock.uptimeMillis();
            Log.d("END", "TimeForContacts " + (endnow - startnow) + " ms");
        }
    }

}
