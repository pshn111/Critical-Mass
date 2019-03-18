package com.example.spei.countbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;


public class NewCounterActivity extends AppCompatActivity {
    private static final String FILENAME = "file.sav";
    private ArrayList<Counter> CountList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_counter);

        loadFromFile();
    }
    public void createCounter(View view) {

        EditText editName = (EditText) findViewById(R.id.Name);
        EditText editValue = (EditText) findViewById(R.id.initialVal);
        EditText editComment = (EditText) findViewById(R.id.editText3);
        boolean valid = true;

        if (editName.getText().toString().equals("")) {
            valid = false;
            editName.setError("Field empty. Please enter required field.");
        }
        if (editValue.getText().toString().equals("")) {
            valid = false;
            editValue.setError("Please enter a count.");
        }



        if (valid && Integer.parseInt(editValue.getText().toString()) >= 0) {
            Counter counter = new Counter(editName.getText().toString(), Integer.parseInt(editValue.getText().toString()), editComment.getText().toString());
//            System.out.println("here");
            CountList.add(counter);
            saveInFile();
//            System.out.println("list: " + CountList.size());

        }

        saveInFile();
        finish();


        //        String name = editName.getText().toString();
//        boolean parsable = true;
//        try{
//            Integer.parseInt(editInitalValue.getText().toString());
//        }catch(NumberFormatException e){
//            parsable = false;
//        }
//
////        int initialValue = Integer.parseInt(editInitalValue.getText().toString());
//        final String comment = editComment.getText().toString();
//
//
//
//
//        if (!comment.isEmpty() && !name.isEmpty() && parsable) {
//            Counter newCounter = new Counter(name,Integer.parseInt(editInitalValue.getText().toString()),comment);
//            list.add(newCounter);
//        }
//        else if (!name.isEmpty() && parsable){
//            Counter newCounter = new Counter(name, Integer.parseInt(editInitalValue.getText().toString()));
//            list.add(newCounter);
//        }
//
//
//
//
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (editName.getText().toString().isEmpty()){
//                    editName.setError("Field empty. Please enter required field.");
//                }
//                else if (editInitalValue.getText().toString().trim().equals("")) {
//                    editInitalValue.setError("Please enter a count.");
//                }
//                else if (Integer.parseInt(editInitalValue.getText().toString()) < 0) {
//                    editInitalValue.setError("Please enter a non-negative number.");
//                }
//                else {
//                    saveInFile();
//                    finish();
//                }
//
//
//
//            }
//        });

    }




    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos));

            Gson gson = new Gson();
            gson.toJson(CountList, out);
            out.flush();
            fos.close();
        }
        catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }


    private void loadFromFile() {
        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));

            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<Counter>>() {
            }.getType();
            CountList = gson.fromJson(in, listType);


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            CountList = new ArrayList<Counter>();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

}