package com.dev.zerty.z_mail.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.dev.zerty.z_mail.Database.DBAdapter;
import com.dev.zerty.z_mail.R;
import com.dev.zerty.z_mail.Students.StudentData;

public class MainActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etSubject;
    private EditText etMessage;

    private FloatingActionButton fab;
    private SwipeRefreshLayout swipeRefresher;
    private int status = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //insertStudentsIntoDB();

        //Init controls
        etEmail = (EditText) findViewById(R.id.etEmail);
        etEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(status == 0){
                    etEmail.setSingleLine(true);
                    status++;
                }
                else{
                    etEmail.setSingleLine(false);
                    status--;
                }
            }
        });
        etEmail.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(etEmail.getText().toString().trim().length() == 0){
                    Toast.makeText(MainActivity.this, "Nothing to clear", Toast.LENGTH_SHORT).show();
                }
                else{
                    etEmail.setText("");
                    Toast.makeText(MainActivity.this, "Cleared Emails", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        etSubject = (EditText) findViewById(R.id.etSubject);
        etMessage = (EditText) findViewById(R.id.etMessage);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        //If all fields are not empty it'll open a email app of choice
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkOnSend();
            }
        });
        //Calling a method
        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                etMessage.setText(fillMessage());
                etMessage.setSelection(etMessage.length());
                return true;
            }
        });

        //Calling a method
        swipeRefresh();

        //Calling a method
        getExtraFromOpener();
    }

    //If the app is opened from another app ("android.intent.action.SEND") it will check for extra info and set it
    private void getExtraFromOpener() {
        Intent i = getIntent();
        String sharedText = i.getStringExtra("Email");
        if (sharedText != null) {
            etEmail.setText(sharedText);
        }
    }

    //OnSwipe clear/reset page
    private void swipeRefresh() {
        swipeRefresher = (SwipeRefreshLayout) findViewById(R.id.swipeRefresher);
        swipeRefresher.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        //If something has been filled in: able to reset
                        if (etEmail.getText().toString().trim().length() > 0 || etSubject.getText().toString().trim().length() > 0 || etMessage.getText().toString().trim().length() > 0) {
                            refreshDialog("Clear", "Are you sure you want to clear the activity?", "Yes", "No");
                        } else if (etEmail.hasFocus() || etSubject.hasFocus() || etMessage.hasFocus()) {
                            clear();
                            Toast.makeText(MainActivity.this, "Focus cleared.", Toast.LENGTH_SHORT).show();
                        }
                        //If everything is empty already:
                        else {
                            Toast.makeText(MainActivity.this, "Nothing to clear.", Toast.LENGTH_SHORT).show();
                        }
                        swipeRefresher.setRefreshing(false);
                    }
                }
        );
    }

    private void clear() {
        etEmail.setText("");
        etEmail.clearFocus();
        etSubject.setText("");
        etSubject.clearFocus();
        etMessage.setText("");
        etMessage.clearFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    private void checkOnSend(){
        //If nothing is empty it'll just send the email
        if (etEmail.getText().toString().trim().length() > 0 && etSubject.getText().toString().trim().length() > 0 && etMessage.getText().toString().trim().length() > 0) {
            openEmailApp(etEmail.getText().toString(), etSubject.getText().toString(), etMessage.getText().toString());
            //If subject and message are blank: Dialog and choice
        } else if (etEmail.getText().toString().trim().length() > 0 && etSubject.getText().toString().trim().length() == 0 && etMessage.getText().toString().trim().length() == 0) {
            emailDialog("Sending", "Subject and message are blank are you sure you want to continue?", "Yes", "No");
            //If only subject is blank: Dialog and choice
        } else if (etEmail.getText().toString().trim().length() > 0 && etMessage.getText().toString().trim().length() > 0 && etSubject.getText().toString().trim().length() == 0) {
            emailDialog("Sending", "Subject is blank are you sure you want to continue?", "Yes", "No");
            //If only message is blank: Dialog and choice
        } else if (etEmail.getText().toString().trim().length() > 0 && etSubject.getText().toString().trim().length() > 0 && etMessage.getText().toString().trim().length() == 0) {
            emailDialog("Sending", "Message is blank are you sure you want to continue?", "Yes", "No");
        }
        else{
            Toast.makeText(MainActivity.this, "Email is required!", Toast.LENGTH_SHORT).show();
        }
    }

    //This will fill the message edittext with sample text (Lorum Ipsum)
    private String fillMessage() {
        String m = "L Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui.\n" +
                "Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum. Nam quam nunc, blandit vel, luctus pulvinar, hendrerit id, lorem. Maecenas nec odio et ante tincidunt tempus. Donec vitae sapien ut libero venenatis faucibus. Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo. Sed fringilla mauris sit amet nibh. Donec sodales sagittis magna. Sed consequat, leo eget bibendum sodales, augue velit cursus nunc, quis gravida magna mi a libero. Fusce vulputate eleifend sapien. Vestibulum purus quam, scelerisque ut, mollis sed, nonummy id, metus. Nullam accumsan lorem in dui. Cras ultricies mi eu turpis hendrerit fringilla. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; In ac dui quis mi consectetuer lacinia.";
        return m;
    }

    //Opens a email app of choice, with the values from main activity
    private void openEmailApp(String email, String subject, String message) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("plain/text");
        i.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{email});
        i.putExtra(Intent.EXTRA_SUBJECT, subject);
        i.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(i);
    }

    //Confirmation for clearing the activity
    private void refreshDialog(String title, String message, String pText, String nText) {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(title);
        b.setMessage(message);
        b.setCancelable(false);

        b.setPositiveButton(
                pText,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        clear();
                        Toast.makeText(MainActivity.this, "Activity Cleared", Toast.LENGTH_SHORT).show();
                    }
                });

        b.setNegativeButton(
                nText,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Toast.makeText(MainActivity.this, "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });

        AlertDialog a = b.create();
        a.show();
    }

    //Email dialog: Proceed with empty subject or message
    private void emailDialog(String title, String message, String pText, String nText) {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(title);
        b.setMessage(message);
        b.setCancelable(false);

        b.setPositiveButton(
                pText,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        openEmailApp(etEmail.getText().toString(), etSubject.getText().toString(), etMessage.getText().toString());
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

    //Confirmation dialog if the user really wants to close the app
    //If someone writes a big story and clicks back button by accident progress won't be lost
    private void confirmationDialog(String title, String message, String pText, String nText) {
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle(title);
        b.setMessage(message);
        b.setCancelable(false);

        b.setPositiveButton(
                pText,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
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

    //Method to set email, it will add up, won't add duplicate emails and sets the cursor/pointer at the end of the string
    //I get the values from the RecyclerAdapter using startActivityForResult and this will receive the values
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent i) {
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                String email = i.getStringExtra("EMAIL");
                String classname = i.getStringExtra("CLASSNAME");
                if (!email.equals("")) {
                    if (etEmail.getText().toString().contains(email)) {
                        Toast.makeText(MainActivity.this, "Has been added already!", Toast.LENGTH_SHORT).show();
                    } else if (etEmail.getText().toString().equals("")) {
                        etEmail.setText(email);
                    } else if (!etEmail.getText().toString().equals("")) {
                        if (classname.equals("I4AO1")) {
                            etEmail.setText("");
                            etEmail.setText(email);
                        } else {
                            etEmail.setText(etEmail.getText().toString() + "," + "\n" + email);
                        }
                    }
                    etEmail.setSelection(etEmail.length());
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Toast.makeText(this, "No student has been selected...", Toast.LENGTH_SHORT).show();
                //Nothing, no result
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            confirmationDialog("Exit", "Are you sure you want to exit?", "Yes", "No");
        }
        return super.onKeyDown(keyCode, event);
    }

    //Default, init menu.main
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //Opens a intent loading the student list
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                return true;

            case R.id.action_student:
                Intent i = new Intent(this, SelectStudentActivity.class);
                startActivityForResult(i, 1);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}