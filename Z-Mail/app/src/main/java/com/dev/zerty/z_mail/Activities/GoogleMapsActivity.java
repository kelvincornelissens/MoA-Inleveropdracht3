package com.dev.zerty.z_mail.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.zerty.z_mail.R;
import com.dev.zerty.z_mail.Students.Student;
import com.dev.zerty.z_mail.Students.StudentData;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class GoogleMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    //Reachable in the entire class
    private String image;
    private String firstname;
    private String pre;
    private String lastname;
    private String studentnumber;
    private String classname;
    private String zipcode;
    private String place;
    private String email;
    private double latitude;
    private double longitude;

    //Some properties for resizing image and the zoomlevel for the lat/long
    private int height = 180;
    private int width = 140;
    private float zoomLevel = 16.0f; //This goes up to 21

    private FloatingActionButton fab;

    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Getting all the values
        Intent i = getIntent();
        image = i.getStringExtra("image");
        firstname = i.getStringExtra("firstname");
        pre = i.getStringExtra("pre");
        lastname = i.getStringExtra("lastname");
        studentnumber = i.getStringExtra("studentnumber");
        classname = i.getStringExtra("classname");
        zipcode = i.getStringExtra("zipcode");
        place = i.getStringExtra("place");
        email = i.getStringExtra("email");
        latitude = i.getDoubleExtra("latitude", 0);
        longitude = i.getDoubleExtra("longitude", 0);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAllStudentsDialog("Students", "Show all students on the map?", "Yes", "No");
            }
        });
    }

    //Returns the correct name, with or without prefix
    private String studentName() {
        String name = "";
        if (pre.equals("")) {
            name = firstname + " " + lastname;
        } else {
            name = firstname + " " + pre + " " + lastname;
        }
        return name;
    }

    //Code to resize the image using the width and height under class line
    private Bitmap bImage() {
        BitmapDrawable bDraw = (BitmapDrawable) getResources().getDrawable(this.getApplicationContext().getResources().getIdentifier("com.dev.zerty.z_mail:drawable/" + image, null, null));
        Bitmap b = bDraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
        return smallMarker;
    }

    private void showAllStudents(){
        map.clear();
        for(int i = 0; i < StudentData.studentList().size(); i++){
            LatLng mLatLng = new LatLng(StudentData.studentList().get(i).getLatitude(), StudentData.studentList().get(i).getLongitude());

            String name = "";
            if (StudentData.studentList().get(i).getPreposition().equals("")) {
                name = StudentData.studentList().get(i).getFirstname() + " " + StudentData.studentList().get(i).getLastname();
            } else {
                name = StudentData.studentList().get(i).getFirstname() + " " + StudentData.studentList().get(i).getPreposition() + " " + StudentData.studentList().get(i).getLastname();
            }

            BitmapDrawable bDraw = (BitmapDrawable) getResources().getDrawable(GoogleMapsActivity.this.getResources().getIdentifier("com.dev.zerty.z_mail:drawable/" +
                    StudentData.studentList().get(i).getImage(), null, null));
            Bitmap b = bDraw.getBitmap();
            Bitmap smallMarker = Bitmap.createScaledBitmap(b, 140, 180, false);

            map.addMarker(new MarkerOptions().position(mLatLng).title(name)
                    .snippet("Studentnumber: " + StudentData.studentList().get(i).getStudentnumber()
                            + "\n" + "Classname: " + StudentData.studentList().get(i).getClassname()
                            + "\n" + "Zipcode: " + StudentData.studentList().get(i).getZipcode()
                            + "\n" + "Place: " + StudentData.studentList().get(i).getPlace()
                            + "\n" + "Email: " + StudentData.studentList().get(i).getEmail()).icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
        }
        Toast.makeText(GoogleMapsActivity.this, "Showing all students", Toast.LENGTH_SHORT).show();
    }

    private void showAllStudentsDialog(String title, String message, String pText, String nText) {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(title);
        b.setMessage(message);
        b.setCancelable(false);

        b.setPositiveButton(
                pText,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        showAllStudents();
                    }
                });

        b.setNegativeButton(
                nText,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog a = b.create();
        a.show();
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        map = googleMap;

        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                LinearLayout info = new LinearLayout(GoogleMapsActivity.this);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(GoogleMapsActivity.this);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.LEFT);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(GoogleMapsActivity.this);
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }

        });
        LatLng mLatLng = new LatLng(latitude, longitude);

        //Adding the marker with the info from i.getExtra using the getInfoContents method
        //m would be used for the method showInfoWindow();
        googleMap.addMarker(new MarkerOptions().position(mLatLng).title(studentName())
                .snippet("Studentnumber: " + studentnumber
                        + "\n" + "Classname: " + classname
                        + "\n" + "Zipcode: " + zipcode
                        + "\n" + "Place: " + place
                        + "\n" + "Email: " + email).icon(BitmapDescriptorFactory.fromBitmap(bImage())));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, zoomLevel));
        Toast.makeText(GoogleMapsActivity.this, "Showing " + studentName(), Toast.LENGTH_SHORT).show();
    }

}