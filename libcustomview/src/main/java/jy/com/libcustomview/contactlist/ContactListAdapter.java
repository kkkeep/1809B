package jy.com.libcustomview.contactlist;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import jy.com.libcustomview.R;

/*
 * created by taofu on 2019-05-14
 **/
public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> {


    private ArrayList<Contact> mContacts;

    public ContactListAdapter(ArrayList<Contact> contacts) {
        this.mContacts = contacts;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ContactViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_contact_list,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder contactViewHolder, int i) {
        contactViewHolder.bindData(mContacts.get(i));
    }

    @Override
    public int getItemCount() {
        return mContacts == null ? 0 : mContacts.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder{
        private TextView mContent;

        private static final String FORMAT = "%s";
        ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            mContent = itemView.findViewById(R.id.tv_item_contact_list_content);
        }

        private void bindData(Contact contact){
            mContent.setText(String.format(Locale.getDefault(),FORMAT, contact.getName()));


        }
    }
}
