package com.example.sxr169330.contacts;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by sxr169330 on 3/21/2017.
 *
 * Name: Shreya Vishwanath Rao
 * Net ID: sxr169330
 *
 * Assignment Due Date: March 27, 2017
 *
 *  Purpose: The application acts as a miniature contact manager (A simple android program)
 *          -It stores the following details:
 *                  ~First Name
 *                  ~Last Name
 *                  ~Phone number
 *                  ~Email address
 *                  ~Birth Date
 *
 *          - On loading, a list of contacts (First Name, Last Name and Phone number) is displayed.
 *          - User can create a new contact(only First Name is a requirement) and SAVE it.
 *          -User can click on any contact in the displayed list to EDIT/MODIFY or DELETE it.
 *          -All data is stored in a file named "Asg5_sxr169330.txt"
 *
 *
 */

public class NewContact extends AppCompatActivity {

    public EditText fname; //to access First Name text field
    public EditText lname; //to access Last Name text field
    public EditText phone; //to access Phone text field
    public EditText email; //to access Email text field
    public EditText bdate; //to access Birth date text field

    public Button cancel; //to access Cancel button
    public Button save; //to access Save button
    public Button delete; //to access Delete button

    public int numofLines; //total number of lines in the file
    public int loc = -1; //indicated if a line has been selected (loc>=0) or not (loc=-1)
    File file; //to access the file

    public String filename = "Asg5_sxr169330.txt"; //file name


    //function is called when the file activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact); // sets the Content to the appropritate designed layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //accessing each text field and button
        fname = (EditText) findViewById(R.id.txtFname);
        lname = (EditText) findViewById(R.id.txtLname);
        phone = (EditText) findViewById(R.id.txtPhone);
        email = (EditText) findViewById(R.id.txtEmail);
        bdate = (EditText) findViewById(R.id.txtBdate);

        cancel = (Button) findViewById(R.id.btnCancel);
        save = (Button) findViewById(R.id.btnSave);
        delete = (Button) findViewById(R.id.btnDelete);

        delete.setEnabled(false);

        Bundle b = getIntent().getExtras(); //catching the data sent by MainActivity.java
        String s = b.getString("Position"); //extracting position value
        String num = b.getString("TotalLines"); //extracting the numOfLines value

        int pos = Integer.parseInt(s);
        numofLines = Integer.parseInt(num);

        final String[] ret = new String[numofLines];
        //Retrieving all data
        try {

            //storing all the lines in the file ina n array names "ret"
            File file1 = new File(filename);
            InputStream inputStream = this.openFileInput(filename);
            System.out.println("Inside try for read main after count");
            int i = 0;
            if (inputStream != null) {
                System.out.println("Inside if IS main after count");
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";

                while ((receiveString = bufferedReader.readLine()) != null) {
                    ret[i] = receiveString;
                    i++;
                }

                //sorting the array ret according to first name
                Arrays.sort(ret, String.CASE_INSENSITIVE_ORDER);
                inputStream.close();

            }
        } catch (FileNotFoundException e1) {
            Log.e("login activity", "File not found: " + e1.toString());
        } catch (IOException e1) {
            Log.e("login activity", "Can not read file: " + e1.toString());
        }


        //to indicate if the a row in the contact list had been clicked
        if (pos > 0) {
            editContact(ret, pos);
        }

        //called when cancel button is clicked
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonCancel();
            }
        });


        //called when save button is clicked
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    btnSave_onClick(NewContact.this, ret);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        //called when delete button is clicked
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    buttonDelete(NewContact.this, ret);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    //buttoncancel
    public void buttonCancel() {
        startActivity(new Intent(this, MainActivity.class));
    }

    //buttonDelete
    public void buttonDelete(Context context, String[] list) throws IOException {

        //clearing the contents of the file
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
        String toWrite = "";
        outputStreamWriter.write(toWrite);
        outputStreamWriter.close();

        //Rewrite the file without the deleted contact
        OutputStreamWriter outputStreamWriter1 = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));

        for (int i = 0; i < numofLines; i++) {
            if (i != loc) {
                outputStreamWriter1.write(list[i] + "\n");
            }
        }

        outputStreamWriter1.close();

        System.out.println("done!");
        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, MainActivity.class));
    }

    //savebutton
    public void btnSave_onClick(Context context, String[] list) throws IOException {
        System.out.println("Inside save");

        //check if First Name text field has some data in it
        if (fname.getText().toString().matches("")) {
            Toast.makeText(context, "Please enter the First Name", Toast.LENGTH_SHORT).show();
            fname.requestFocus();
            }
        else
        {
            //replace the remaining empty field with ";"
            if (lname.getText().toString().matches("")) {
                lname.setText(";");
            }
            if (phone.getText().toString().matches("")) {
                phone.setText(";");
            }
            if (email.getText().toString().matches("")) {
                email.setText(";");
            }
            if (bdate.getText().toString().matches("")) {
                bdate.setText(";");
            }

            //new contact is saved
            if (loc < 0) {

                try {
                    //new contact to be written
                    String new_val = fname.getText().toString() + "\t" + lname.getText().toString() + "\t" + phone.getText().toString() + "\t" + email.getText().toString() + "\t" + bdate.getText().toString() + "\n";

                    FileOutputStream fos = openFileOutput(filename, context.MODE_APPEND);

                    //new contact is appended to the file
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_APPEND));
                    String toWrite = new_val;
                    outputStreamWriter.write(toWrite);
                    outputStreamWriter.close();

                    System.out.println("done!");
                    Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, MainActivity.class));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else //modify an existing contact
            {
                //Clear the contents of the file
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
                String toWrite = "";
                outputStreamWriter.write(toWrite);
                outputStreamWriter.close();

                //Rewrite the file
                OutputStreamWriter outputStreamWriter1 = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));

                for (int i = 0; i < numofLines; i++) {
                    if (i == loc) {
                        //write new line
                        String new_val = fname.getText().toString() + "\t" + lname.getText().toString() + "\t" + phone.getText().toString() + "\t" + email.getText().toString() + "\t" + bdate.getText().toString() + "\n";
                        outputStreamWriter1.write(new_val);
                    } else {
                        //write existing lines
                        outputStreamWriter1.write(list[i] + "\n");
                    }

                }

                outputStreamWriter1.close();

                System.out.println("done!");
                Toast.makeText(context, "Modified", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));

            }

        }
    }


//    private static boolean isExternalStorageReadOnly() {
//        String extStorageState = Environment.getExternalStorageState();
//        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
//            return true;
//        }
//        return false;
//    }
//
//    private static boolean isExternalStorageAvailable() {
//        String extStorageState = Environment.getExternalStorageState();
//        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
//            return true;
//        }
//        return false;
//
//    }


    //to edit the existing contact
    public void editContact(String[] ret, int pos) {
        //Displaying respective data
        String[] items = ret[pos - 1].split("\t");
        loc = pos - 1;

        //replace the ";" to empty string as they were not provided by the user earlier
        for(int i=1;i<5;i++)
        {
            if(items[i].matches(";"))
                items[i]="";
        }

        //initialize each text field on the activity
        fname.setText(items[0]);
        lname.setText(items[1]);
        phone.setText(items[2]);
        email.setText(items[3]);
        bdate.setText(items[4]);

        delete.setEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

}
