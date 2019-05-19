package jy.com.libcustomview.contactlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import jy.com.libcustomview.R;

/*
 * created by taofu on 2019-05-14
 **/
public class ContactListActivity extends AppCompatActivity {

    private RecyclerView mRvContactListView;
    private SliderView mSliderView;

    private List<Contact> mContacts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_contact_list);

        mRvContactListView = findViewById(R.id.rv_contact_list);

        mSliderView = findViewById(R.id.contact_slider_view);

        mSliderView.setOnSelectedCharListener(new SliderView.OnSelectedCharListener() {
            @Override
            public void onSelected(String value) {
                if(mContacts == null)
                    return;
                scrollToPosition(value);
            }
        });

        loadContactList();

    }

    private void scrollToPosition(String value){
        for(int i =0; i < mContacts.size(); i++){
            if(mContacts.get(i).getFistChar().equals(value)){
                ((LinearLayoutManager) mRvContactListView.getLayoutManager()).scrollToPositionWithOffset(i, 0);
                break;
            }

        }

    }

    private void loadContactList(){
        new Thread(new Runnable(){
            @Override
            public void run() {
               final ArrayList<Contact> contactList = ContactUtils.getPhoneContactList(getApplicationContext());

               if(contactList == null || contactList.size() < 1){
                   return;
               }

               for(Contact contact : contactList){
                   contact.setPinyin(ContactUtils.hanyu2pinyin(contact.getName()));

               }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       showContactList(contactList);
                    }
                });

            }
        }).start();
    }

    private void showContactList(ArrayList<Contact> contacts){

        Collections.sort(contacts);


        ContactListAdapter adapter = new ContactListAdapter(contacts);

        mContacts = contacts;
        mRvContactListView.setLayoutManager(new LinearLayoutManager(this));
        mRvContactListView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRvContactListView.addItemDecoration(new CharDecoration(this, contacts));
        mRvContactListView.setAdapter(adapter);
    }
}
