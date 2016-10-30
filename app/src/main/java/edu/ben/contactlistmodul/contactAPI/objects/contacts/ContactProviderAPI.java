package edu.ben.contactlistmodul.contactAPI.objects.contacts;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;

import edu.ben.contactlistmodul.contactAPI.objects.models.Address;
import edu.ben.contactlistmodul.contactAPI.objects.models.Contact;
import edu.ben.contactlistmodul.contactAPI.objects.models.Email;
import edu.ben.contactlistmodul.contactAPI.objects.models.IM;
import edu.ben.contactlistmodul.contactAPI.objects.models.Organization;
import edu.ben.contactlistmodul.contactAPI.objects.models.Phone;

public class ContactProviderAPI {
    private Context context;
    private Cursor cursor;
    private ContentResolver contentResolver;


    public ContactProviderAPI(Context context) {
        this.context = context;
        this.contentResolver = getContentResolver();
    }

    private ContentResolver getContentResolver() {
        return context.getContentResolver();
    }

   /* public Cursor getCursor() {
        return cursor;
    }

    public void setCursor(Cursor cursor) {
        this.cursor = cursor;
    }*/

    /**
     * create a new contact list
     * @return
     */
    public ContactList newContactList() {

        ContactList contacts = new ContactList();
        String id;
        String sortOrder = "ContactsContract.Contacts.DISPLAY_NAME  ASC";

        cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null, null, null,
                sortOrder);

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                Contact c = new Contact();

                //get the id, display name, photo uri
                id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                c.setId(id);
                c.setDisplayName(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
                c.setPhotoUri(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI)));

                //from here get the complex data: phone, email, notes, addresses, IM addresses, organization
                if (Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    c.setPhone(this.getPhoneNumbers(id));
                }

                c.setEmail(this.getEmailAddresses(id));
                c.setNotes(this.getContactNotes(id));
                c.setAddresses(this.getContactAddresses(id));
                c.setImAddresses(this.getIM(id));
                c.setOrganization(this.getContactOrg(id));

                contacts.addContact(c);
            }
            cursor.close();
        }

        return(contacts);
    }

    private ArrayList<Phone> getPhoneNumbers(String id) {
        ArrayList<Phone> phones = new ArrayList<Phone>();

        Cursor pCur = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                new String[]{id}, null);

        if (pCur != null) {
            while (pCur.moveToNext()) {
                phones.add(new Phone(
                        pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                        , pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE))
                ));

            }
            pCur.close();
        }

        return(phones);
    }

    private ArrayList<Email> getEmailAddresses(String id) {
        ArrayList<Email> emails = new ArrayList<Email>();

        Cursor emailCur = contentResolver.query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                new String[]{id}, null);
        if (emailCur != null) {
            while (emailCur.moveToNext()) {
                // This would allow you get several email addresses
                Email e = new Email(emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA))
                        ,emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE))
                );
                emails.add(e);
            }
            emailCur.close();
        }

        return(emails);
    }

    private ArrayList<String> getContactNotes(String id) {
        ArrayList<String> notes = new ArrayList<String>();
        String where = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
        String[] whereParameters = new String[]{id,
                ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE};
        Cursor noteCur = contentResolver.query(ContactsContract.Data.CONTENT_URI, null, where, whereParameters, null);
        if (noteCur != null && noteCur.moveToFirst()) {
            String note = noteCur.getString(noteCur.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE));
            if (note.length() > 0) {
                notes.add(note);
            }

            noteCur.close();
        }

        return(notes);
    }

    private ArrayList<Address> getContactAddresses(String id) {
        ArrayList<Address> addrList = new ArrayList<Address>();

        String where = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
        String[] whereParameters = new String[]{id,
                ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE};

        Cursor addrCur = contentResolver.query(ContactsContract.Data.CONTENT_URI, null, where, whereParameters, null);

        if (addrCur != null) {
            while (addrCur.moveToNext()) {
                String poBox = addrCur.getString(addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX));
                String street = addrCur.getString(addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
                String city = addrCur.getString(addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
                String state = addrCur.getString(addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
                String postalCode = addrCur.getString(addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
                String country = addrCur.getString(addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
                String type = addrCur.getString(addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE));
                Address a = new Address(poBox, street, city, state, postalCode, country, type);
                addrList.add(a);
            }
            addrCur.close();
        }
        return(addrList);
    }

    private ArrayList<IM> getIM(String id) {
        ArrayList<IM> imList = new ArrayList<IM>();
        String where = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
        String[] whereParameters = new String[]{id,
                ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE};

        Cursor imCur = contentResolver.query(ContactsContract.Data.CONTENT_URI, null, where, whereParameters, null);

        if (imCur != null && imCur.moveToFirst()) {
            String imName = imCur.getString(imCur.getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA));
            String imType;
            imType = imCur.getString(imCur.getColumnIndex(ContactsContract.CommonDataKinds.Im.TYPE));
            if (imName.length() > 0) {
                IM im = new IM(imName, imType);
                imList.add(im);
            }
            imCur.close();
        }

        return(imList);
    }

    private Organization getContactOrg(String id) {
        Organization org = new Organization();
        String where = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
        String[] whereParameters = new String[]{id,
                ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE};

        Cursor orgCur = contentResolver.query(ContactsContract.Data.CONTENT_URI, null, where, whereParameters, null);

        if (orgCur != null && orgCur.moveToFirst()) {
            String orgName = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DATA));
            String title = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));
            if (orgName.length() > 0) {
                org.setOrganization(orgName);
                org.setTitle(title);
            }
            orgCur.close();
        }

        return(org);
    }
}
