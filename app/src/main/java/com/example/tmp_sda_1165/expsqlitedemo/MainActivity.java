package com.example.tmp_sda_1165.expsqlitedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String USER_NAME = "username";
    private EditText emailInput;
    private EditText pwInput;
    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    
    public void login(View view) {
        emailInput =(EditText) findViewById(R.id.editText_Email);
        pwInput=(EditText) findViewById(R.id.editText_Password);
        String emailname = emailInput.getText().toString().trim();
        String password = pwInput.getText().toString().trim();

        if (emailname.equals("") || emailname == null) {
            Toast myToast = Toast.makeText(this, "Please input the email!",
                    Toast.LENGTH_SHORT);
            myToast.show();
            ((EditText) findViewById(R.id.editText_Email)).requestFocus();
        } else if (password.equals("") || password == null) {
            Toast myToast = Toast.makeText(this, "Please input the password!",
                    Toast.LENGTH_SHORT);
            myToast.show();
            ((EditText) findViewById(R.id.editText_Password)).requestFocus();
        } else {
            UserTableImp userDao = new UserTableImp(this);
            User user = userDao.getUserByEmail(emailname);
            if (user == null) {
                Toast myToast = Toast.makeText(this, "User does not exist!",
                        Toast.LENGTH_SHORT);
                myToast.show();
                ((EditText) findViewById(R.id.editText_Email)).requestFocus();
                ((UserTableImp) userDao).closeDBConnection();
            } else if (!password.equals(user.getPassword())) {
                Toast myToast = Toast.makeText(this, "Please type in a correct password!",
                        Toast.LENGTH_SHORT);
                myToast.show();
                ((EditText) findViewById(R.id.editText_Password)).requestFocus();
                ((UserTableImp) userDao).closeDBConnection();
            } else if (password.equals(user.getPassword())) {
                ((UserTableImp) userDao).closeDBConnection();
                Intent newIntent = new Intent(this, HomePagreActivity.class);
                newIntent.putExtra(USER_NAME, user.getUsername());
                startActivity(newIntent);
            }
        }

    }

    public void signup(View view) {
        Intent newIntent = new Intent(this, UserSignup.class);
        startActivity(newIntent);
    }





}
