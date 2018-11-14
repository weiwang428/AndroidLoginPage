package com.example.tmp_sda_1165.expsqlitedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserTableImp {
    private DBHelper sqLiteUtil;
    private SQLiteDatabase db;

    public UserTableImp(Context context) {
        sqLiteUtil = new DBHelper(context, 1);
        System.out.println(sqLiteUtil.getWritableDatabase().getVersion());
        db = sqLiteUtil.getWritableDatabase();
    }


    public void add(User user) {
        //sqLiteUtil.onUpgrade(db,8,8);
        if (user != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(User.USER_TABLE_INFO_COLUM_USERNAME, user.getUsername());
            contentValues.put(User.USER_TABLE_INFO_COLUM_PASSWORD, user.getPassword());
            contentValues.put(User.USER_TABLE_INFO_COLUM_EMAIL, user.getPassword());
            contentValues.put(User.USER_TABLE_INFO_COLUM_STATUS, user.isStatus());
            db.insert(User.USER_TABLE_NAME, null, contentValues);
        }
    }


    public void delete(String user_name) {
        db.delete(User.USER_TABLE_NAME, User.USER_TABLE_INFO_COLUM_USERNAME+" = ?", new String[]{String.valueOf(user_name)});
        db.close();

    }


    public void update(String user_name, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(User.USER_TABLE_INFO_COLUM_PASSWORD,password);
        db.update(User.USER_TABLE_NAME,contentValues,User.USER_TABLE_INFO_COLUM_USERNAME+"="+user_name,null);
        db.close();
    }

    public void update(String user_name, String password,String email) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(User.USER_TABLE_INFO_COLUM_PASSWORD,password);
        contentValues.put(User.USER_TABLE_INFO_COLUM_EMAIL,email);
        db.update(User.USER_TABLE_NAME,contentValues,User.USER_TABLE_INFO_COLUM_USERNAME+"="+user_name,null);
        db.close();
    }


    public User getUser(String user_name) {
        String querySQL= "select * from " + User.USER_TABLE_NAME + " where "+User.USER_TABLE_INFO_COLUM_USERNAME+"='"+user_name+"' and " + User.USER_TABLE_INFO_COLUM_STATUS + "=1";
        System.out.println(querySQL);
        Cursor cursor = db.rawQuery(querySQL, null);
        User userObj = null;

        while (cursor.moveToNext()) {
            String username = cursor.getString(cursor.getColumnIndex(User.USER_TABLE_INFO_COLUM_USERNAME));
            String password = cursor.getString(cursor.getColumnIndex(User.USER_TABLE_INFO_COLUM_PASSWORD));
            String email = cursor.getString(cursor.getColumnIndex(User.USER_TABLE_INFO_COLUM_EMAIL));
            int statusInt = cursor.getInt(cursor.getColumnIndex(User.USER_TABLE_INFO_COLUM_STATUS));
            boolean status;
            if (statusInt == 1) {
                status = true;
            } else {
                status = false;
            }
            userObj = new User(username, password, email, status);
        }
        cursor.close();
        return userObj;
    }

    public User getUserByEmail(String pemail) {
        String querySQL= "select * from " + User.USER_TABLE_INFO_COLUM_EMAIL + " where "+User.USER_TABLE_INFO_COLUM_EMAIL+"='"+pemail+"' and " + User.USER_TABLE_INFO_COLUM_STATUS + "=1";
        System.out.println(querySQL);
        Cursor cursor = db.rawQuery(querySQL, null);
        User userObj = null;

        while (cursor.moveToNext()) {
            String username = cursor.getString(cursor.getColumnIndex(User.USER_TABLE_INFO_COLUM_USERNAME));
            String password = cursor.getString(cursor.getColumnIndex(User.USER_TABLE_INFO_COLUM_PASSWORD));
            String email = cursor.getString(cursor.getColumnIndex(User.USER_TABLE_INFO_COLUM_EMAIL));
            int statusInt = cursor.getInt(cursor.getColumnIndex(User.USER_TABLE_INFO_COLUM_STATUS));
            boolean status;
            if (statusInt == 1) {
                status = true;
            } else {
                status = false;
            }
            userObj = new User(username, password, email,status);
        }
        cursor.close();
        return userObj;
    }


    public void closeDBConnection(){
        db.close();
    };



}
