
package com.example.sxr169330.contacts;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import static com.example.sxr169330.contacts.R.layout.da_item;

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
public class MainActivity extends AppCompatActivity {

    public String filename = "Asg5_sxr169330.txt"; //file name
    int numOfLines = 0; //total number of lines in the file

    //function is called when the file activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // sets the Content to the appropritate designed layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        populateListView(MainActivity.this); //called to populate the list of contacts
        registerClickCallback(); //called when a contact in the displayed list is clicked

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void populateListView(Context context) {

        //counting the number of lines in the file
        try {

            File file = new File(filename);
            InputStream inputStream = context.openFileInput(filename);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";

                while ((receiveString = bufferedReader.readLine()) != null) {
                    numOfLines++;
                }
                inputStream.close();
            }

            //creating an array named "ret" to store all the lines stored in the file
            String[] ret = new String[numOfLines];

            //reading the lines in the file into array ret
            File file1 = new File(filename);
            InputStream inputStream1 = context.openFileInput(filename);
            int i = 0;
            if (inputStream != null) {
                InputStreamReader inputStreamReader1 = new InputStreamReader(inputStream1);
                BufferedReader bufferedReader1 = new BufferedReader(inputStreamReader1);
                String receiveString = "";

                while ((receiveString = bufferedReader1.readLine()) != null) {
                    String[] items = receiveString.split("\t");

                    //replace the ";" to empty string as they were not provided by the user earlier

                    if(items[1].matches(";")) {
                        items[1] = "";
                    }
                    if(items[2].matches(";")) {
                        items[2] = "";
                    }
                    if(items[3].matches(";")) {
                        items[3] = "";
                    }
                    if(items[4].matches(";")) {
                        items[4] = "";
                    }

                    String val = "" + items[0] + " " + items[1] + "\t" + items[2]; //formating the line to be displayed
                    ret[i] = val;

                    i++;
                }

                //sorting
                Arrays.sort(ret, String.CASE_INSENSITIVE_ORDER);

                // Build Adapter to display the list
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, da_item, ret); // Items to be displayed
                // Configure the list view.
                ListView list = (ListView) findViewById(R.id.listViewMain);
                list.setAdapter(adapter);
            }
            inputStream1.close();
        } catch (FileNotFoundException e1) {
            Log.e("login activity", "File not found: " + e1.toString());
        } catch (IOException e1) {
            Log.e("login activity", "Can not read file: " + e1.toString());
        }
    }

    //called when a entry in the list view is clicked
    private void registerClickCallback() {
        ListView list = (ListView) findViewById(R.id.listViewMain); //accessing the list view

        //called when a row is clicked
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> paret, View viewClicked, int position, long id) {

                position+=1; //incrementing the row number to range it between 1 and numOfLines
                String pos=String.valueOf(position);
                String num=String.valueOf(numOfLines);
                Intent intent = new Intent(MainActivity.this, NewContact.class);
                Bundle b=new Bundle();

                b.putString("Position",pos);
                b.putString("TotalLines",num);
                intent.putExtras(b); //adding data to be sent to NewContact.jave
                startActivity(intent); //sending control to NewContact.java
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_add_contact) {
            String position="0"; //seting row value to zero to indicate that no row has been clicked
            String num=String.valueOf(numOfLines);
            Intent intent = new Intent(MainActivity.this, NewContact.class);
            Bundle b=new Bundle();

            b.putString("Position",position);
            b.putString("TotalLines",num);
            intent.putExtras(b); //adding data to be sent to NewContact.java
            startActivity(intent); //sending control to NewContact.java
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
