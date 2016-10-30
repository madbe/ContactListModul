package edu.ben.contactlistmodul.contacts;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

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
        ArrayList<Contact> contacts = null;
        ContactsProvider provider = new ContactsProvider(context);
        ContactProviderProj providerProj = new ContactProviderProj(context);
        providerProj.getAllContactsFast();
        //ArrayList<Contact> allContacts = providerProj.getContacts();

        provider.contactsRead();
        contacts = provider.readContacts(context);
        /*Log.d("Contact",sb.toString());*/
        //contacts = provider.getContacts();
        return contacts;
    }

    @Override
    protected void onPostExecute(ArrayList<Contact> contacts) {
        super.onPostExecute(contacts);

        mProgressBar.setVisibility(View.GONE);
//        ContactAdapter adapter = new ContactAdapter(contacts);
//        mContactRecycler.setAdapter(adapter);

    }
}
