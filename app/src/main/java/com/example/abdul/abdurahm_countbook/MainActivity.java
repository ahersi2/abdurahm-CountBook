package com.example.abdul.abdurahm_countbook;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MainActivity extends AppCompatActivity {
    EditText user_init;
    public int counter=0;
    Button positive;
    Button negative;
    Button reset_value;
    TextView txt;
    Button saving;
    EditText nametext;
    String thename;
    public Date date;
    EditText datetext;

    static int initialvalues[] = new int[10];
    static String names[] = new String[10];
    static String comments[] = new String[10];
    static String current_value[] = new String[10];

    /***************************************************************
     *This .java file does a few things. There are 4 lists created, and are their indexes
     * are tracked. the whole purpose to know which listview item we clicked on(by passing
     * variable that tells us what index), and save the corresponding data in the same index
     * but in the correct list.
     * We have a list for the name, comment, initial value, and current value. They are all
     * indexed at the same place as the listview
     *
     */

    int initial;
    String the_comment;

    int itemposition;

    int list_position;


    public static final String TEXTFILE = "countbook.txt";

    private static final String FILENAME2 = "file2.sav";


// This is initializing the files to store the data
    public static final String PREFS_NAME = "MyPrefsFile5";
    public static final String PREFS_NAME2 = "MyPrefsFile6";
    public static final String PREFS_NAME3 = "MyPrefsFile7";
    public static final String PREFS_NAME4 = "MyPrefsFile8";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Preparing sharedpreferences

        SharedPreferences settings = getSharedPreferences(PREFS_NAME,0);
        SharedPreferences settings2 = getSharedPreferences(PREFS_NAME2,0);
        SharedPreferences settings3 = getSharedPreferences(PREFS_NAME3,0);
        SharedPreferences settings4 = getSharedPreferences(PREFS_NAME4,0);



        //****************************************************************************
        String wordsString = settings.getString("names","");
        String wordsString2 = settings2.getString("comments","");
        String wordsString3 = settings3.getString("current_value","");
        counter = settings4.getInt("counter",counter);



        String[] itemsWords = wordsString.split(",");
        String[] itemsWords2 = wordsString2.split(",");
        String[] itemsWords3 = wordsString3.split(",");
        //****************************************************************************
        Log.i("strEditText", "this is the itemswords "+itemsWords.length);

        String names_new[] = new String[50];
        for(int i=0;i< itemsWords.length;i++){
            names_new[i]=itemsWords[i];
        }


        if(names[0]==null){
            for(int i=0;i< itemsWords.length;i++){
                names[i] = itemsWords[i];
            }
        }

        //****************************************************************************
        String comments_new[] = new String[50];
        for(int i=0;i< itemsWords2.length;i++){
            comments_new[i]=(itemsWords2[i]);
        }

        if(comments[0]==null){
            for(int i=0;i< itemsWords2.length;i++){
                comments[i] = itemsWords2[i];
            }
        }
        //****************************************************************************
        String current_value_new[] = new String[50];
        for(int i=0;i< itemsWords3.length;i++){
            current_value_new[i]=(itemsWords3[i]);
        }

        if(current_value[0]==null){
            for(int i=0;i< itemsWords3.length;i++){
                current_value[i] = itemsWords3[i];
            }
        }


        Log.i("strEditText", "this is sharedprefferences "+names[0]);



        //This is where the index of the listview and the total items
        // in the listview is passed. keyname2, and keyname hold
        //those pieces of info. They are used as indexes.

        Bundle extras = getIntent().getExtras();
        if(extras !=null){
            list_position= extras.getInt("keyname2");
            itemposition = extras.getInt("keyname");
        }



        Log.i("strEditText", "this is where item will be appended to on list "+itemposition);

        Log.i("strEditText", "this is the list position "+list_position);


        SimpleDateFormat formating = new SimpleDateFormat("yyyy-MM-dd");
        String newdate = formating.format(new Date());



        nametext = (EditText) findViewById(R.id.naming);
        datetext = (EditText) findViewById(R.id.the_date);


        datetext.setText(newdate);
        saving = (Button) findViewById(R.id.saved);

        positive = (Button) findViewById(R.id.up);
        negative = (Button) findViewById(R.id.down);
        reset_value = (Button) findViewById(R.id.reset);
        txt = (TextView) findViewById(R.id.tx);

        //Enters here if a listview item is clicked
        if(list_position >=0){
            EditText fillname = (EditText)findViewById(R.id.naming);
            fillname.setText(names[list_position]);


            //For initial value user input
            EditText fill_initial  =(EditText)findViewById(R.id.initial_id);
            fill_initial.setText(String.valueOf(initialvalues[list_position]));
            //counter = initialvalues[list_position];

            String cur = current_value[list_position];


            Log.i("strEditText", "this is the cur " + cur);


            if(cur != null) {
                if(cur != "") {
                    counter = Integer.parseInt(cur);
                }
            }


            txt.setText(current_value[list_position]);

            //For comment user input
            EditText fill_comment = (EditText)findViewById(R.id.comment_id);
            fill_comment.setText(comments[list_position]);
        }

        //Enters here if the save button is clicked
        saving.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                EditText editText = (EditText)findViewById(R.id.naming);
                String text = editText.getText().toString();

                //For initial value user input
                EditText editText2  =(EditText)findViewById(R.id.initial_id);
                initial = Integer.parseInt(editText2.getText().toString());

                //For comment user input
                EditText edittext3 = (EditText)findViewById(R.id.comment_id);
                the_comment = edittext3.getText().toString();


                //For name user input
                thename = nametext.getText().toString();

                Intent passinginfo = new Intent(MainActivity.this, Home_activity.class);
                passinginfo.putExtra("thename_passed", thename );






                //The following if else conditions test if we clicked on add a counter,
                //or if we clicked on an existing counter.
                if(list_position ==0 && itemposition ==0) {
                    names[itemposition] = thename;
                    comments[itemposition] = the_comment;
                    initialvalues[itemposition] = initial;
                    if(counter == 0) {
                        current_value[itemposition] = Integer.toString(initial);
                    }
                }
                else if(list_position>0 && itemposition ==0){
                    names[list_position] = thename;
                    comments[list_position] = the_comment;
                    initialvalues[list_position] = initial;
                    //current_value[list_position] = Integer.toString(initial);
                }
                else if(itemposition>0){
                    names[itemposition] = thename;
                    comments[itemposition] = the_comment;
                    initialvalues[itemposition] = initial;
                    current_value[itemposition] = Integer.toString(initial);
                }
                else{
                    names[itemposition] = thename;
                    comments[itemposition] = the_comment;
                    initialvalues[itemposition] = initial;
                    current_value[itemposition] = Integer.toString(initial);
                }

                if(list_position == 0) {
                    Log.d("strEditText", "This is the updated name, itemposition " + names[itemposition]);
                }
                else{
                    Log.d("strEditText", "This is the updated name, listposition " + names[list_position]);
                }


                //Stringbuilder data for saving the lists

                StringBuilder stringBuilder4 = new StringBuilder();
                for(String s : names){
                    stringBuilder4.append(s);
                    stringBuilder4.append(",");

                }
                //****************************************************************************
                StringBuilder stringBuilder5 = new StringBuilder();
                for(String s : comments){
                    stringBuilder5.append(s);
                    stringBuilder5.append(",");

                }

                //****************************************************************************

                StringBuilder stringBuilder6 = new StringBuilder();
                for(String s : current_value){
                    stringBuilder6.append(s);
                    stringBuilder6.append(",");

                }

                //****************************************************************************

                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences settings2 = getSharedPreferences(PREFS_NAME2, 0);
                SharedPreferences settings3 = getSharedPreferences(PREFS_NAME3, 0);


                SharedPreferences.Editor editor = settings.edit();
                editor.putString("names", stringBuilder4.toString());
                editor.commit();

                SharedPreferences.Editor editor2 = settings2.edit();
                editor2.putString("comments", stringBuilder5.toString());
                editor2.commit();

                SharedPreferences.Editor editor3 = settings3.edit();
                editor3.putString("current_value", stringBuilder6.toString());
                editor3.commit();


                SharedPreferences settings4 = getSharedPreferences(PREFS_NAME4,0);
                SharedPreferences.Editor editor4 = settings4.edit();
                editor4.putInt("counter",counter);
                editor4.commit();

                setResult(RESULT_OK,passinginfo);
                editText.getText().clear();

                finish();
                //startActivity(passinginfo);


            }
        });

        //This sets the currentvalue to equal initial value
        reset_value.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View view){
                EditText editText2  =(EditText)findViewById(R.id.initial_id);
                initial = Integer.parseInt(editText2.getText().toString());
                current_value[list_position] = Integer.toString(initial);
            }
        });


        //this increase counter
        positive.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View view){
                counter= counter +1;
                current_value[list_position] = Integer.toString(counter);
                txt.setText(Integer.toString(counter));



            }

        });

        //this decreases counter
        negative.setOnClickListener((new View.OnClickListener(){
            @Override

            public  void onClick(View view){
                if(counter>0){
                    counter--;
                    current_value[list_position] = Integer.toString(counter);
                }

                txt.setText(Integer.toString(counter));
            }
        }));

    }



}
