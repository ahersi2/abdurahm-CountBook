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
    private int counter = 0;
    Button positive;
    Button negative;
    TextView txt;
    Button saving;
    EditText nametext;
    String thename;
    public Date date;
    EditText datetext;

    static int initialvalues[] = new int[10];
    static String names[] = new String[10];
    static String comments[] = new String[10];

    //ArrayList<String> nameslist = new ArrayList<String>(10);

    int initial;
    String the_comment;

    int itemposition;

    int list_position;


    public static final String TEXTFILE = "countbook.txt";

    private static final String FILENAME2 = "file2.sav";






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //itemposition = getIntent().getExtras().getString("keyname");

        Bundle extras = getIntent().getExtras();
        if(extras !=null){
            list_position= extras.getInt("keyname2");
            itemposition = extras.getInt("keyname");
        }



        Log.i("strEditText", "this is where item will be appended to on list "+itemposition);

        Log.i("strEditText", "this is the list position "+list_position);

        //user_init = (EditText) findViewById(R.id.editText2);

        //if(user_init != null){
        //String num = user_init.getText().toString();
        //txt.setText(num);

        //}

        //String newdate = DateFormat.getDateInstance().format(new Date());
        SimpleDateFormat formating = new SimpleDateFormat("yyyy-MM-dd");
        String newdate = formating.format(new Date());



        nametext = (EditText) findViewById(R.id.naming);
        datetext = (EditText) findViewById(R.id.the_date);


        datetext.setText(newdate);
        saving = (Button) findViewById(R.id.saved);

        positive = (Button) findViewById(R.id.up);
        negative = (Button) findViewById(R.id.down);
        txt = (TextView) findViewById(R.id.tx);
        txt.setText("0");

        //Log.d("strEditText", thename);

        if(list_position !=0){
            EditText fillname = (EditText)findViewById(R.id.naming);
            fillname.setText(names[list_position-1]);

            //For initial value user input
            EditText fill_initial  =(EditText)findViewById(R.id.initial_id);
            fill_initial.setText(names[list_position-1]);

            //For comment user input
            EditText fill_comment = (EditText)findViewById(R.id.comment_id);
            fill_comment.setText(names[list_position-1]);
        }

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
                setResult(RESULT_OK,passinginfo);
                finish();


                //for(int i=0; i<10; i++){
                //    names[i]= null;
                //}

                if(list_position ==0) {
                    names[itemposition] = thename;
                    comments[itemposition] = the_comment;
                    initialvalues[itemposition] = initial;
                }
                else{
                    names[list_position-1] = thename;
                    comments[list_position-1] = the_comment;
                    initialvalues[list_position-1] = initial;
                }

                if(list_position == 0) {
                    Log.d("strEditText", "This is the updated name, itemposition " + names[itemposition]);
                }
                else{
                    Log.d("strEditText", "This is the updated name, listposition " + names[list_position-1]);
                }

                editText.getText().clear();
                finish();
                //startActivity(passinginfo);


            }
        });





        positive.setOnClickListener(new View.OnClickListener(){
            @Override

            public void onClick(View view){
                counter ++;
                txt.setText(Integer.toString(counter));



            }

        });


        negative.setOnClickListener((new View.OnClickListener(){
            @Override

            public  void onClick(View view){
                if(counter>0){
                    counter--;
                }

                txt.setText(Integer.toString(counter));
            }
        }));

    }
/*
    @Override

    protected void onStart(){
        super.onStart();
        loadFromFile();
        //adapter = new ArrayAdapter<String>(Home_activity.this, android.R.layout.simple_list_item_multiple_choice, list_array);
        //counter_list.setAdapter(adapter);
        //counter_list.getAdapter().getCount();
        //length_hack = counter_list.getAdapter().getCount();
        //Log.d("strEditText", "here is the size of the array "+length_hack);
        //Toast.makeText(getApplicationContext(), "Total number of items: "+ counter_list.getAdapter().getCount(), Toast.LENGTH_LONG).show();
    }

    private void loadFromFile(){
        try{
            FileInputStream fis = openFileInput(FILENAME2);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            String line = in.readLine();
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<String>>() {}.getType();
            list_array = gson.fromJson(line, listType);






        } catch (FileNotFoundException e){
            list_array = new ArrayList<>();
        } catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    private void saveinFile(){
        try{
            FileOutputStream fos = openFileOutput(FILENAME2,Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(list_array, writer);
            writer.flush();
            fos.close();
        } catch (FileNotFoundException e){
            throw new RuntimeException();
        } catch (IOException e){
            throw new RuntimeException();

        }
    }


*/


}
