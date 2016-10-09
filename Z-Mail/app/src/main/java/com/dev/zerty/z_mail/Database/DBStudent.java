package com.dev.zerty.z_mail.Database;

/**
 * Created by Zerty on 9/28/2016.
 */

public class DBStudent {

    private int id;
    private String studentnumber;
    private String classname;
    private String image;
    private String firstname;
    private String preposition;
    private String lastname;
    private String zipcode;
    private String place;
    private String email;
    private double latitude, longitude;

    //Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentnumber() {
        return studentnumber;
    }

    public void setStudentnumber(String studentnumber) {
        this.studentnumber = studentnumber;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getPreposition() {
        return preposition;
    }

    public void setPreposition(String preposition) {
        this.preposition = preposition;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    //Constructor
    public DBStudent(int id, String studentnumber, String classname, String image, String firstname, String preposition, String lastname, String zipcode, String place, String email, double latitude, double longitude) {
        this.id = id;
        this.studentnumber = studentnumber;
        this.classname = classname;
        this.image = image;
        this.firstname = firstname;
        this.preposition = preposition;
        this.lastname = lastname;
        this.zipcode = zipcode;
        this.place = place;
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
