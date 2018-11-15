package com.example.tmp_sda_1165.expsqlitedemo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final String KEY_ID = "USER_ID";
    private static final String KEY_PWD = "USER_PWD";
    private static final String KEY_USERNAME = "USER_NAME";

    private EditText emailInput;
    private EditText pwInput;

    private Context _context;
    private UserSessionManager session;
    String user_id;
    String user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this._context = this.getApplicationContext();
        this.session = new UserSessionManager(this._context);

        if (!this.session.CheckLogIn()) {
            this.session.logoutUser();
        }

        HashMap<String, String> usr  = this.session.getUserDetails();
        //user_id = usr.get(KEY_ID);
        user_name = usr.get(KEY_USERNAME);

        TextView tv = (TextView) findViewById(R.id.tv_UserId);
        tv.setText("HomePage " + user_name);

        Button btnLogOut = (Button) findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                session.logoutUser();
            }
        });
    }
}
