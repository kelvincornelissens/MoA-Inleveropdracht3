package com.dev.zerty.z_mail.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.dev.zerty.z_mail.Database.DBAdapter;
import com.dev.zerty.z_mail.Database.DBStudent;
import com.dev.zerty.z_mail.R;
import com.dev.zerty.z_mail.Recycler.MyRecyclerAdapter;
import com.dev.zerty.z_mail.Students.Student;
import com.dev.zerty.z_mail.Students.StudentData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.jar.Manifest;

import static com.dev.zerty.z_mail.R.id.swipeRefresher;

public class SelectStudentActivity extends AppCompatActivity{

    public class CompareValues implements Comparator<DBStudent> {

        public int compare(DBStudent obj1, DBStudent obj2) {
            return obj1.getFirstname().compareTo(obj2.getFirstname());
        }
    }

    private MyRecyclerAdapter adapter;
    private RecyclerView rv;
    private ArrayList<Student> s;
    private ArrayList<DBStudent> students = new ArrayList<>();
    private StringBuffer sb = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_student);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rv = (RecyclerView)findViewById(R.id.mRecyclerStudents);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setNestedScrollingEnabled(false);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmationDialog("Students", "Select all students?" , "Yes", "No");
            }
        });

        retrieve();
        //Set adapter and sort it by firstname (Default)
        CompareValues cv = new CompareValues();
        Collections.sort(students, cv);
        adapter = new MyRecyclerAdapter(this, students, this);
        rv.setAdapter(adapter);
    }

    private void retrieve() {
        DBAdapter db = new DBAdapter(this);
        db.openDB();
        students.clear();
        Cursor c = db.getAllInfo();
        //Loop through the data, adding to the arraylist
        while (c.moveToNext()) {
            int id = c.getInt(0);
            String studentnumber = c.getString(1);
            String classname = c.getString(2);
            String image = c.getString(3);
            String firstname = c.getString(4);
            String preposition = c.getString(5);
            String lastname = c.getString(6);
            String zipcode = c.getString(7);
            String place = c.getString(8);
            String email = c.getString(9);
            double latitude = c.getDouble(10);
            double longitude = c.getDouble(11);
            DBStudent dbs = new DBStudent(id, studentnumber, classname, image, firstname, preposition,
                    lastname, zipcode, place, email, latitude, longitude);
            students.add(dbs);
        }
    }

    //Sets the adapter with a chosen filter
    private void filterAdapter(ArrayList arr){
        adapter = (new MyRecyclerAdapter(this, arr, this));
        rv.setAdapter(adapter);
    }

    //Confirmation dialog to select all addresses
    private void confirmationDialog(String title, String message, String pText, String nText) {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(title);
        b.setMessage(message);
        b.setCancelable(false);

        b.setPositiveButton(
                pText,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        sb = new StringBuffer();
                        adapter.allstudents.addAll(students);
                        for (DBStudent s : adapter.allstudents) {
                            sb.append(s.getEmail() + ", ");
                        }
                        Intent i = new Intent();
                        i.putExtra("EMAIL", sb.toString());
                        i.putExtra("CLASSNAME", "I4AO1");
                        setResult(Activity.RESULT_OK, i);

                        dialog.cancel();

                        finish();
                    }
                });

        b.setNegativeButton(
                nText,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(SelectStudentActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

        AlertDialog a = b.create();
        a.show();
    }

    //Default Init menu.student
    //Setting the SV full width: It will hide the sort button while searching, this will prevent a lot more unnecessary bugs and code.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_student, menu);
        final MenuItem mi = menu.findItem(R.id.action_search);
        final SearchView sv = (SearchView)mi.getActionView();
        sv.setMaxWidth(Integer.MAX_VALUE);
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                adapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    //By clicking a item menu it'll sort the Students list and refresh the adapter with the new list
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_firstname:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                    Collections.sort(students, new Comparator<DBStudent>() {
                        public int compare(DBStudent o1, DBStudent o2) {
                            return o1.getFirstname().compareTo(o2.getFirstname());
                        }
                    });
                    filterAdapter(students);
                }
                break;
            case R.id.action_lastname:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                    Collections.sort(students, new Comparator<DBStudent>() {
                        public int compare(DBStudent o1, DBStudent o2) {
                            return o1.getLastname().compareTo(o2.getLastname());
                        }
                    });
                    filterAdapter(students);
                }
                break;
            case R.id.action_studentnumber:
                if (item.isChecked()) {
                    item.setChecked(false);
                } else {
                    item.setChecked(true);
                    Collections.sort(students, new Comparator<DBStudent>() {
                        public int compare(DBStudent o1, DBStudent o2) {
                            return o1.getStudentnumber().compareTo(o2.getStudentnumber());
                        }
                    });
                    filterAdapter(students);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
