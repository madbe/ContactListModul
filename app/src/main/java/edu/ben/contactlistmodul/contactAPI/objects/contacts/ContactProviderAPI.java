package edu.ben.contactlistmodul.contactAPI.objects.contacts;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import java.util.ArrayList;

import edu.ben.contactlistmodul.contactAPI.objects.models.Contact;

public class ContactProviderAPI extends ContactDetailGettersAPI {
    private Context context;
    private Cursor cursor;
    private ContentResolver contentResolver;


    ContactProviderAPI(Context context) {
        super(context);
        this.context = context;
        this.contentResolver = getContentResolver();
    }

    private ContentResolver getContentResolver() {
        return context.getContentResolver();
    }

    private Cursor getCursor() {
        return cursor;
    }

    private void setCursor(Cursor cursor) {
        this.cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null, null, null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC");
    }

    /**
     * Create a new contact list
     * Set only the id, name and photo uri
     * @return ContactList
     */
    public ContactList newContactList() {

        ContactList contacts = new ContactList();
        String id;
        //call setCursor and init the main cursor
        setCursor(cursor);

        try {
            if (getCursor() != null && getCursor().getCount() > 0) {
                while (getCursor().moveToNext()) {

                    Contact c = new Contact();

                    //get the id, display name, photo uri
                    id = getCursor().getString(getCursor().getColumnIndex(ContactsContract.Contacts._ID));
                    c.setId(id);
                    c.setDisplayName(getCursor().getString(getCursor().getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)));
                    c.setPhotoUri(getCursor().getString(getCursor().getColumnIndex(ContactsContract.Contacts.PHOTO_URI)));

                    contacts.addContact(c);
                }
            }
        } finally {
            getCursor().close();
        }

        return(contacts);
    }

    /**
     * Get the Contacts list ArrayList and fill the complex data for each Contact
     * @param contactList the ContactList Array
     * @return Full Details Contacts List
     */
    public ContactList  setFullContactsDetailsList(ContactList contactList){
        ArrayList<Contact> contacts = contactList.getContacts();
        ContactList contactsList = new ContactList();

        for (Contact contact : contacts) {
            String id = contact.getId();
            //from here get the complex data: phone, email, notes, addresses, IM addresses, organization
            contact.setPhone(this.getPhoneNumbers(id));
            contact.setEmail(this.getEmailAddresses(id));
            contact.setNotes(this.getContactNotes(id));
            contact.setAddresses(this.getContactAddresses(id));
            contact.setImAddresses(this.getIM(id));
            contact.setOrganization(this.getContactOrg(id));

            contactsList.addContact(contact);
        }

        return contactList;
    }

    //TODO: Check if is best to move this Method to the Contact class and call it from there.
    private enum ContactFieldType  {PHONE, EMAIL, NOTES, ADDRESS, IM_ADDRESS, ORGANIZATION};
    /**
     * Set and Return a Contact obj' with the required field type.
     * @param contact The Contact that we want to get is detail.
     * @param type The type of the field chose from the Static ContactFieldType param
     *             PHONE, EMAIL, NOTES, ADDRESS, IM_ADDRESS, ORGANIZATION
     * @return Contact object with the require Field.
     */
    public Contact getContactDetailsByType(Contact contact, ContactFieldType type){
        String id = contact.getId();
        //from here get the complex data:
        // phone, email, notes, addresses, IM addresses, organization
        switch (type) {
            case PHONE:
                contact.setPhone(this.getPhoneNumbers(id));
                break;
            case EMAIL:
                contact.setEmail(this.getEmailAddresses(id));
                break;
            case NOTES:
                contact.setNotes(this.getContactNotes(id));
                break;
            case ADDRESS:
                contact.setAddresses(this.getContactAddresses(id));
                break;
            case IM_ADDRESS:
                contact.setImAddresses(this.getIM(id));
                break;
            case ORGANIZATION:
                contact.setOrganization(this.getContactOrg(id));
                break;
        }
        return contact;
    }

    public Contact getContactPhones(Contact contact) {
        String id = contact.getId();
        contact.setPhone(this.getPhoneNumbers(id));
        return contact;
    }

    public Contact getContactEmails(Contact contact) {
        String id = contact.getId();
        contact.setEmail(this.getEmailAddresses(id));
        return contact;
    }

    public Contact getContactNotes(Contact contact) {
        String id = contact.getId();
        contact.setNotes(this.getContactNotes(id));
        return contact;
    }

    public Contact getContactAddresses(Contact contact) {
        String id = contact.getId();
        contact.setAddresses(this.getContactAddresses(id));
        return contact;
    }

    public Contact getContactImAddresses(Contact contact) {
        String id = contact.getId();
        contact.setImAddresses(this.getIM(id));
        return contact;
    }

    public Contact getContactOrganization(Contact contact) {
        String id = contact.getId();
        contact.setOrganization(this.getContactOrg(id));
        return contact;
    }
}
