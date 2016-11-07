package edu.ben.contactlistmodul.contactAPI.objects.contacts;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;

import edu.ben.contactlistmodul.contactAPI.objects.models.Address;
import edu.ben.contactlistmodul.contactAPI.objects.models.Email;
import edu.ben.contactlistmodul.contactAPI.objects.models.IM;
import edu.ben.contactlistmodul.contactAPI.objects.models.Organization;
import edu.ben.contactlistmodul.contactAPI.objects.models.Phone;


class ContactDetailGettersAPI {

    private Context context;
    private ContentResolver contentResolver;

    ContactDetailGettersAPI(Context context) {
        this.context = context;
        this.contentResolver = getContentResolver();
    }

    private ContentResolver getContentResolver() {
        return context.getContentResolver();
    }

    /**
     * get the contact phone numbers
     * @param id Contact id
     * @return ArrayList<Phone> Contact Phone numbers
     */
    ArrayList<Phone> getPhoneNumbers(String id) {
        ArrayList<Phone> phones = new ArrayList<Phone>();

        Cursor pCur = contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                new String[]{id}, null);

        try {
            if (pCur != null /*&& Integer.parseInt(pCur.getString(pCur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0*/) {
                while (pCur.moveToNext()) {
                    phones.add(new Phone(
                            pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                            , pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE))
                    ));
                }
            }
        } finally {
            if (pCur != null) {
                pCur.close();
            }
        }

        return(phones);
    }

    /**
     * Get the contact email address
     * @param id Contact id
     * @return ArrayList<Email> Contact Email's
     */
    ArrayList<Email> getEmailAddresses(String id) {
        ArrayList<Email> emails = new ArrayList<Email>();

        Cursor emailCur = contentResolver.query(
                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                new String[]{id}, null);

        try {
            if (emailCur != null) {
                while (emailCur.moveToNext()) {
                    // This would allow you get several email addresses
                    Email e = new Email(emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA))
                            ,emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE))
                    );
                    emails.add(e);
                }
            }
        } finally {
            if (emailCur != null) {
                emailCur.close();
            }
        }

        return(emails);
    }

    /**
     * Get the Contact Note's
     * @param id Contact id
     * @return ArrayList of Note's
     */
    ArrayList<String> getContactNotes(String id) {
        ArrayList<String> notes = new ArrayList<String>();
        String where = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
        String[] whereParameters = new String[]{id,
                ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE};

        Cursor noteCur = contentResolver.query(
                ContactsContract.Data.CONTENT_URI,
                null,
                where,
                whereParameters,
                null);

        try {
            if (noteCur != null && noteCur.moveToFirst()) {
                String note = noteCur.getString(noteCur.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE));
                if (note.length() > 0) {
                    notes.add(note);
                }
            }
        } finally {
            if (noteCur != null) {
                noteCur.close();
            }
        }

        return(notes);
    }

    /**
     * Get The Contact Addresses
     * @param id Contact id
     * @return ArrayList of the Contact Addresses
     */
    ArrayList<Address> getContactAddresses(String id) {
        ArrayList<Address> addrList = new ArrayList<Address>();

        String where = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
        String[] whereParameters = new String[]{id,
                ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE};

        Cursor addrCur = contentResolver.query(ContactsContract.Data.CONTENT_URI, null, where, whereParameters, null);
        try {
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

            }
        } finally {
            if (addrCur != null) {
                addrCur.close();
            }
        }

        return(addrList);
    }

    /**
     * Get the Contact IM
     * @param id Contact id
     * @return ArrayList of the Contact IM's
     */
    ArrayList<IM> getIM(String id) {
        ArrayList<IM> imList = new ArrayList<IM>();
        String where = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
        String[] whereParameters = new String[]{id,
                ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE};

        Cursor imCur = contentResolver.query(ContactsContract.Data.CONTENT_URI, null, where, whereParameters, null);

        try {
            if (imCur != null && imCur.moveToFirst()) {
                String imName = imCur.getString(imCur.getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA));
                String imType;
                imType = imCur.getString(imCur.getColumnIndex(ContactsContract.CommonDataKinds.Im.TYPE));
                if (imName.length() > 0) {
                    IM im = new IM(imName, imType);
                    imList.add(im);
                }

            }
        } finally {
            if (imCur != null) {
                imCur.close();
            }
        }

        return(imList);
    }

    /**
     * Get the Contact Organization
     * @param id Contact id
     * @return Contact Organization
     */
    Organization getContactOrg(String id) {
        Organization org = new Organization();
        String where = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
        String[] whereParameters = new String[]{id,
                ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE};

        Cursor orgCur = contentResolver.query(ContactsContract.Data.CONTENT_URI, null, where, whereParameters, null);

        try {
            if (orgCur != null && orgCur.moveToFirst()) {
                String orgName = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DATA));
                String title = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));
                if (orgName.length() > 0) {
                    org.setOrganization(orgName);
                    org.setTitle(title);
                }

            }
        } finally {
            if (orgCur != null) {
                orgCur.close();
            }
        }

        return(org);
    }
}
