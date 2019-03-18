package com.example.spei.countbook;


        import android.content.Context;
        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;

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


public class Detail extends AppCompatActivity {
    private static final String FILENAME = "file.sav";
    private ArrayList<Counter> CountList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        final int position = intent.getIntExtra("position",0);

        loadFromFile();

        final Counter counter = CountList.get(position);
        String comment = counter.getComment();
        String name = counter.getName();
        String date = counter.getDate();
        int currentValue = counter.getCurrentValue();
        int initialValue = counter.getInitialValue();

        TextView Name = (TextView) findViewById(R.id.Name);
        TextView Comment = (TextView) findViewById(R.id.Comment);
        final TextView Count = (TextView) findViewById(R.id.Count);
        TextView InitialValue = (TextView) findViewById(R.id.initialValue);
        final TextView Date = (TextView) findViewById(R.id.Date);

        Button Reset = (Button) findViewById(R.id.Reset);
        Button Decrement = (Button) findViewById(R.id.Decrement);
        Button Increment = (Button) findViewById(R.id.Increment);
        Button Edit = (Button) findViewById(R.id.edit);
        Button Delete = (Button) findViewById(R.id.delete);

        Name.setText("Name: "+name);
        InitialValue.setText("Initial Value: "+Integer.toString(initialValue));
        Date.setText("Date: "+date);
        Comment.setText("Comment: "+comment);
        Count.setText("Current value: "+Integer.toString(currentValue));


        Decrement.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setResult(RESULT_OK);
                counter.changeCount(-1);
                counter.setDate();
                Count.setText("Current value: "+Integer.toString(counter.getCurrentValue()));
                Date.setText("Date: "+counter.getDate());
                CountList.set(position,counter);
                saveInFile();
            }
        });
        Increment.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setResult(RESULT_OK);
                counter.changeCount(1);
                counter.setDate();
                Count.setText("Current value: "+Integer.toString(counter.getCurrentValue()));
                Date.setText("Date: "+counter.getDate());
                CountList.set(position,counter);
                saveInFile();
            }
        });

        Reset.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setResult(RESULT_OK);
                counter.resetCount();
                counter.setDate();
                Count.setText("Current value: "+Integer.toString(counter.getCurrentValue()));
                Date.setText("Date: "+counter.getDate());
                CountList.set(position,counter);
                saveInFile();
            }
        });

        Edit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setResult(RESULT_OK);
                Intent intent = new Intent(Detail.this, Modify.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
        Delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setResult(RESULT_OK);
                CountList.remove(position);
                saveInFile();
                finish();
            }
        });



    }

    @Override
    protected void onResume (){
        super.onResume();
        loadFromFile();
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
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
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
