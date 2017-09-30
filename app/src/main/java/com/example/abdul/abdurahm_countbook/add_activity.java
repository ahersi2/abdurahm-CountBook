package com.example.abdul.abdurahm_countbook;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
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

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class add_activity extends AppCompatActivity {

    Button save;

    public static final String TEXTFILE = "countbook.txt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_activity);

        loadSavedFile();

        save = (Button) findViewById(R.id.save_button);

        save.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View view){

                EditText editText = (EditText)findViewById(R.id.thename);
                String text = editText.getText().toString();
                

                try {
                    FileOutputStream fos = openFileOutput(TEXTFILE, Context.MODE_PRIVATE);
                    fos.write(text.getBytes());
                    fos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }));
    }


    private void loadSavedFile(){
        try {
            FileInputStream fis = openFileInput(TEXTFILE);

            BufferedReader reader = new BufferedReader(new InputStreamReader( new DataInputStream(fis)));

            EditText editText = (EditText)findViewById(R.id.thename);

            String line;

            while((line = reader.readLine()) != null){
                editText.append(line);
                editText.append("\n");
            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
