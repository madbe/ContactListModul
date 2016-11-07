package edu.ben.contactlistmodul;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

import edu.ben.contactlistmodul.adapter.AsyncResponse;
import edu.ben.contactlistmodul.adapter.SearchContactsAdapter;
import edu.ben.contactlistmodul.contactAPI.objects.contacts.ContactList;
import edu.ben.contactlistmodul.contactAPI.objects.contacts.SearchContactsService;
import edu.ben.contactlistmodul.decoratorUtils.DividerItemDecoration;
import edu.ben.contactlistmodul.decoratorUtils.SpacesItemDecoration;

public class SearchContactsActivity extends AppCompatActivity implements AsyncResponse {

    private MaterialSearchView searchView;
    private SearchContactsAdapter mSContactsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_contacts);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.search_contacts_recycler);
        //add divider decoration to the item in the recycler
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);

        mRecyclerView.addItemDecoration(itemDecoration);
        //add space between the dividers
        SpacesItemDecoration decoration = new SpacesItemDecoration(15);
        mRecyclerView.addItemDecoration(decoration);
        //enable optimizations if all item views are of the same height and width
        mRecyclerView.setHasFixedSize(true);

        //execute the async task
        new SearchContactsService(this, this, mRecyclerView, mSContactsAdapter).execute();

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        ActionBar actionBar = getSupportActionBar();
        setSupportActionBar(toolbar);
        if (actionBar != null)
            actionBar.setTitle("Contact Search");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setVoiceSearch(true); //or false

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (mSContactsAdapter != null) {
                    mSContactsAdapter.filterSearchText(newText);
                    return true;
                } else {
                    return false;
                }
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Implement the AsyncResponse method
     * @param outputAdapter THe init adapter from the AsyncTask.
     * @param contacts
     */
    @Override
    public void processFinish(SearchContactsAdapter outputAdapter, ContactList contacts) {
        //init the local adapter with the output adapter
        mSContactsAdapter = outputAdapter;
    }
}
