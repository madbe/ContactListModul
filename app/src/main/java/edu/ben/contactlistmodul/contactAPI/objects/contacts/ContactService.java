package edu.ben.contactlistmodul.contactAPI.objects.contacts;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import edu.ben.contactlistmodul.adapter.AsyncResponse;
import edu.ben.contactlistmodul.adapter.ContactAdapter;

public class ContactService extends AsyncTask<Void, Integer, ContactList> {

    private final Context context;
    private RecyclerView mContactRecycler;
    private ProgressBar mProgressBar;
    //declare the Interface as a field and set it to null
    public AsyncResponse delegate = null;

    public ContactService(Context context, AsyncResponse delegate, RecyclerView mContactRecycler, ProgressBar progressBar) {
        this.mContactRecycler = mContactRecycler;
        this.mProgressBar = progressBar;
        this.context = context;
        this.delegate = delegate;
    }

    @Override
    protected ContactList doInBackground(Void... params) {
        ContactList contacts = null;
        ContactProviderAPI cpApi = new ContactProviderAPI(context);
        contacts = cpApi.newContactList();

        return contacts;
    }

    @Override
    protected void onPostExecute(ContactList contacts) {
        super.onPostExecute(contacts);

        mProgressBar.setVisibility(View.GONE);
        ContactAdapter adapter = new ContactAdapter(contacts);
        mContactRecycler.setAdapter(adapter);
        delegate.processFinish(contacts);
        new SetFullContactDetailsService(context, mContactRecycler).execute(contacts);

    }
}