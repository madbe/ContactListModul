package edu.ben.contactlistmodul;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import edu.ben.contactlistmodul.contactAPI.objects.contacts.ContactService;
import edu.ben.contactlistmodul.decoratorUtils.DividerItemDecoration;
import edu.ben.contactlistmodul.decoratorUtils.SpacesItemDecoration;
import edu.ben.contactlistmodul.userPermission.UserPermission;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView)findViewById(R.id.contact_layout_recycler);
        mProgressBar = (ProgressBar) findViewById(R.id.contact_progress_bar);

        //add divider decoration to the item in the recycler
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);

        mRecyclerView.addItemDecoration(itemDecoration);
        //add space between the dividers
        SpacesItemDecoration decoration = new SpacesItemDecoration(15);
        mRecyclerView.addItemDecoration(decoration);
        //enable optimizations if all item views are of the same height and width
        mRecyclerView.setHasFixedSize(true);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchContactsActivity.class);
                startActivity(intent);
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (UserPermission.checkContactsPermission(fab)) {
            loadContactList();
        } else {
            UserPermission.requestContactsPermission(MainActivity.this);
        }

    }
    private void loadContactList() {
//        new ContactLoaderService(MainActivity.this,mRecyclerView).initLoaderManger();
        new ContactService(this, mRecyclerView, mProgressBar).execute();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(UserPermission.getResultPermission(fab,"we need your permission to add the contact to the list", requestCode,grantResults)){
            loadContactList();
        } else
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
