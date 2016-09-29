package edu.ben.contactlistmodul.contacts;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

import edu.ben.contactlistmodul.adapter.ContactAdapter;
import edu.ben.contactlistmodul.models.Contact;

public class ContactService extends AsyncTask<Void, Integer, ArrayList<Contact>> {

    private final Context context;
    private RecyclerView mContactRecycler;
    private ProgressBar mProgressBar;

    public ContactService(Context context, RecyclerView mContactRecycler, ProgressBar progressBar) {
        this.mContactRecycler = mContactRecycler;
        this.mProgressBar = progressBar;
        this.context = context;
    }

    @Override
    protected ArrayList<Contact> doInBackground(Void... params) {

        ContactsProvider provider = new ContactsProvider(context);
        /*StringBuffer sb = provider.readContacts(context);
        Log.d("Contact",sb.toString());*/
        return provider.getContacts();
    }

    @Override
    protected void onPostExecute(ArrayList<Contact> contacts) {
        super.onPostExecute(contacts);

        mProgressBar.setVisibility(View.GONE);
        ContactAdapter adapter = new ContactAdapter(contacts);
        mContactRecycler.setAdapter(adapter);

    }
}
