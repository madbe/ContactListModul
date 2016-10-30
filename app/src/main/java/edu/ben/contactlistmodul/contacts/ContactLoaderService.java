package edu.ben.contactlistmodul.contacts;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import edu.ben.contactlistmodul.adapter.ContactAdapter;
import edu.ben.contactlistmodul.models.Contact;
import edu.ben.contactlistmodul.models.SelectUser;


public class ContactLoaderService implements LoaderManager.LoaderCallbacks<Cursor>{
    // Identifies a particular Loader being used in this component
    private static final int URL_LOADER = 0;
    private Uri mDataUrl;
    private String[] mProjection;
    private Activity activity;
    private Context context;
    private RecyclerView mContactRecycler;
    private  ContactAdapter mAdapter;
    private ArrayList<SelectUser> selectUsers;
    private ArrayList<Contact> contacts;

    public ContactLoaderService(Activity activity, RecyclerView mContactRecycler) {
        this.activity = activity;
        this.context = activity.getApplicationContext();
        this.mContactRecycler = mContactRecycler;
        this.mDataUrl = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        this.mProjection =  new String[] {
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Email.DATA2,
                ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI
        };
    }
    public void initLoaderManger() {
        /*
         * Initializes the CursorLoader. The URL_LOADER value is eventually passed
         * to onCreateLoader().
         */
         activity.getLoaderManager().initLoader(URL_LOADER, null, this);
    }
    private ContentResolver getContentResolver() {
        return context.getContentResolver();
    }
    @Override
    public Loader<Cursor> onCreateLoader(int loaderID, Bundle args) {
       /*
     * Takes action based on the ID of the Loader that's being created
     */
        switch (loaderID) {
            case URL_LOADER:
                // Returns a new CursorLoader
                return new CursorLoader(
                        activity,   // Parent activity context
                        mDataUrl,        // Table to query
                        mProjection,     // Projection to return
                        null,            // No selection clause
                        null,            // No selection arguments
                        null             // Default sort order
                );
            default:
                // An invalid id was passed in
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ArrayList<String> phones = new ArrayList<>(), emails = new ArrayList<>();

        // Get Contact list from Phone
        if (data != null) {
            Log.e("count", "" + data.getCount());
            if (data.getCount() == 0) {
                Toast.makeText(activity, "No contacts in your contact list.", Toast.LENGTH_LONG).show();
            }

            while (data.moveToNext()) {
                Bitmap bit_thumb = null;
                String id = data.getString(data.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
                String name = data.getString(data.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = data.getString(data.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String EmailAddr = data.getString(data.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA2));
                String image_thumb = data.getString(data.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));
                try {
                    if (image_thumb != null) {
                        bit_thumb = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(image_thumb));
                    } else {
                        Log.e("No Image Thumb", "--------------");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

               /* SelectUser selectUser = new SelectUser();
                selectUser.setThumb(bit_thumb);
                selectUser.setName(name);
                selectUser.setPhone(phoneNumber);
                selectUser.setEmail(id);
                selectUser.setCheckedBox(false);
                selectUsers.add(selectUser);*/

                phones.add(phoneNumber);
                emails.add("test@email.test");
                Contact contact = new Contact(image_thumb, id, name, emails, phones);
                contacts.add(contact);
            }
        } else {
            Log.e("Cursor close 1", "----------------");
        }
        //phones.close();
       /* mAdapter = new ContactAdapter(contacts);
        mContactRecycler.setAdapter(mAdapter);*/
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
//        mAdapter.notifyDataSetChanged();
    }
}
