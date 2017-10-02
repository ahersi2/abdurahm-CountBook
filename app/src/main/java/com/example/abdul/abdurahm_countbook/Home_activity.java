package com.example.abdul.abdurahm_countbook;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;


import android.app.ListActivity;
import android.util.Log;
import android.widget.AdapterView;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class Home_activity extends AppCompatActivity {

    Button btn;
    Button add;
    private Date date;

    int length_hack;

    private static final String FILENAME = "file.sav";
    public ListView counter_list;

    ArrayAdapter<String> adapter;

    ArrayList<String> list_array;

    public ListView allcounters;

    int remember;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //list_array = new ArrayList<String>();
        //adapter = new ArrayAdapter<String>(Home_activity.this, android.R.layout.simple_list_item_multiple_choice, list_array);
        //counter_list.setAdapter(adapter);
        Log.d("strEditText", "hcheck this out part 2 " + remember);


        counter_list = (ListView) findViewById(R.id.list);



        String newdate  = DateFormat.getDateInstance().format(new Date());



        btn = (Button) findViewById(R.id.option);
        add = (Button) findViewById(R.id.add_button);







        // This function is entered when a specific item in the listview is clicked

        counter_list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Intent intent = new Intent(Home_activity.this, MainActivity.class);
                //This passes the index of the clicked item to mainactivity.java
                intent.putExtra("keyname2",position);
                Home_activity.this.startActivity(intent);
                saveinFile();
            }
        });

        //If the user wants to add a counter, here is the function it enters

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //Log.d("strEditText", "here is the specific item  " + list_array.get(1));
                Intent i= new Intent(Home_activity.this, MainActivity.class);
                int value = counter_list.getAdapter().getCount();
                //This is passing the length of the current listview to the mainactivity.java,
                //Purpose is to know where to index correctly
                i.putExtra("keyname",value);
                startActivityForResult(i,1);


                saveinFile();



            }
        });

        //If the user wants to clear all the counters, this is the function it enters
        //

        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                list_array.clear();
                adapter.clear();
                adapter.notifyDataSetChanged();
                saveinFile();

            }
        });
    }


    @Override

    protected void onStart(){
        super.onStart();
        loadFromFile();
        if(list_array ==null){
            list_array = new ArrayList<String>();
        }
        adapter = new ArrayAdapter<String>(Home_activity.this, android.R.layout.simple_list_item_multiple_choice, list_array);

        //if(list_array !=null) {

        counter_list.setAdapter(adapter);


        if(list_array !=null) {
            counter_list.getAdapter().getCount();
        }
        if(list_array !=null) {
            length_hack = counter_list.getAdapter().getCount();
        }
        //Log.d("strEditText", "here is the size of the array "+length_hack);
        if(list_array !=null) {
            Toast.makeText(getApplicationContext(), "Total number of items: " + counter_list.getAdapter().getCount(), Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Total number of items:0 ", Toast.LENGTH_LONG).show();
        }
        Log.d("strEditText", "hcheck this out " + list_array.size());
        remember = list_array.size();
        Log.d("strEditText", "hcheck this outssdfdfs " + list_array.size());
    }

    //Loading the files
    private void loadFromFile(){
        try{
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            String line = in.readLine();
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<String>>() {}.getType();
            list_array = gson.fromJson(line, listType);




        } catch (FileNotFoundException e){
            list_array = new ArrayList<String>();
        } catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    //Function that saves the data in a file

    private void saveinFile(){
        try{
            FileOutputStream fos = openFileOutput(FILENAME,Context.MODE_PRIVATE);
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

// This is the function that gets called when we press save in the Main Activity portion
    // Its purpose is to update the name in the listview
    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        String textt = "result is ";



        if(requestCode ==1){
            if(resultCode == RESULT_OK){
                String strEditText = data.getStringExtra("thename_passed");



                Log.d("strEditText", textt+strEditText);





                list_array.add(strEditText);
                Log.d("strEditText", "here is the size of the array " + list_array.size());



                adapter.notifyDataSetChanged();
                saveinFile();

            }
        }
    }

}
