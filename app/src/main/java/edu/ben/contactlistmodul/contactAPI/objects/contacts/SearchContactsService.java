package edu.ben.contactlistmodul.contactAPI.objects.contacts;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import edu.ben.contactlistmodul.adapter.AsyncResponse;
import edu.ben.contactlistmodul.adapter.SearchContactsAdapter;

public class SearchContactsService extends AsyncTask<Void, Void, ContactList> {

    private Context context;
    private RecyclerView mContactRecycler;
    private SearchContactsAdapter mSContactsAdapter;
    //declare the Interface as a field and set it to null
    public AsyncResponse delegate = null;

    public SearchContactsService(Context context, AsyncResponse delegate, RecyclerView mContactRecycler, SearchContactsAdapter mSContactsAdapter) {
        this.context = context;
        this.mContactRecycler = mContactRecycler;
        this.mSContactsAdapter = mSContactsAdapter;
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

        mSContactsAdapter = new SearchContactsAdapter(contacts);
        mContactRecycler.setAdapter(mSContactsAdapter);
        //In the onPostExecute we call the processFinish method from the interface class
        //and pass it the init adapter.
        delegate.processFinish(mSContactsAdapter, contacts);
    }
}
