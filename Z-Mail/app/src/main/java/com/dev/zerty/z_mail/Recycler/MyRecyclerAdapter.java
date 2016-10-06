package com.dev.zerty.z_mail.Recycler;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.dev.zerty.z_mail.Database.DBStudent;
import com.dev.zerty.z_mail.R;

import java.util.ArrayList;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyViewHolder> implements Filterable {

    Context c;
    private Activity activity;
    ArrayList<DBStudent> students;
    ArrayList<DBStudent> filterList;
    public ArrayList<DBStudent> allstudents = new ArrayList<>();
    CustomFilter filter;
    private int lastPosition = -1;

    //Constructor
    public MyRecyclerAdapter(Context c, ArrayList<DBStudent> students, Activity activity) {
        this.c = c;
        this.students = students;
        this.filterList = students;
        this.activity = activity;
    }

    //Inflating the card layout
    //Using 2 click listeners on the card, this is possible due the MyViewHolder class and the interface passing the pos and view
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card, null);
        MyViewHolder holder = new MyViewHolder(v);

        holder.onClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                passEmail(v, pos);
            }
        });

        holder.onLongClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                longClickDialog("Student information", "Choose a action", "Location", "Details", pos, v);
            }
        });

        return holder;
    }

    //Setting the values from the StudentData class
    @Override
    public void onBindViewHolder(MyViewHolder holder, int pos) {

        //Setting the image using a string value(imgxxxxxxx, studentnumber)
        int id = c.getApplicationContext().getResources().getIdentifier("com.dev.zerty.z_mail:drawable/" + students.get(pos).getImage(), null, null);
        holder.imgView.setImageResource(id);

        //If people don't have preposition it will show a unnecessary space in the card, using the if/else it'll show the full names correctly.
        if (students.get(pos).getPreposition().equals("")) {
            holder.tName.setText(students.get(pos).getFirstname() + " " + students.get(pos).getLastname());
        } else {
            holder.tName.setText(students.get(pos).getFirstname() + " " + students.get(pos).getPreposition() + " " + students.get(pos).getLastname());
        }

        //Setting the studentnumber and classname
        holder.tStudentNumber.setText(students.get(pos).getStudentnumber());
        holder.tClassname.setText(students.get(pos).getClassname());

        setAnimation(holder.container, pos);
    }

    //Passing the data to the mainactivity
    private void passEmail(View v, int pos) {
        Intent i = new Intent();
        i.putExtra("EMAIL", students.get(pos).getEmail());
        i.putExtra("CLASSNAME", "");
        activity.setResult(Activity.RESULT_OK, i);
        ((Activity) v.getContext()).finish();
    }

    //Returning a spanned with all students info, this is used in the detail dialog
    private Spanned returnAllInfo(int pos) {
        Spanned r =
                Html.fromHtml("<b>" + "Full Name: " + "</b>" + students.get(pos).getFirstname() + " " + students.get(pos).getPreposition() + " " + students.get(pos).getLastname() + "<br/>" +
                        "<b>" + "Student number: " + "</b> " + students.get(pos).getStudentnumber() + "<br/>" +
                        "<b>" + "Class name: " + "</b>" + students.get(pos).getClassname() + "<br/>" +
                        "<b>" + "Zip code: " + "</b> " + students.get(pos).getZipcode() + "<br/>" +
                        "<b>" + "Place: " + "</b> " + students.get(pos).getPlace() + "<br/>" +
                        "<b>" + "Email: " + "</b> " + students.get(pos).getEmail());
        return r;
    }

    //Opening a dialog with the students details, using paramaters to set specific values in the calling method
    //Parameters for re-using purposes
    private void detailDialog(String title, Spanned message, String bText, View v) {
        AlertDialog.Builder b = new AlertDialog.Builder(v.getContext());
        b.setTitle(title);
        b.setMessage(message);
        b.setCancelable(false);

        b.setNegativeButton(
                bText,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog a = b.create();
        a.show();
    }

    //Opening detail screen or the students location using maps intent
    private void longClickDialog(String title, String message, String pText, String nText, final int pos, final View v){
        AlertDialog.Builder b = new AlertDialog.Builder(v.getContext());
        b.setTitle(title);
        b.setMessage(message);
        b.setCancelable(true);

        b.setPositiveButton(
                pText,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(c, "Opdracht 3", Toast.LENGTH_SHORT).show();
                    }
                });

        b.setNegativeButton(
                nText,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        detailDialog("Details", returnAllInfo(pos), "Close", v);
                        dialog.cancel();
                    }
                });

        AlertDialog a = b.create();
        a.show();
    }

    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(c, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    //List size
    @Override
    public int getItemCount() {
        return students.size();
    }

    //Default method from implementing Filterable
    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CustomFilter(this, filterList);
        }
        return filter;
    }
}
