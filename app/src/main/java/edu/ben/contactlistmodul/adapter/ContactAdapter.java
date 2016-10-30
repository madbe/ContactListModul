package edu.ben.contactlistmodul.adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import edu.ben.contactlistmodul.R;
import edu.ben.contactlistmodul.contactAPI.objects.contacts.ContactList;
import edu.ben.contactlistmodul.contactAPI.objects.models.Contact;
import edu.ben.contactlistmodul.contactAPI.objects.models.Phone;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private final ArrayList<Contact> contactList;

    public ContactAdapter(ContactList contactList) {
        this.contactList = contactList.getContacts();
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflate the Contact item layout and get a view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_list_item, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {

        Contact contact = contactList.get(position);
        holder.contact = contact;

        String path = contact.getPhotoUri();
        Picasso.with(holder.layout.getContext()).load(path).resize(50,50).centerInside().into(holder.mContactPhoto);
        //holder.mContactPhoto.setImageBitmap(contact.getPhoto());
        holder.mContactName.setText(contact.getDisplayName());
        ArrayList<Phone> phones = contact.getPhone();
        //holder.mContactPhoneNo.setText(phones.get(0));
        //here we add the holder layout object
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout layout;
        TextView mContactName, mContactPhoneNo;
        ImageView mContactPhoto;
        Contact contact;

        public ContactViewHolder(View view) {
            super(view);
            layout = (ConstraintLayout) view.findViewById(R.id.contact_layout);
            mContactName = (TextView) view.findViewById(R.id.tv_disply_name);
            mContactPhoneNo = (TextView) view.findViewById(R.id.tv_phone_no);
            mContactPhoto = (ImageView) view.findViewById(R.id.iv_contact_photo);

        }
    }
}
