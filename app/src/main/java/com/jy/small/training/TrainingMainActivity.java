package com.jy.small.training;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.*;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import jy.com.libbdmap.training.BdMapActivity;
import jy.com.libcustomview.contactlist.ContactListActivity;
import jy.com.libumengsharelogin.LoginActivity;
import jy.com.libumengsharelogin.ShareActivity;
import k.mr.com.libdispatchevent.EventDispatchActivity;

import java.lang.reflect.Field;
import java.security.acl.Group;

/*
 * created by taofu on 2019/5/5
 **/
@SuppressLint("HandlerLeak")
public class TrainingMainActivity extends AppCompatActivity {



    private Group mGroup1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_training);

        mGroup1 = findViewById(R.id.group);

        ((View) mGroup1).setVisibility(View.GONE);

       // startActivity(new Intent(this,EventDispatchActivity.class));




    }




    public void onClick(View v){
        switch (v.getId()){

            case R.id.btn_start_umeng_share:{
                Intent intent = new Intent(this,ShareActivity.class);
                startActivity(intent);

                int i = 10 / 0;
                break;
            }

            case R.id.btn_start_umeng_login:{
                Intent intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_start_bdmap:{
                Intent intent = new Intent(this,BdMapActivity.class);
                startActivity(intent);
                break;
            }
            case  R.id.btn_start_easemob:{
              //  Intent intent = new Intent(this,RegisterActivity.class);
                //startActivity(intent);

                break;
            }

            case R.id.btn_start_contact_list:{
                Intent intent = new Intent(this, ContactListActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
