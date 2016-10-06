package com.dev.zerty.z_mail.Database;

public class Constants {

    //Columns
    static final String ROW_ID="id";
    static final String STUDENTNUMBER = "studentnumber";
    static final String CLASSNAME = "classname";
    static final String IMAGE = "image";
    static final String FIRSTNAME = "firstname";
    static final String PREPOSITION = "preposition";
    static final String LASTNAME = "lastname";
    static final String ZIPCODE = "zipcode";
    static final String PLACE = "place";
    static final String EMAIL = "email";
    static final String LATITUDE = "latitude";
    static final String LONGITUDE = "longitude";

    //DB Properties
    static final String DB_NAME="n_DB";
    static final String TB_NAME="n_TB";
    static final int DB_VERSION='1';


    //Create Table Statements
    static final String CREATE_TB="CREATE TABLE n_TB(id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "studentnumber TEXT NOT NULL, classname TEXT NOT NULL, image TEXT NOT NULL, firstname TEXT NOT NULL, preposition TEXT NOT NULL, lastname TEXT NOT NULL," +
            "zipcode TEXT NOT NULL, place TEXT NOT NULL, email TEXT NOT NULL, latitude TEXT NOT NULL, longitude TEXT NOT NULL);";
}
