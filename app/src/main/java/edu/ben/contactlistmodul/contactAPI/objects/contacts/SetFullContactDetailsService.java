package edu.ben.contactlistmodul.contactAPI.objects.contacts;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import edu.ben.contactlistmodul.adapter.ContactAdapter;

public class SetFullContactDetailsService extends AsyncTask<ContactList, Integer, ContactList> {
    private Context context;
    private RecyclerView mContactRecycler;

    public SetFullContactDetailsService(Context context, RecyclerView mContactRecycler) {
        this.mContactRecycler = mContactRecycler;
        this.context = context;
    }

    @Override
    protected ContactList doInBackground(ContactList... contactsList) {
        ContactList contacts = null;
        ContactProviderAPI cpApi = new ContactProviderAPI(context);
        contacts = cpApi.setFullContactsDetailsList(contactsList[0]);

        return contacts;
    }

    @Override
    protected void onPostExecute(ContactList contactList) {
        super.onPostExecute(contactList);

        ContactAdapter adapter = new ContactAdapter(contactList);
        mContactRecycler.setAdapter(adapter);
    }
}
