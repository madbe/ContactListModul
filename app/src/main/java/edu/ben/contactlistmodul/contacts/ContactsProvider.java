package edu.ben.contactlistmodul.contacts;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import edu.ben.contactlistmodul.models.Contact;

import static android.os.SystemClock.elapsedRealtime;

public class ContactsProvider {

    private final Context context;
    private ArrayList<Contact> contacts = new ArrayList<>();

    public ContactsProvider(Context context) {
        this.context = context;
        this.contacts = readContacts();
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


    /**
     * read the Contacts list of the user phone
     * the Method requires permission: android.permission.READ_CONTACTS
     * and run time permission
     *
     * @return
     */
    private ArrayList<Contact> readContacts() {
        long timeStamp = elapsedRealtime();
        Log.d("Line 56 Method entry", String.valueOf(timeStamp));
        ArrayList<Contact> contacts = new ArrayList<>();
        //Set the Uri to the Contacts list Uri
        Uri contentUri = ContactsContract.Contacts.CONTENT_URI;
        //query the uri and getting a cursor to contacts table
        Cursor cursor = getContentResolver().query(contentUri, null, null, null,ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        //checking if the cursor isn't null and move the cursor to the
        //beginning of the table


        if (cursor != null && cursor.moveToFirst()) {
            Log.d("Line 64 entry whileLoop", String.valueOf((elapsedRealtime() - timeStamp)));
            timeStamp = elapsedRealtime();
            Log.e("Contact count", "" + cursor.getCount());

            do {

                //get to contact id and name
                String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));

                String contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                //with the id calling the getPhones and getEmails methods to get the
                //contact phones and emails

                Bitmap photo = getPhoto(contactId);

                Bitmap thumb = getThumb(contactId);

                Log.d("Line 83 middelOfwhile", String.valueOf((elapsedRealtime() - timeStamp)));
                timeStamp = elapsedRealtime();

                String photoUri = getPhotoUri(contactId);

                ArrayList<String> phones = getPhones(contactId);

                ArrayList<String> emails = getEmails(contactId);


                Contact contact = new Contact(photoUri, thumb, photo, contactId, contactName, emails, phones);
                contacts.add(contact);
                Log.d("Line 95 endOfWhile", String.valueOf((elapsedRealtime() - timeStamp)));
                timeStamp = elapsedRealtime();
                //Log.d("ContactUri",contact.toString());
            } while (cursor.moveToNext());
            Log.d("Line 96 endOfMethod", String.valueOf((elapsedRealtime() - timeStamp)));
            cursor.close();
        }

        return contacts;
    }


    /**
     * get the contact phone
     *
     * @param id Contact id
     * @return ArrayList of the Contact emails (can be 1 or more)
     */
    private ArrayList<String> getPhones(String id) {
        ArrayList<String> phones = new ArrayList<>();
        Uri phoneUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = getContactCursor(phoneUri, id, ContactsContract.CommonDataKinds.Phone.CONTACT_ID);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                phones.add(phoneNumber);
            } while (cursor.moveToNext());
        }

        if (cursor != null)
            cursor.close();

        return phones;
    }


    /**
     * get the Contact email
     *
     * @param id Contact id
     * @return ArrayList of the Contact emails (can be 1 or more)
     */
    private ArrayList<String> getEmails(String id) {
        ArrayList<String> emails = new ArrayList<>();
        Uri emailUri = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        Cursor cursor = getContactCursor(emailUri, id, ContactsContract.CommonDataKinds.Email.CONTACT_ID);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String email = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                emails.add(email);
            } while (cursor.moveToNext());
        }

        if (cursor != null)
            cursor.close();

        return emails;
    }

    /**
     * get the user Large Photo
     * @param id Contact id
     * @return bitmap Contact Photo
     */
    private Bitmap getPhoto(String id) {

        Bitmap contactPhoto = null;

        try {
            InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(getContentResolver(),
                    ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.valueOf(id)), true);

            if (inputStream != null) {
                contactPhoto = BitmapFactory.decodeStream(inputStream);
            }

            if (inputStream != null)
                inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return contactPhoto;
    }

    /**
     * get the Contact thumbnail
     * @param id Contact id
     * @return Bitmap Contact thumbnail
     */
    private Bitmap getThumb(String id) {

        Bitmap contactThumb = null;

        try {
            InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(getContentResolver(),
                    ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.valueOf(id)));

            if (inputStream != null) {
                contactThumb = BitmapFactory.decodeStream(inputStream);
            }

            if (inputStream != null)
                inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return contactThumb;
    }

    /**
     * get the the contact id and then search for is phone number, emails ...
     * dependent on the searchStringUri we pass.
     *
     * @param uri             the table query uri
     * @param id              contact id
     * @param searchStringUri search string of what we wont to get phone, email, etc.
     * @return cursor to the query result
     */
    private Cursor getContactCursor(Uri uri, String id, String searchStringUri) {

        return getContentResolver().query(
                uri,
                null,
                searchStringUri + "=?",
                new String[]{id},
                null
        );
    }


    /**
     * get the user Photo Uri (enable to use with picasso)
     * @param id Contact id
     * @return Uri String
     */
    private String getPhotoUri(String id) {

        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, Long.valueOf(id));
        Uri displayPhotoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.DISPLAY_PHOTO);

        return displayPhotoUri.toString();

    }

    //call this method you get all contact information ....
    public StringBuffer readContacts(Context context) {
        StringBuffer sb = new StringBuffer();
        sb.append("......Contact Details.....");
        ContentResolver cr = context.getContentResolver();

        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        String phone = null;
        String emailContact = null;
        String emailType = null;
        String image_uri = "";
        Bitmap bitmap = null;

        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur
                        .getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur
                        .getString(cur
                                .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                image_uri = cur
                        .getString(cur
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI));
                if (Integer
                        .parseInt(cur.getString(cur
                                .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    System.out.println("name : " + name + ", ID : " + id);
                    sb.append("\n Contact Name:" + name);
                    Cursor pCur = cr.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                    + " = ?", new String[] { id }, null);
                    while (pCur.moveToNext()) {
                        phone = pCur
                                .getString(pCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        sb.append("\n Phone number:" + phone);
                        System.out.println("phone" + phone);
                    }
                    pCur.close();

                    Cursor emailCur = cr.query(
                            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID
                                    + " = ?", new String[] { id }, null);
                    while (emailCur.moveToNext()) {
                        emailContact = emailCur
                                .getString(emailCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        emailType = emailCur
                                .getString(emailCur
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
                        sb.append("\nEmail:" + emailContact + "Email type:" + emailType);
                        System.out.println("Email " + emailContact
                                + " Email Type : " + emailType);

                    }

                    emailCur.close();
                }

                if (image_uri != null) {
                    System.out.println(Uri.parse(image_uri));
                    try {
                        bitmap = MediaStore.Images.Media
                                .getBitmap(context.getContentResolver(),
                                        Uri.parse(image_uri));
                        sb.append("\n Image in Bitmap:" + bitmap);
                        System.out.println(bitmap);

                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
                sb.append("\n........................................");

            }
        }
        return sb;
    }
}