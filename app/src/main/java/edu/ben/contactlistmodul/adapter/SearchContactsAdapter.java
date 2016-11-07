package edu.ben.contactlistmodul.adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import edu.ben.contactlistmodul.R;
import edu.ben.contactlistmodul.contactAPI.objects.contacts.ContactList;
import edu.ben.contactlistmodul.contactAPI.objects.models.Contact;

public class SearchContactsAdapter extends
        RecyclerView.Adapter<SearchContactsAdapter.SearchContactViewHolder> implements Filterable{

    private ArrayList<Contact> contactList;
    private ArrayList<Contact> originalContactList;

    public SearchContactsAdapter(ContactList contactList) {
        this.contactList = contactList.getContacts();
    }

    @Override
    public SearchContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the Contact item layout and get a view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_list_item, parent, false);
        return new SearchContactsAdapter.SearchContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SearchContactViewHolder holder, final int position) {
        Contact contact = contactList.get(position);
        holder.contact = contact;

        String path = contact.getPhotoUri();
        Picasso.with(holder.layout.getContext()).load(path).resize(50,50).centerInside().into(holder.mContactPhoto);
        //holder.mContactPhoto.setImageBitmap(contact.getPhoto());
        holder.mContactName.setText(contact.getDisplayName());
        if (contact.getPhone() != null){
            if (contact.getPhone().size() > 0) {
                String phoneNumber = contact.getPhone().get(0).getNumber();
                holder.mContactPhoneNo.setVisibility(View.VISIBLE);
                holder.mContactPhoneNo.setText(phoneNumber);
            }
        }
        holder.mOptionDigit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                //Display Option menu
                PopupMenu popupMenu = new PopupMenu(view.getContext(),holder.mOptionDigit);
                popupMenu.inflate(R.menu.menu_option);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.menu_action_add:
                                Toast.makeText(view.getContext(),holder.contact.getDisplayName() +"",Toast.LENGTH_LONG).show();
                                break;
                            case R.id.menu_action_delete:
                                Toast.makeText(view.getContext(),holder.contact.getDisplayName() +" Deleted",Toast.LENGTH_LONG).show();
                                contactList.remove(position);
                                notifyDataSetChanged();
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        //here we add the holder layout object
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class SearchContactViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout layout;
        TextView mContactName, mContactPhoneNo, mOptionDigit;
        CircularImageView mContactPhoto;
        Contact contact;

        public SearchContactViewHolder(View view) {
            super(view);
            layout = (ConstraintLayout) view.findViewById(R.id.contact_layout);
            mContactName = (TextView) view.findViewById(R.id.tv_disply_name);
            mContactPhoneNo = (TextView) view.findViewById(R.id.tv_phone_no);
            mOptionDigit = (TextView) view.findViewById(R.id.tv_option_digit);
            mContactPhoto = (CircularImageView) view.findViewById(R.id.iv_contact_photo);
        }
    }

    /**
     * Filter the Contacts list name in the SearchView
     * @return Filter results
     */
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<Contact> results = new ArrayList<Contact>();
                //if the originalContactList is equal to null then we copy the contactList to it.
                if (originalContactList == null)
                    originalContactList  = contactList;
                //if there is a text to filter we check if the originalContactList is not null and
                //the array size is greater the 0. if so, we iterate on the array, and check if the
                //Contact name contains the charSequence to filter.
                if (constraint != null){
                    if(originalContactList != null && originalContactList.size()>0 ){
                        for ( final Contact contact :originalContactList) {
                            if (contact.getDisplayName().toLowerCase().contains(constraint.toString().toLowerCase()))
                                results.add(contact);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void publishResults(CharSequence constraint, FilterResults results) {
                contactList = (ArrayList<Contact>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    /**
     * The method get a char sequence text and use the getFilter method to filter the array list
     * of the contacts
     * @param newText the contact name to Filter
     */
    public void filterSearchText(CharSequence newText) {
        if ( TextUtils.isEmpty ( newText ) ) {
            getFilter().filter("");
        } else {
            getFilter().filter(newText);
        }
    }
}
