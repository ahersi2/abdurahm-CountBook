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

    //private ArrayList<Counter> Counters = new ArrayList<Counter>();





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        list_array = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(Home_activity.this, android.R.layout.simple_list_item_multiple_choice, list_array);
        //counter_list.setAdapter(adapter);

        counter_list = (ListView) findViewById(R.id.list);

        //This gets the most recent size of the listview
        //loadFromFile();
        //if(list_array !=null) {
        //Log.d("strEditText", "here is the size of the array " + list_array.size());
        //}
        //*********************************************
        //adapter = new ArrayAdapter<String>(Home_activity.this, android.R.layout.simple_list_item_multiple_choice, list_array);
        //counter_list.setAdapter(adapter);
        //*********************************************

        //Bundle info = getIntent().getExtras();
        //String data = info.getString("thename_passed");


        //counter_list = (ListView) findViewById(R.id.list);

        //adapter = new ArrayAdapter<String>(Home_activity.this, android.R.layout.simple_list_item_1, list_array);
        //counter_list.setAdapter(adapter);

        String newdate  = DateFormat.getDateInstance().format(new Date());


        //Log.d("strEditText", "here isjjj "+newdate);

        btn = (Button) findViewById(R.id.option);
        add = (Button) findViewById(R.id.add_button);



        //list_array.add(data);

        //adapter.notifyDataSetChanged();



        counter_list.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Intent intent = new Intent(Home_activity.this, MainActivity.class);
                int value = counter_list.getAdapter().getCount();
                intent.putExtra("keyname2",position);
                Home_activity.this.startActivity(intent);
                saveinFile();
            }
        });

        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i= new Intent(Home_activity.this, MainActivity.class);
                startActivityForResult(i,1);

                saveinFile();



                //Bundle info = getIntent().getExtras();
                //String data = info.getString("thename_passed");



            }
        });

        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent2 = new Intent(Home_activity.this, MainActivity.class);
                int value = counter_list.getAdapter().getCount();
                intent2.putExtra("keyname",list_array.size());
                Home_activity.this.startActivity(intent2);
                //startActivity(new Intent(Home_activity.this, add_activity.class));
                saveinFile();
            }
        });
    }


    @Override

    protected void onStart(){
        super.onStart();
        loadFromFile();
        adapter = new ArrayAdapter<String>(Home_activity.this, android.R.layout.simple_list_item_multiple_choice, list_array);

        if(list_array !=null) {
            counter_list.setAdapter(adapter);
        }
        //This clears the adapter if i want to do another run
        //adapter.clear();
        //adapter.notifyDataSetChanged();
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
    }

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


    @Override
    public void onActivityResult(int requestCode,int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        String textt = "result is ";


        if(requestCode ==1){
            if(resultCode == RESULT_OK){
                String strEditText = data.getStringExtra("thename_passed");

                Log.d("strEditText", textt+strEditText);



                list_array.add(strEditText);
                //list_array.add(strEditText);

                adapter.notifyDataSetChanged();
                //saveinFile();

            }
        }
    }
    //@Override
    //protected void onListItem




}
