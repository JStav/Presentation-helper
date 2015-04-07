package edu.ucf.cecs.acm.presentationhelper;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class CustomCuesActivity extends ActionBarActivity {
    private Button btnAddCue;
    private static EditText cueDuration;
    private static EditText cueName;
    private Button btnSave;

    private ListView list;
    private static ArrayList<String> cueNameList;
    private static ArrayList<Integer> cueDurationList;
    private static ArrayList<String> cueList;

    private int oldduration = 0;

    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_cues);

        cueNameList = new ArrayList<String>();
        cueDurationList = new ArrayList<Integer>();

        btnAddCue = (Button) findViewById(R.id.btnAddCue);
        cueName = (EditText) findViewById(R.id.cueName);
        cueDuration = (EditText) findViewById(R.id.cueDuration);

        cueList = new ArrayList<String>();

        list = (ListView) findViewById(R.id.cueList);

        adapter = new ArrayAdapter<String>(this, R.layout.li_layout, cueList);
        list.setAdapter(adapter);

        btnAddCue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = cueName.getText().toString();
                cueNameList.add(name);
                int duration = Integer.parseInt(cueDuration.getText().toString());
                cueDurationList.add(duration);
                duration += oldduration;
                oldduration = duration;
                resetNameAndDuration();
                String combined;
                if(duration < 60)
                    combined = "\t\t" + name + "\t\t\t\t\t" + duration + " seconds";
                else {
                    String time;
                    if(duration % 60 < 10)
                        time = (duration / 60) + ":0" + duration % 60;
                    else
                        time = (duration / 60) + ":" + duration % 60;
                    combined = "\t\t" + name + "\t\t\t\t\t" + time;
                }

                cueList.add(combined);
                adapter.notifyDataSetChanged();

                Toast.makeText(getApplicationContext(), "\"" + name + "\" added at " + duration + " seconds", Toast.LENGTH_LONG).show();
            }
        });

        btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "This functionality ain't been added yet...", Toast.LENGTH_LONG).show();
            }
        });
    }


    public static void resetNameAndDuration() {
        cueName.setText("");
        cueDuration.setText("");
    }
}