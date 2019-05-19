package jy.com.libeasemob.training;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import jy.com.libeasemob.R;

/*
 * created by taofu on 2019/5/10
 **/
public class RegisterActivity  extends AppCompatActivity {

        private static final String TAG = "RegisterActivity";

        private Button mBtnRegister;
        private EditText mEtvUserName;
        private EditText mEtvPassword;



        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);


            mBtnRegister = findViewById(R.id.btn_register);

            mEtvUserName = findViewById(R.id.etv_username);

            mEtvPassword = findViewById(R.id.etv_password);



            mBtnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    register();
                }
            });
        }


        private void register(){
            final String userName = mEtvUserName.getText().toString().trim();
            final String password = mEtvPassword.getText().toString().trim();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        EMClient.getInstance().createAccount(userName, password);//同步方法
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            }
                        });

                    } catch (HyphenateException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                        e.printStackTrace();
                    }
                }
            }).start();

        }
}
