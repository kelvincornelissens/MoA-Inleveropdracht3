package com.dev.zerty.z_mail.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DBAdapter {
    Context c;
    SQLiteDatabase db;
    DBHelper helper;

    public DBAdapter(Context ctx) {
        this.c = ctx;
        helper = new DBHelper(c);
    }

    //Open DB
    public DBAdapter openDB() {
        try {
            db = helper.getWritableDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    //Close DB
    public void closeDB() {
        try {
            helper.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Insert Data to DB
    public long addToDB(String studentnumber, String classname, String image, String firstname, String preposition,
                        String lastname, String zipcode, String place, String email, double latitude, double longitude) {
        try {
            ContentValues cv = new ContentValues();
            cv.put(Constants.STUDENTNUMBER, studentnumber);
            cv.put(Constants.CLASSNAME, classname);
            cv.put(Constants.IMAGE, image);
            cv.put(Constants.FIRSTNAME, firstname);
            cv.put(Constants.PREPOSITION, preposition);
            cv.put(Constants.LASTNAME, lastname);
            cv.put(Constants.ZIPCODE, zipcode);
            cv.put(Constants.PLACE, place);
            cv.put(Constants.EMAIL, email);
            cv.put(Constants.LATITUDE, latitude);
            cv.put(Constants.LONGITUDE, longitude);

            return db.insert(Constants.TB_NAME, Constants.ROW_ID, cv);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //Retrieve all info from DB
    public Cursor getAllInfo() {
        String[] columns = {Constants.ROW_ID, Constants.STUDENTNUMBER, Constants.CLASSNAME, Constants.IMAGE, Constants.FIRSTNAME, Constants.PREPOSITION,
        Constants.LASTNAME, Constants.ZIPCODE, Constants.PLACE, Constants.EMAIL, Constants.LATITUDE, Constants.LONGITUDE};

        return db.query(Constants.TB_NAME, columns, null, null, null, null, null);
    }

    //Update info to DB
    public long Update(int id, String studentnumber, String classname, String image, String firstname, String preposition,
                       String lastname, String zipcode, String place, String email, double latitude, double longitude) {
        try {
            ContentValues cv = new ContentValues();
            cv.put(Constants.STUDENTNUMBER, studentnumber);
            cv.put(Constants.CLASSNAME, classname);
            cv.put(Constants.IMAGE, image);
            cv.put(Constants.FIRSTNAME, firstname);
            cv.put(Constants.PREPOSITION, preposition);
            cv.put(Constants.LASTNAME, lastname);
            cv.put(Constants.ZIPCODE, zipcode);
            cv.put(Constants.PLACE, place);
            cv.put(Constants.EMAIL, email);
            cv.put(Constants.LATITUDE, latitude);
            cv.put(Constants.LONGITUDE, longitude);
            return db.update(Constants.TB_NAME, cv, Constants.ROW_ID + " = ?", new String[]{String.valueOf(id)});
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //Delete info from DB
    public long Delete(int id) {
        try {
            return db.delete(Constants.TB_NAME, Constants.ROW_ID + " =?", new String[]{String.valueOf(id)});
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}