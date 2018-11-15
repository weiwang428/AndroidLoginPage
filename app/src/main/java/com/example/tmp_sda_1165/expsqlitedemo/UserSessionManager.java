package com.example.tmp_sda_1165.expsqlitedemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class UserSessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    private static int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "SqlDemoPerf";
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";
    private static final String KEY_ID = "USER_ID";
    private static final String KEY_PWD = "USER_PWD";
    private static final String KEY_USERNAME = "USER_NAME";

    UserTableImp _userTable;

    public UserSessionManager(Context context) {
        this._context = context;
        this.pref = this._context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        this.editor = this.pref.edit();
        this._userTable = new UserTableImp(context);
    }

    public void createUserLoginSession(String id, String pwd) {
        this.editor.putBoolean(IS_USER_LOGIN, true);
        this.editor.putString(KEY_ID, id);
        this.editor.putString(KEY_PWD, pwd);
        this.editor.commit();

/*        if (!this.CheckLogIn()) {
            this.logoutUser();
        }*/
    }

    // Check the log in information.
    public boolean CheckLogIn() {
        boolean is_login = this.pref.getBoolean(IS_USER_LOGIN, false);
        if (is_login) {
            String user_id = this.pref.getString(KEY_ID, "");
            String user_pwd = this.pref.getString(KEY_PWD, "");
            User user = this._userTable.getUser(user_id);
            if (user != null && user_pwd.equals(user.getPassword())) {
                this.editor.putString(KEY_USERNAME, user.getUsername());
                this.editor.commit();
                return true;
            }
        }
        return false;
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> usr = new HashMap<String, String>();
        usr.put(KEY_ID, this.pref.getString(KEY_ID, null));
        usr.put(KEY_PWD, this.pref.getString(KEY_PWD, null));
        usr.put(KEY_USERNAME, this.pref.getString(KEY_USERNAME, null));
        // Return to the caller.
        return usr;
    }

    public void logoutUser() {
        this.editor.clear();
        this.editor.commit();

        // Now Start the log in Activity again.
        Intent i = new Intent(this._context, loginActivity.class);
        // Closing all the activities.
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new flag.
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Now start the new activity.
        this._context.startActivity(i);
    }
}
